package com.raul.spring.jpa.springjpav1.controllers.rest;

import com.raul.spring.jpa.springjpav1.models.entity.Partner;
import com.raul.spring.jpa.springjpav1.models.service.IPartnerRepoService;
import com.raul.spring.jpa.springjpav1.models.service.IUploadService;
import com.raul.spring.jpa.springjpav1.view.xml.PartnerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PartnerRestController {

    @Autowired
    private IPartnerRepoService partnerRepoService;

    @Autowired
    private IUploadService uploadService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = {"/partner/list-partner"})
    @Secured("ROLE_ADMIN")
    public PartnerList listRest() {
        return new PartnerList(partnerRepoService.findAll());
    }

    @GetMapping(value = {"/partner/list-partner/page/{page}"})
    public Page<Partner> listRest(@PathVariable int page) {
        Pageable pageRequest = PageRequest.of(page, 6);
        return partnerRepoService.findAll(pageRequest);
    }

    @GetMapping("partner/{id}")
    public ResponseEntity<?> partnerDetail(@PathVariable Long id) {
        Partner partner = null ;
        Map<String, Object> response = new HashMap<>();

        try{
            partner = partnerRepoService.findOne(id);
        } catch(DataAccessException e) {
            response.put("errorMsg","Error trying to connect to the db");
            response.put("errorCause",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (partner == null) {
            response.put("errorMsg","The id does not match with a partner");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Partner>( partner
                ,HttpStatus.OK);
    }

    @PostMapping("/partner")
    public ResponseEntity<?> partnerCreate(@Valid @RequestBody Partner partner, BindingResult bindingResult){
        Map<String, Object> response = new HashMap<>();
        Partner newPartner = null ;

        if(bindingResult.hasErrors()){
            response.put("errors",
                    bindingResult.getFieldErrors()
                            .stream()
                            .map(error -> "the field "+ error.getField() + ": "+ error.getDefaultMessage())
                            .collect(Collectors.toList()) );
            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.CONFLICT) ;
        }

        try{
            newPartner = partnerRepoService.saveAndReturnPartner(partner);
        } catch (Exception e){
            if(e.getMessage().contains("validation")){
                response.put("errorMsg","Error of validation in fields");
            }
            response.put("errorCause",e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("successError" , "New partner "+ partner.getName()+" created successfully");
        response.put("partner", newPartner);

        return new ResponseEntity<Map<String, Object>>(response , HttpStatus.CREATED) ;
    }

    @PutMapping("/partner/{id}")
    public ResponseEntity<?> partnerEdition(@Valid @RequestBody Partner partner,
                                  @PathVariable Long id, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();
        Partner partnerToUpdate = null;

        if(bindingResult.hasErrors()){
            response.put("errors",
                    bindingResult.getFieldErrors()
                            .stream()
                            .map(error -> error.getDefaultMessage())
                            .collect(Collectors.toList()) );
            return new ResponseEntity<Map<String, Object>>(response , HttpStatus.CONFLICT) ;
        }

        try{
            partnerToUpdate = partnerRepoService.findOne(id);
        } catch (DataAccessException e){
            response.put("errorMsg","Error trying to connect to the db");
            response.put("errorCause",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        partnerToUpdate.setEmail(partner.getEmail());
        partnerToUpdate.setName(partner.getName());
        partnerToUpdate.setSurname(partner.getSurname());
        Partner updatedPartner = new Partner();

        updatedPartner = partnerRepoService.saveAndReturnPartner(partnerToUpdate);
        response.put("partner", updatedPartner);
        response.put("msg", "The partner has been update successfully");
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/partner-full-detail/{id}")
    public ResponseEntity<?> partnerFullEdition(
            //@Valid Partner partner,
            @RequestParam("id")Long id,
            @RequestParam(name = "file") MultipartFile photo
    ) {
        Partner partnerToUpdate = null;
        Map<String, Object> response = new HashMap<>();

        try{
            partnerToUpdate = partnerRepoService.findOne(id);
        } catch (DataAccessException e){
            response.put("errors","Error updating the partner we can not look the partner");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!photo.isEmpty()) {
            if (partnerToUpdate.getId() != null && partnerToUpdate.getId() > 0) {
                try {
                    partnerToUpdate = partnerRepoService.findOne(id);
                    if (partnerToUpdate.getPhoto().length() > 0) {

                        if (uploadService.delete(partnerToUpdate.getPhoto())) {
                            response.put("warning", "Your old photo has been replace with : " + photo.getOriginalFilename());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
        try {
            partnerToUpdate.setPhoto(uploadService.copy(photo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.put("success", "The partner was " + (partnerToUpdate.getId() != null ? "Updated" : "Created"));
        partnerRepoService.save(partnerToUpdate);
        response.put("partner",partnerToUpdate);

        return new ResponseEntity<Map<String, Object>>(response , HttpStatus.CREATED) ;
    }

    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> showPhoto(@PathVariable String filename) {
        Resource resource = null;

        try {
            resource = uploadService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename:\"" + resource.getFilename());
        return new ResponseEntity<Resource>(resource,header,HttpStatus.OK);

    }

    @DeleteMapping("/partner/{id}")
    public ResponseEntity<?> deletePartner(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Partner partnerToUpdate = null;
        try{
            partnerRepoService.delete(id);
        } catch (DataAccessException e){
            response.put("errorMsg","Error trying to connect to the db");
            response.put("errorCause",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("msg", "The partner has been deleted successfully");
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

    }

}

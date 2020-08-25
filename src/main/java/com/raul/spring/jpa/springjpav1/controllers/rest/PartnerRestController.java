package com.raul.spring.jpa.springjpav1.controllers.rest;

import com.raul.spring.jpa.springjpav1.models.entity.Partner;
import com.raul.spring.jpa.springjpav1.models.service.IPartnerRepoService;
import com.raul.spring.jpa.springjpav1.view.xml.PartnerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PartnerRestController {

    @Autowired
    private IPartnerRepoService partnerRepoService;

    @GetMapping(value = {"/partner/list-partner"})
    @Secured("ROLE_ADMIN")
    public PartnerList listRest() {
        return new PartnerList(partnerRepoService.findAll());
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> partnerCreate(@RequestBody Partner partner){
        //return partnerRepoService.saveAndReturnPartner(partner);
        Map<String, Object> response = new HashMap<>();
        Partner newPartner = null ;
        try{
            newPartner = partnerRepoService.saveAndReturnPartner(partner);
        } catch (DataAccessException e){
            response.put("errorMsg","Error trying to connect to the db");
            response.put("errorCause",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("successError" , "New partner created successfully");
        response.put("partner", newPartner);

        return new ResponseEntity<Map<String, Object>>(response , HttpStatus.CREATED) ;
    }

    @PutMapping("/partner/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> partnerEdition(@RequestBody Partner partner,
                                  @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Partner partnerToUpdate = null;
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

package com.raul.spring.jpa.springjpav1.controllers;

import com.raul.spring.jpa.springjpav1.models.entity.Partner;
import com.raul.spring.jpa.springjpav1.models.service.IPartnerRepoService;
import com.raul.spring.jpa.springjpav1.models.service.IUploadService;
import com.raul.spring.jpa.springjpav1.util.paginator.PageRender;
import com.raul.spring.jpa.springjpav1.view.xml.PartnerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class PartnerController {

    private String TITLE = "Partner form";
    private boolean flag = false;


    @Autowired
    private IPartnerRepoService partnerRepoService;

    @Autowired
    private IUploadService uploadService;

    /**
     * I18N
     */
    @Autowired
    private MessageSource messageSource;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> showPhoto(@PathVariable String filename) {
        Resource resource = null;

        try {
            resource = uploadService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=/" + resource.getFilename() + "/")
                .body(resource);

    }

    @GetMapping(value = "/show/{id}")
    public String show(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Partner partner = null;
        try {
            //partner = partnerRepoService.findOne(id);
            partner = partnerRepoService.fetchByIdWithSaleOrders(id);
        } catch (Exception e) {
            e.printStackTrace();
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/list";
        }
        if (partner == null) {
            flash.addFlashAttribute("error", "The partner is unknow in DB");
            return "redirect:/list";
        }

        model.put("partner", partner);
        model.put("title", "Partner detail: " + partner.getName());
        return "show-partner";
    }


    @RequestMapping(value = {"/list", "/","","/index"}, method = RequestMethod.GET)
    public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Locale locale) {
        Pageable pageRequest = PageRequest.of(page, 6);

        Page<Partner> partners = partnerRepoService.findAll(pageRequest);

        PageRender<Partner> pageRender = new PageRender<>("/list", partners);

        model.addAttribute("Title", messageSource.getMessage("text.controller.partner.title", null,locale));
        model.addAttribute("partners", partners);
        model.addAttribute("page", pageRender);

        return "index";
    }

    @RequestMapping(value = "/partner-form")
    public String create(Map<String, Object> model) {
        Partner partner = new Partner();
        model.put("titulo", TITLE);
        model.put("partner", partner);

        return "partner-form";
    }

    @RequestMapping(value = "/partner-form", method = RequestMethod.POST)
    public String save(
            @Valid Partner partner,
            BindingResult result,
            Model model,
            @RequestParam(name = "file") MultipartFile photo,
            RedirectAttributes flash,
            SessionStatus status
    ) {
        if (result.hasErrors()) {

            model.addAttribute("titulo", TITLE);
            return "partner-form";
        }

        if (!photo.isEmpty()) {
            if (partner.getId() != null && partner.getId() > 0) {
                Partner pa = null;
                try {
                    pa = partnerRepoService.findOne(partner.getId());
                    if (pa.getPhoto().length() > 0) {

                        if (uploadService.delete(pa.getPhoto())) {
                            flash.addFlashAttribute("warning", "Your old photo has been replace with : " + photo.getOriginalFilename());

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
        try {
            partner.setPhoto(uploadService.copy(photo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        flash.addFlashAttribute("success", "The partner was " + (partner.getId() != null ? "Updated" : "Created"));
        partnerRepoService.save(partner);
        status.setComplete();

        return "redirect:list";
    }

    @RequestMapping(value = "/partner-form/{id}")
    public String update(
            @PathVariable(value = "id") Long id,
            Map<String, Object> model, RedirectAttributes flash
    ) {
        Partner partner = null;
        if (id > 0) {
            try {
                partner = partnerRepoService.findOne(id);
                if (partner == null) {
                    flash.addFlashAttribute("error", "Unknow partner in db");
                }
            } catch (Exception e) {
                flash.addFlashAttribute("error", "The partner is unknow ");
                return "redirect:../list";
            }


        } else {
            flash.addFlashAttribute("error", "The partner id can not be 0 ");
            return "redirect:../list";
        }
        model.put("partner", partner);
        model.put("title", "Edit partner");
        return "partner-form";

    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        Partner partner;
        if (id > 0) {
            try {

                partner = partnerRepoService.findOne(id);
                if (partner.getPhoto() != null) {
                    if (uploadService.delete(partner.getPhoto())) {
                        flash.addFlashAttribute("info", "photo: " + partner.getPhoto() + " deleted success full");
                    }
                }

                partnerRepoService.delete(id);
                flash.addFlashAttribute("success", "The partner was delete successful ");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return "redirect:../list";
    }


    /*-----------------------------------checkRole-------------------------------------------------*/
    /**
     * To verify if the user is part of some rol, and check
     */
    public boolean hasRoleNative(HttpServletRequest request){
        return request.isUserInRole("ROLE_ADMIN");
    }


    /**
     * To verify if the user is part of some rol, and check
     */
    public boolean hasRole(HttpServletRequest request){
        flag = false;
        SecurityContextHolderAwareRequestWrapper securityContext =
                new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        if(securityContext.isUserInRole("ADMIN")){
            flag = true;
        }

        return flag;
    }

    /**
     * To verify if the user is part of some rol, and check
     */
    private boolean hasRole(String role) {

        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null ){
            flag = false;
        }
        Authentication auth = context.getAuthentication();

        if (auth == null ){
            flag = false;
        }

        /**
         * all class Role have to implements this interface
         */
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        authorities.forEach( rol ->{
            if(role.equals(rol.getAuthority())){
                flag = true;
            }
        });

        return flag;
    }

}

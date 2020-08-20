package com.raul.spring.jpa.springjpav1.controllers.rest;

import com.raul.spring.jpa.springjpav1.models.entity.Partner;
import com.raul.spring.jpa.springjpav1.models.service.IPartnerRepoService;
import com.raul.spring.jpa.springjpav1.view.xml.PartnerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PartnerRestController {

    @Autowired
    private IPartnerRepoService partnerRepoService;

    @GetMapping(value = {"/partner/list-partner"})
    @Secured("ROLE_ADMIN")
    public PartnerList listRest() {
        return new PartnerList(partnerRepoService.findAll());
    }

    @GetMapping("partner/{id}")
    public Partner partnerDetail(@PathVariable Long id) {
        return partnerRepoService.findOne(id);
    }

    @PostMapping("/partner")
    @ResponseStatus(HttpStatus.CREATED)
    public Partner partnerCreate(@RequestBody Partner partner){
        return partnerRepoService.saveAndReturnPartner(partner);
    }

    @PutMapping("/partner/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Partner partnerEdition(@RequestBody Partner partner,
                                  @PathVariable Long id) {
        Partner partnerToUpdate = partnerRepoService.findOne(id);
        partnerToUpdate.setEmail(partner.getEmail());
        partnerToUpdate.setName(partner.getName());
        partnerToUpdate.setSurname(partner.getSurname());

        return partnerRepoService.saveAndReturnPartner(partnerToUpdate);
    }

    @DeleteMapping("/partner/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartner(@PathVariable Long id) {
        partnerRepoService.delete(id);
    }
}

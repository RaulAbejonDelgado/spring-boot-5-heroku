package com.raul.spring.jpa.springjpav1.controllers;

import com.raul.spring.jpa.springjpav1.models.service.IPartnerRepoService;
import com.raul.spring.jpa.springjpav1.view.xml.PartnerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PartnerRestController {

    @Autowired
    private IPartnerRepoService partnerRepoService;

    /**
     *
     * @return Parter list to convert too xml
     */
    @GetMapping(value = {"/partner/list-partner"})
    public PartnerList listRest() {
        return new PartnerList(partnerRepoService.findAll());
    }
}

package com.raul.spring.jpa.springjpav1.view.json;

import com.raul.spring.jpa.springjpav1.models.entity.Partner;
import com.raul.spring.jpa.springjpav1.view.xml.PartnerList;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

@Component("index.json")
public class PartnerListJson extends MappingJackson2JsonView {

    @Override
    protected Object filterModel(Map<String, Object> model) {

        Page<Partner> pageRender = (Page<Partner> )model.get("partners");
        model.remove("Title");
        model.remove("page");
        model.remove("partners");
        model.put("partnerList", pageRender.getContent());

        return super.filterModel(model);
    }
}

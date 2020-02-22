package com.raul.spring.jpa.springjpav1.view.xml;

import com.raul.spring.jpa.springjpav1.models.entity.Partner;
import com.raul.spring.jpa.springjpav1.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("index.xml")
public class PartnerXmlView extends MarshallingView {

    @Autowired
    public PartnerXmlView(Jaxb2Marshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request,
                                           HttpServletResponse response)
            throws Exception {
        Page<Partner> pageRender = (Page<Partner> )model.get("partners");
        model.remove("Title");
        model.remove("page");
        model.remove("partners");
        model.put("partnerList", new PartnerList(pageRender.getContent()));

        super.renderMergedOutputModel(model, request, response);
    }
}

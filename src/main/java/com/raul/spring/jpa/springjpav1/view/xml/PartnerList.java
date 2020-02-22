package com.raul.spring.jpa.springjpav1.view.xml;

import com.raul.spring.jpa.springjpav1.models.entity.Partner;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This clase is done because jaxb cant parse list of object then to do it
 * we create this class how a wrapper
 */
@XmlRootElement(name="partners")
public class PartnerList {

    @XmlElement(name="partner")
    public List<Partner> partnerList;

    public PartnerList() {
    }

    public PartnerList(List<Partner> partnerList) {
        this.partnerList = partnerList;
    }

    public List<Partner> getPartnerList() {
        return partnerList;
    }
}

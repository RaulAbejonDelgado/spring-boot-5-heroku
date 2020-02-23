package com.raul.spring.jpa.springjpav1.models.dao;

import com.raul.spring.jpa.springjpav1.models.entity.Partner;

import java.util.List;


public interface IPartner {

    public List<Partner> getAll();

    public void save(Partner partner);

    public Partner findOne(Long id);

    public void delete(Long id);
}

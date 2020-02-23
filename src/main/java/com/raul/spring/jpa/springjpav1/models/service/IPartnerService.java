package com.raul.spring.jpa.springjpav1.models.service;

import java.util.List;

public interface IPartnerService {

    public List<Partner> getAll();

    public void save(Partner partner);

    public Partner findOne(Long id);

    public void delete(Long id);
}

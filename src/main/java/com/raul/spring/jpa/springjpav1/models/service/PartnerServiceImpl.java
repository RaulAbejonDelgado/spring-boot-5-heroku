package com.raul.spring.jpa.springjpav1.models.service;

import com.raul.spring.jpa.springjpav1.models.dao.IPartner;
import com.raul.spring.jpa.springjpav1.models.entity.Partner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartnerServiceImpl implements IPartnerService {

    @Autowired
    private IPartner partnerDao;

    @Transactional(readOnly = true)
    @Override
    public List<Partner> getAll() {
        return partnerDao.getAll();
    }

    @Transactional
    @Override
    public void save(Partner partner) {
        partnerDao.save(partner);
    }

    @Transactional(readOnly = true)
    @Override
    public Partner findOne(Long id) {
        return partnerDao.findOne(id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        partnerDao.delete(id);
    }
}

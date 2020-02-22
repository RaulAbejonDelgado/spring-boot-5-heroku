package com.raul.spring.jpa.springjpav1.models.dao;

import com.raul.spring.jpa.springjpav1.models.entity.Partner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PartnerDaoImpl implements IPartner {

    //automaticamente inyecta segun la unidad de persistencia que conteniene
    // el proveedor jpa, por defecto usa h2 embebida

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")

    @Override
    public List<Partner> getAll() {
        List<Partner> partners = em.createQuery("from Partner").getResultList();
        return partners;
    }

    /**
     * em will managed the safe way to persistance data on db
     * @param partner
     */
    @Override
    public void save(Partner partner) {
        if(partner.getId() != null && partner.getId() > 0){
            partner.prePersist();
            em.merge(partner);
        }else{
            em.persist(partner);
        }

    }

    @Override
    public Partner findOne(Long id) {

        return em.find(Partner.class, id);
    }

    @Override
    public void delete(Long id) {
        em.remove(findOne(id));

    }

}

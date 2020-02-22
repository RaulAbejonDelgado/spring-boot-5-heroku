package com.raul.spring.jpa.springjpav1.models.dao;

import com.raul.spring.jpa.springjpav1.models.entity.SaleOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IsaleOrderDao extends CrudRepository<SaleOrder, Long > {

    @Query("select so from SaleOrder so join fetch so.partner p join fetch so.lines sol join fetch sol.product where so.id=?1")
    SaleOrder fetchByIdWithPartnerWithSoLineWithProduct(Long id);


}

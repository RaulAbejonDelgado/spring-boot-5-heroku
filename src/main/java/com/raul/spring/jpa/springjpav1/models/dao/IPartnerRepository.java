package com.raul.spring.jpa.springjpav1.models.dao;

import com.raul.spring.jpa.springjpav1.models.entity.Partner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IPartnerRepository extends PagingAndSortingRepository<Partner, Long> {

    /**
     * left join for retrieve partner with saleOrder too
     * @param id
     * @return
     */
    @Query("select p from Partner p left join fetch p.saleOrders so where p.id=?1")
    Partner fetchByIdWithSaleOrders(Long id);
}

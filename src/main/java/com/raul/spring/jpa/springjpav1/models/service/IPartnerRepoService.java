package com.raul.spring.jpa.springjpav1.models.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPartnerRepoService {
    public List<Partner> findAll();

    public Page<Partner> findAll(Pageable pageable);

    public void save(Partner partner);

    public Partner findOne(Long id) throws Exception;

    public Partner fetchByIdWithSaleOrders(Long id);

    public void delete(Long id);

    public List<Product> findByProductName(String term);

    public void saveSaleOrder(SaleOrder saleOrder);

    public Product findProductById(Long id);

    public SaleOrder findSaleOrderByPartnerId(Long id);

    public void deletePartnerSaleOrderById(Long id);

    public SaleOrder fetchByIdWithPartnerWithSoLineWithProduct(Long id);


}

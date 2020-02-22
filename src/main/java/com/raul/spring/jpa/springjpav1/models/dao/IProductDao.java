package com.raul.spring.jpa.springjpav1.models.dao;

import com.raul.spring.jpa.springjpav1.models.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IProductDao extends CrudRepository<Product, Long> {

    @Query("select p from Product p where p.name like %?1%")
    public List<Product> findByName(String term);

    //public Optional<Product> findById(Long id);


}

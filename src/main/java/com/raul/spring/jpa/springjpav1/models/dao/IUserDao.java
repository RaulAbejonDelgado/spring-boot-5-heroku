package com.raul.spring.jpa.springjpav1.models.dao;

import com.raul.spring.jpa.springjpav1.models.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Other class to extend could be jpaRepository this class have more methods how Paging , order etc
 */
public interface IUserDao extends CrudRepository<UserEntity, Long> {

    public UserEntity findByUserName(String username);

}

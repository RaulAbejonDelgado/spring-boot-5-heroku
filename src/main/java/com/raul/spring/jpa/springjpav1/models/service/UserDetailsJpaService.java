package com.raul.spring.jpa.springjpav1.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsJpaService")
public class UserDetailsJpaService implements UserDetailsService {

    @Autowired
    IUserDao userDao;

    private Logger logger = LoggerFactory.getLogger(UserDetailsJpaService.class);

    /**
     * UserDetails object = authenticated user
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserEntity user = userDao.findByUserName(s);

        if(user == null){
            logger.error("The user does not exist");
            throw new UsernameNotFoundException("The user does not exist");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if(authorities == null){
            logger.error("The user does not have any roles");
            throw new UsernameNotFoundException("The user ".concat(user.getUserName()).concat(" does not have any roles"));
        }
        for(Role role : user.getRoles()){
            logger.info(role.getAuthority());
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        return new User(
                user.getUserName(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}

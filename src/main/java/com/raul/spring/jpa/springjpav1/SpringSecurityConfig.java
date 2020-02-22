package com.raul.spring.jpa.springjpav1;

import com.raul.spring.jpa.springjpav1.auth.handler.LoginSuccessHandler;
import com.raul.spring.jpa.springjpav1.models.service.UserDetailsJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler successHandler;

    /**
     * Jdbc authentication
     */
//    @Autowired
//    private DataSource dataSource;
//    private String USER_QUERY = "select username, password, enabled from users where username=?";
//    private String USER_ROLE = "select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?";

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * JPA authentication
     */
    @Autowired
    private UserDetailsJpaService userDetailsJpaServcie;





    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/","/css/**","/js/**","/images/**","/list/**","/locale/**","/api/**").permitAll()
                .antMatchers("/show/**","/uploads/**").hasAnyRole("USER")
                .antMatchers("/form/**","/delete/**","/saleorder/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successHandler).loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_http")
        ;
    }

    /**
     * In memory
     */
//    @Autowired
//    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
//
//        PasswordEncoder encoder = passwordEncoder();
//
//        UserBuilder users = UserEntity.builder().passwordEncoder(password -> {
//            return encoder.encode(password);
//        });
//
//        builder.inMemoryAuthentication()
//                .withUser(users.username("admin").password("admin")
//                        .roles("ADMIN","USER"))
//                .withUser(users.username("drohne").password("drohne")
//                        .roles("USER"));
//
//    }

    /**
     * Jdbc authentication
     */
//    @Autowired
//    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
//    {
//        build.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder)
//                .usersByUsernameQuery(USER_QUERY)
//                .authoritiesByUsernameQuery(USER_ROLE);
//    }

    /**
     * JpaAuthentication
     */
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
    {
        build.userDetailsService(userDetailsJpaServcie)
                .passwordEncoder(passwordEncoder);

    }
}

package com.raul.spring.jpa.springjpav1.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LocaleController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/locale")
    public String Locale(HttpServletRequest request) {
        //This retrieve the url
        String currentUrl = request.getHeader("referer");
        log.info("CAPTURING URL VALUE WHEN LOCALE IS CHANGED -> ".concat(currentUrl));
        return "redirect:".concat(currentUrl);
    }
}

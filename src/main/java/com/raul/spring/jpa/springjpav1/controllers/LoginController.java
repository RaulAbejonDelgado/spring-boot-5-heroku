package com.raul.spring.jpa.springjpav1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model, Principal principal, RedirectAttributes flash){

        if(principal != null){
            flash.addFlashAttribute("info","The user was logged");
            return "redirect:/";
        }

        if(error != null){
            flash.addFlashAttribute("error", error);
            System.out.println(error);
            model.addAttribute("error", "Try to contact with technical support");
        }

        return "login";
    }
}

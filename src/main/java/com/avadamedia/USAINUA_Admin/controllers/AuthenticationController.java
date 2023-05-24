package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.enums.ContextPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/admin/";
        } else {
            return "admin/authenticate";
        }
    }
    @GetMapping("/")
    public String admin() {
        return "redirect:/admin/";
    }
}

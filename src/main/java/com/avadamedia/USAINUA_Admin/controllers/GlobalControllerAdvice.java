package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.enums.ContextPath;
import com.avadamedia.USAINUA_Admin.services.impl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final UsersServiceImpl usersService;
    @ModelAttribute("context_path")
    public String addContextPathToModel() {
        return ContextPath.PATH.getUrl();
    }
    @ModelAttribute("navbar")
    public String getHeader(Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            model.addAttribute("userLogo", usersService.getByEmail(authentication.getName()).getName());
        }catch (Exception e){
            log.info("Користувач не авторизований");
        }
        return "blocks/navbar";
    }
}

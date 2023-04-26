package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.Users;
import com.avadamedia.USAINUA_Admin.repositories.UsersRepository;
import com.avadamedia.USAINUA_Admin.util.EmailUtil;
import com.avadamedia.USAINUA_Admin.services.impl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final UsersRepository usersRepository;
    private final UsersServiceImpl usersServiceImpl;
    private String email;

    @GetMapping("/login")
    public String getPassword() {
        return "admin/authenticate";
    }
}

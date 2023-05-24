package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.repositories.*;
import com.avadamedia.USAINUA_Admin.services.impl.*;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequiredArgsConstructor
public class AdminController {
    private final ShopsServiceImpl shopsServiceImpl;
    private final ProductsServiceImpl productsServiceImpl;
    private final StatsServiceImpl statsServiceImpl;
    private final UsersServiceImpl usersServiceImpl;

    @GetMapping("/admin/")
    public String stats(Model model) {
        model.addAttribute("woman", usersServiceImpl.getAllWoman().size());
        model.addAttribute("man", usersServiceImpl.getAllMan().size());
        model.addAttribute("months", new Gson().toJson(statsServiceImpl.getAllMonth()));
        model.addAttribute("value", statsServiceImpl.getAllValue());
        model.addAttribute("shops", shopsServiceImpl.getAll().size());
        model.addAttribute("products", productsServiceImpl.getAll().size());
        model.addAttribute("users", usersServiceImpl.getAll().size());
        return "admin/stats";
    }
}


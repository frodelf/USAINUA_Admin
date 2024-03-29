package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.User;
import com.avadamedia.USAINUA_Admin.mappers.UserMapper;
import com.avadamedia.USAINUA_Admin.models.UserDTO;
import com.avadamedia.USAINUA_Admin.repositories.UsersRepository;
import com.avadamedia.USAINUA_Admin.services.impl.RolesServiceImpl;
import com.avadamedia.USAINUA_Admin.services.impl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {
    private final UsersServiceImpl usersService;
    private final RolesServiceImpl rolesService;
    private final UserMapper userMapper;

    @GetMapping("/")
    public String users(Model model){
        model.addAttribute("users", userMapper.toDtoList(usersService.getOnlyUserAndBlocked()));
        return "admin/user";
    }
    @GetMapping("/admins/")
    public String admins(Model model){
        model.addAttribute("admins", userMapper.toDtoList(usersService.getOnlyAdmin()));
        return "admin/admin";
    }
    @GetMapping("/edit/{id}")
    public String editUserStart(@PathVariable("id")long id, Model model){
        model.addAttribute("user", userMapper.toDto(usersService.getById(id)));
        model.addAttribute("roles",rolesService.getAll());
        return "admin/user-edit";
    }
    @PostMapping("/edit/{id}")
    public String editUserEnd(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult, @PathVariable("id")long id){
        if(bindingResult.hasErrors()){
            return "admin/user-edit";
        }
        User user = userMapper.toEntity(usersService.getById(id),userDTO);
        usersService.save(user);
        if(user.getRoles().contains(rolesService.getById(1)))return "redirect:/admin/user/admins/";
        return "redirect:/admin/user/";
    }
    @PostMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id")long id){
        usersService.deleteById(id);
        return "redirect:/admin/user/";
    }
}

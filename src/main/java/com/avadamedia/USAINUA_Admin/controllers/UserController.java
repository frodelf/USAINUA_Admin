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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {
    private final UsersServiceImpl usersService;
    private final UsersRepository usersRepository;
    private final RolesServiceImpl rolesService;
    private final UserMapper userMapper;

    @GetMapping("/")
    public String users(Model model){
//        model.addAttribute("current", number);
//        if (number < 1) {
//            return "redirect:/admin/user/1";
//        }
//        int max = (int) Math.ceil(usersService.getAll().size() / 5.0);
//        max = max == 0 ? 1 : max;
//        if (number > max) {
//            return "redirect:/admin/user/" + max;
//        }
//        model.addAttribute("max", max);
//        if (!usersService.getAll().isEmpty()) {
//            Page<User> users = usersRepository.findAll(PageRequest.of((number - 1), 5));
//            model.addAttribute("users", userMapper.toDtoList(users.getContent()));
//            return "admin/user";
//        }
        model.addAttribute("users", userMapper.toDtoList(usersService.getAll()));
        return "admin/user";
    }

    @GetMapping("/edit/{id}")
    public String editUserStart(@PathVariable("id")long id, Model model){
        model.addAttribute("user", userMapper.toDto(usersService.getById(id)));
        model.addAttribute("roles",rolesService.getAll());
        return "admin/user-edit";
    }

    @PostMapping("/edit/{id}")
    public String editUserEnd(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "admin/user-edit";
        }
        User user = userMapper.toEntity(userDTO);
        usersService.save(user);
        return "redirect:/admin/user/";
    }

    @PostMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id")long id){
        usersService.deleteById(id);
        return "redirect:/admin/user/";
    }
}

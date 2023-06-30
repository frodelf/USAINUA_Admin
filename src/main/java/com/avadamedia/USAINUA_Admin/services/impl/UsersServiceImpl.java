package com.avadamedia.USAINUA_Admin.services.impl;

import com.avadamedia.USAINUA_Admin.entity.Role;
import com.avadamedia.USAINUA_Admin.entity.User;
import com.avadamedia.USAINUA_Admin.repositories.RolesRepository;
import com.avadamedia.USAINUA_Admin.repositories.UsersRepository;
import com.avadamedia.USAINUA_Admin.services.UsersService;
import com.avadamedia.USAINUA_Admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    public void save(User user){usersRepository.save(user);}
    public User getById(long id){return usersRepository.findById(id).get();}
    public User getByEmail(String email){
        return usersRepository.findByEmail(email).get();
    }
    public void deleteById(long id){
        if(id == 3)return;
        User user = usersRepository.findById(id).get();
        if(user.getRoles().contains(rolesRepository.findById(1L).get()) || user.getRoles().contains(rolesRepository.findById(2L).get())){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(rolesRepository.findById(3L).get());
            user.setRoles(roles);
            user.setPassword(encoder.encode(ImageUtil.generateName()));
            usersRepository.save(user);
        }
        else if (user.getRoles().contains(rolesRepository.findById(3L).get())) usersRepository.deleteById(id);
    }
    public List<User> getAllMan(){return usersRepository.findByIsManIsTrue();}
    public List<User> getAllWoman(){return usersRepository.findByIsManIsFalse();}
    public List<User> getAll(){return usersRepository.findAll();}

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usersRepository.findByEmail(authentication.getName()).get();
    }
    public List<User> getOnlyUserAndBlocked(){
        List<User> result = new ArrayList<>();
        for(User user:usersRepository.findAll()){
            if(user.getRoles().contains(rolesRepository.findById(2L).get()))result.add(user);
            if(user.getRoles().contains(rolesRepository.findById(3L).get()))result.add(user);
        }
        return result;
    }
    public List<User> getOnlyAdmin(){
        List<User> result = new ArrayList<>();
        for(User user:usersRepository.findAll()){
            if(user.getRoles().contains(rolesRepository.findById(1L).get()))result.add(user);
        }
        return result;
    }
}

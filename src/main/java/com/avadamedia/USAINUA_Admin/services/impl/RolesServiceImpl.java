package com.avadamedia.USAINUA_Admin.services.impl;

import com.avadamedia.USAINUA_Admin.entity.Role;
import com.avadamedia.USAINUA_Admin.repositories.RolesRepository;
import com.avadamedia.USAINUA_Admin.services.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {
    private final RolesRepository rolesRepository;

    public Role getById(long id){
        return rolesRepository.findById(id).get();
    }
    public List<Role> getAll(){return rolesRepository.findAll();}
}

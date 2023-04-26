package com.avadamedia.USAINUA_Admin.services;

import com.avadamedia.USAINUA_Admin.entity.Users;

import java.util.List;

public interface UsersService {
    void save(Users users);
    Users getById(long id);
    Users getByEmail(String email);
    List<Users> getAllMan();
    List<Users> getAllWoman();
    List<Users> getAll();
    Users getCurrentUser();
}

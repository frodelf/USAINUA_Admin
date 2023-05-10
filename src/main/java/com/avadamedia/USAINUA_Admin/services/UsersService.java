package com.avadamedia.USAINUA_Admin.services;

import com.avadamedia.USAINUA_Admin.entity.User;

import java.util.List;

public interface UsersService {
    void save(User user);
    User getById(long id);
    User getByEmail(String email);
    List<User> getAllMan();
    List<User> getAllWoman();
    List<User> getAll();
    User getCurrentUser();
}

package com.avadamedia.USAINUA_Admin.mappers;

import com.avadamedia.USAINUA_Admin.entity.User;
import com.avadamedia.USAINUA_Admin.models.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public UserDTO toDto(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setMoney(String.valueOf(user.getMoney()));
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
    public User toEntity(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setMoney(Double.parseDouble(userDTO.getMoney()));
        user.setRoles(userDTO.getRoles());
        return user;
    }
    public List<UserDTO> toDtoList(List<User> users){
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(toDto(user));
        }
        return userDTOS;
    }
    public List<User> toEntityList(List<UserDTO> userDTOS){
        List<User> users = new ArrayList<>();
        for (UserDTO userDTO : userDTOS) {
            users.add(toEntity(userDTO));
        }
        return users;
    }
}

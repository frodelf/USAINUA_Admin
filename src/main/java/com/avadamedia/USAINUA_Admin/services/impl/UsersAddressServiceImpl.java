package com.avadamedia.USAINUA_Admin.services.impl;

import com.avadamedia.USAINUA_Admin.entity.UsersAddress;
import com.avadamedia.USAINUA_Admin.repositories.UsersAddressRepository;
import com.avadamedia.USAINUA_Admin.services.UsersAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersAddressServiceImpl implements UsersAddressService {
    private final UsersAddressRepository usersAddressRepository;

    public void save(UsersAddress usersAddress){usersAddressRepository.save(usersAddress);}
}

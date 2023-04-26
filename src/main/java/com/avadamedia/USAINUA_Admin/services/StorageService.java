package com.avadamedia.USAINUA_Admin.services;

import com.avadamedia.USAINUA_Admin.entity.Storage;

import java.util.List;

public interface StorageService {
    List<Storage> getAll();
    void save(Storage storage);
    void deleteById(long id);
    Storage getById(long id);
}

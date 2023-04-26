package com.avadamedia.USAINUA_Admin.services;

import com.avadamedia.USAINUA_Admin.entity.Shops;

import java.util.List;

public interface ShopsService {
    void save(Shops shops);
    List<Shops> getAll();
    Shops getByLink(String link);
    Shops getById(long id);
    void deleteById(long id);
}

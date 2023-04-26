package com.avadamedia.USAINUA_Admin.services;

import com.avadamedia.USAINUA_Admin.entity.Products;

import java.util.List;

public interface ProductsService {
    void save(Products products);
    List<Products> getAll();
    Products getByLink(String link);
    void deleteById(Long id);
    Products getById(long id);
}

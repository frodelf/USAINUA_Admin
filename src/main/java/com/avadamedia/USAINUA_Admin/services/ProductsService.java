package com.avadamedia.USAINUA_Admin.services;

import com.avadamedia.USAINUA_Admin.entity.Product;

import java.util.List;

public interface ProductsService {
    void save(Product product);
    List<Product> getAll();
    Product getByLink(String link);
    void deleteById(Long id);
    Product getById(long id);
}

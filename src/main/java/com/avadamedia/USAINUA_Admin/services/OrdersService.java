package com.avadamedia.USAINUA_Admin.services;

import com.avadamedia.USAINUA_Admin.entity.Orders;

import java.util.List;

public interface OrdersService {
    void save(Orders orders);
    List<Orders> getAll();
    Orders getById(long id);
}

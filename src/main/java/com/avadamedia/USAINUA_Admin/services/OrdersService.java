package com.avadamedia.USAINUA_Admin.services;

import com.avadamedia.USAINUA_Admin.entity.Order;

import java.util.List;

public interface OrdersService {
    void save(Order order);
    List<Order> getAll();
    Order getById(long id);
}

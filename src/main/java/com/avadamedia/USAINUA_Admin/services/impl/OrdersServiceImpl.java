package com.avadamedia.USAINUA_Admin.services.impl;

import com.avadamedia.USAINUA_Admin.entity.Order;
import com.avadamedia.USAINUA_Admin.repositories.OrdersRepository;
import com.avadamedia.USAINUA_Admin.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;

    public void save(Order order){ordersRepository.save(order);}
    public List<Order> getAll(){return ordersRepository.findAll();}
    public Order getById(long id){return ordersRepository.findById(id).get();}
}

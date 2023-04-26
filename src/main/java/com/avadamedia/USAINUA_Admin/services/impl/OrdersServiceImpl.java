package com.avadamedia.USAINUA_Admin.services.impl;

import com.avadamedia.USAINUA_Admin.entity.Orders;
import com.avadamedia.USAINUA_Admin.repositories.OrdersRepository;
import com.avadamedia.USAINUA_Admin.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;

    public void save(Orders orders){ordersRepository.save(orders);}
    public List<Orders> getAll(){return ordersRepository.findAll();}
    public Orders getById(long id){return ordersRepository.findById(id).get();}
}

package com.avadamedia.USAINUA_Admin.repositories;

import com.avadamedia.USAINUA_Admin.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order, Long> {
}

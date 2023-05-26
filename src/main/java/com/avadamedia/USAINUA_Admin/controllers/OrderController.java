package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.AdditionalService;
import com.avadamedia.USAINUA_Admin.entity.Order;
import com.avadamedia.USAINUA_Admin.enums.Status;
import com.avadamedia.USAINUA_Admin.enums.Transport;
import com.avadamedia.USAINUA_Admin.repositories.OrdersRepository;
import com.avadamedia.USAINUA_Admin.services.impl.AdditionalServicesServiceImpl;
import com.avadamedia.USAINUA_Admin.services.impl.OrdersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrdersServiceImpl ordersService;
    private final OrdersRepository ordersRepository;
    private final AdditionalServicesServiceImpl additionalServicesService;
    @GetMapping("/")
    public String getOrders(Model model) {
//        model.addAttribute("current", number);
//        if (number < 1) {
//            return "redirect:/admin/order/1";
//        }
//        int max = (int) Math.ceil(ordersService.getAll().size() / 5.0);
//        max = max == 0 ? 1 : max;
//        if (number > max) {
//            return "redirect:/admin/order/" + max;
//        }
//        model.addAttribute("max", max);
//        if (!ordersService.getAll().isEmpty()) {
//            Page<Order> orders = ordersRepository.findAll(PageRequest.of((number - 1), 5));
//            model.addAttribute("orders", orders);
//            return "admin/orders";
//        }
        model.addAttribute("orders", ordersService.getAll());
        return "admin/orders";
    }

    @GetMapping("/edit/{id}")
    public String orderEditStart(@PathVariable("id") Long id, Model model) {
        model.addAttribute("order_type", ordersService.getById(id).isOnlyDelivery() ? "Тільки доставка" : "Покупка і доставка");
        model.addAttribute("additionalServices", additionalServicesService.getAll());
        model.addAttribute("order", ordersService.getById(id));
        model.addAttribute("transport", Transport.getAll());
        return "admin/orders-edit";
    }

    @PostMapping("/edit/{type}/{id}")
    public String approximatePrice(@PathVariable("type") boolean type, @PathVariable("id") long id, @RequestParam("transport") String transport,
                                   @RequestParam("weight") String weight, @RequestParam("price") String price,
                                   @RequestParam("services") List<Long> additionalServicesId) {
        Order order = ordersService.getById(id);
        additionalServicesId.remove(0);
        List<AdditionalService> additionalServices = new ArrayList<>();
        for (Long aLong : additionalServicesId) {
            additionalServices.add(additionalServicesService.getById(aLong));
        }
        double totalPrice = 0;
        if (type) {
            for (AdditionalService additionalService : additionalServices) {
                totalPrice += additionalService.getPrice();
            }
            if (transport.equals("plane")) totalPrice += 0.5 * Double.valueOf(weight) + 1000;
            else if (transport.equals("ship")) totalPrice += 0.3 * Double.valueOf(weight) + 500;
            else totalPrice += 800;
        } else {

            for (AdditionalService additionalService : additionalServices) {
                totalPrice += additionalService.getPrice();
            }
            if (transport.equals("plane"))
                totalPrice += 0.1 * Double.valueOf(price) + 0.5 * Double.valueOf(weight) + 1000;
            else if (transport.equals("ship"))
                totalPrice += 0.05 * Double.valueOf(price) + 0.3 * Double.valueOf(weight) + 500;
            else totalPrice += 800;
        }
        order.setStatus(Status.READY_FOR_PAYMENT.getStatus());
        order.setTotalPrice(totalPrice);
        ordersService.save(order);
        return "redirect:/admin/order/edit/" + id;
    }

    @GetMapping("/edit/status/{id}")
    public String editOrderStatus(@PathVariable("id")long id){
        Order order = ordersService.getById(id);
        order.setStatus(Status.CANCEL.getStatus());
        ordersService.save(order);
        return "redirect:/admin/order/";
    }
}

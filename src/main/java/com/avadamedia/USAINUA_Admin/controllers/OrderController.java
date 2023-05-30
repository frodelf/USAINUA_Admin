package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.AdditionalService;
import com.avadamedia.USAINUA_Admin.entity.Order;
import com.avadamedia.USAINUA_Admin.enums.Status;
import com.avadamedia.USAINUA_Admin.enums.Transport;
import com.avadamedia.USAINUA_Admin.repositories.OrdersRepository;
import com.avadamedia.USAINUA_Admin.services.impl.AdditionalServicesServiceImpl;
import com.avadamedia.USAINUA_Admin.services.impl.OrdersServiceImpl;
import com.avadamedia.USAINUA_Admin.util.CalculatorUtil;
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
                                   @RequestBody List<Long> additionalServicesId) {
        Order order = ordersService.getById(id);
        List<AdditionalService> additionalServices = new ArrayList<>();
        for (Long aLong : additionalServicesId) {
            additionalServices.add(additionalServicesService.getById(aLong));
        }
        double totalPrice;
        if (type) {
            totalPrice = CalculatorUtil.deliveryApproximatePrice(Double.parseDouble(weight), additionalServices, transport);
        } else {
            totalPrice = CalculatorUtil.purchaseAndDeliveryApproximatePrice(Double.parseDouble(weight), additionalServices, transport , Double.parseDouble(price));
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

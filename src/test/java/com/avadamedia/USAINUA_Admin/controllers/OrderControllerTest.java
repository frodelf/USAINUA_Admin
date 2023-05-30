package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.AdditionalService;
import com.avadamedia.USAINUA_Admin.entity.Order;
import com.avadamedia.USAINUA_Admin.enums.Transport;
import com.avadamedia.USAINUA_Admin.repositories.AdditionalServicesRepository;
import com.avadamedia.USAINUA_Admin.repositories.OrdersRepository;
import com.avadamedia.USAINUA_Admin.repositories.UsersRepository;
import com.avadamedia.USAINUA_Admin.services.AdditionalServicesService;
import com.avadamedia.USAINUA_Admin.services.OrdersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrdersRepository ordersRepository;
    @MockBean
    private AdditionalServicesRepository additionalServicesRepository;
    @MockBean
    private UsersRepository usersRepository;


    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testGetOrders() throws Exception {
        List<Order> orderList = Arrays.asList(
                new Order(),
                new Order()
        );
        when(ordersRepository.findAll()).thenReturn(orderList);

        mockMvc.perform(get("/admin/order/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/orders"))
                .andExpect(model().attribute("orders", orderList));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testOrderEditStart() throws Exception {
        long id = 1L;
        Order order = new Order();

        List<AdditionalService> additionalServicesList = Arrays.asList(
                new AdditionalService(1L, "Service 1", 10.5),
                new AdditionalService(2L, "Service 2", 20.0)
        );
        when(ordersRepository.findById(id)).thenReturn(Optional.of(order));
        when(additionalServicesRepository.findAll()).thenReturn(additionalServicesList);

        mockMvc.perform(get("/admin/order/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/orders-edit"))
                .andExpect(model().attribute("order_type", "Покупка і доставка"))
                .andExpect(model().attribute("additionalServices", additionalServicesList))
                .andExpect(model().attribute("order", order))
                .andExpect(model().attribute("transport", Transport.getAll()));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testApproximatePrice() throws Exception {
        long id = 1L;
        String transport = "Transport 1";
        String weight = "10.5";
        String price = "100.0";
        List<Long> additionalServicesId = Arrays.asList(1L);
        Order order = new Order();
        when(ordersRepository.findById(id)).thenReturn(Optional.of(order));
        when(additionalServicesRepository.findById(anyLong())).thenReturn(Optional.of(new AdditionalService(1L, "qwerty", 12.5)));

        mockMvc.perform(post("/admin/order/edit/true/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(additionalServicesId))
                        .param("transport", "plane")
                        .param("weight", weight)
                        .param("price", price))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/order/edit/" + id));

        mockMvc.perform(post("/admin/order/edit/false/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(additionalServicesId))
                        .param("transport", "plane")
                        .param("weight", weight)
                        .param("price", price))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/order/edit/" + id));

        verify(ordersRepository, times(2)).save(any());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testEditOrderStatus() throws Exception {
        long id = 1L;
        Order order = new Order();
        when(ordersRepository.findById(id)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/admin/order/edit/status/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/order/"));

        verify(ordersRepository, times(1)).save(any());
    }

}

package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.Shop;
import com.avadamedia.USAINUA_Admin.models.ShopDTO;
import com.avadamedia.USAINUA_Admin.repositories.ShopsRepository;
import com.avadamedia.USAINUA_Admin.repositories.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShopsRepository shopRepository;
    @MockBean
    private UsersRepository usersRepository;

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testGetShops() throws Exception {
        List<Shop> shopList = Arrays.asList(
                new Shop(1L, "Shop 1", "Address 1"),
                new Shop(2L, "Shop 2", "Address 2")
        );
        when(shopRepository.findAll()).thenReturn(shopList);

        mockMvc.perform(get("/admin/shop/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/shops"))
                .andExpect(model().attribute("shops", shopList));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testShopAddStart() throws Exception {
        mockMvc.perform(get("/admin/shop/add/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/shops-add"))
                .andExpect(model().attributeExists("shop"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testShopAddEnd() throws Exception {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setLink("http://some.link");
        shopDTO.setImageName("image1");

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "Test Image".getBytes());

        mockMvc.perform(multipart("/admin/shop/add/")
                        .file(image)
                        .flashAttr("shop", shopDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/shop/"));


        mockMvc.perform(multipart("/admin/shop/add/")
                        .file(image)
                        .flashAttr("shop", new ShopDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/shops-add"));

        mockMvc.perform(multipart("/admin/shop/add/")
                        .file(new MockMultipartFile("image", new byte[0]))
                        .flashAttr("shop", new ShopDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/shops-add"));

        verify(shopRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testShopEditStart() throws Exception {
        long id = 1L;
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setLink("http://some.link");
        shopDTO.setImageName("image1");
        when(shopRepository.findById(id)).thenReturn(Optional.of(new Shop(id, "http://some.link", "image1")));

        mockMvc.perform(get("/admin/shop/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/shops-edit"))
                .andExpect(model().attribute("id", id))
                .andExpect(model().attribute("shop", shopDTO));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testShopEditEnd() throws Exception {
        long id = 1L;
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setLink("http://some.link");
        shopDTO.setImageName("image1");

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "Test Image".getBytes());

        when(shopRepository.findById(id)).thenReturn(Optional.of(new Shop(id, "http://some.link", "image1")));

        mockMvc.perform(multipart("/admin/shop/edit/{id}", id)
                        .file(image)
                        .flashAttr("shop", shopDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/shop/"));

        mockMvc.perform(multipart("/admin/shop/edit/{id}", id)
                        .file(new MockMultipartFile("image", new byte[0]))
                        .flashAttr("shop", shopDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/shop/"));

        mockMvc.perform(multipart("/admin/shop/edit/{id}", id)
                        .file(image)
                        .flashAttr("shop", new ShopDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/shops-edit"));

        verify(shopRepository, times(2)).save(any());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testShopDeleteById() throws Exception {
        long id = 1L;

        mockMvc.perform(post("/admin/shop/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/shop/"));

        verify(shopRepository, times(1)).deleteById(id);
    }

}

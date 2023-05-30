package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.Product;
import com.avadamedia.USAINUA_Admin.enums.Type;
import com.avadamedia.USAINUA_Admin.models.ProductDTO;
import com.avadamedia.USAINUA_Admin.repositories.ProductsRepository;
import com.avadamedia.USAINUA_Admin.repositories.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.pool.TypePool;
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
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductsRepository productsRepository;
    @MockBean
    private UsersRepository usersRepository;


    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testGetProducts() throws Exception {
        List<Product> productList = Arrays.asList(
                new Product(1L, "Product 1", 12.5, Type.Clothes.name(), "http://some.link","image1"),
                new Product(2L, "Product 2", 23.5, Type.Gadgets.name(), "http://some.link", "image2")
        );
        when(productsRepository.findAll()).thenReturn(productList);

        mockMvc.perform(get("/admin/product/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/products"))
                .andExpect(model().attribute("products", productList));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testProductAddStart() throws Exception {
        mockMvc.perform(get("/admin/product/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/products-add"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("types"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testProductAddEnd() throws Exception {
        ProductDTO productDTO = new ProductDTO("Product 1", "12.5", Type.Clothes.name(), "http://some.link", "image1");

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "Test Image".getBytes());

        mockMvc.perform(multipart("/admin/product/add")
                        .file(image)
                        .flashAttr("product", productDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/product/"));

        mockMvc.perform(multipart("/admin/product/add")
                        .file(image)
                        .flashAttr("product", new ProductDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/products-add"));
        mockMvc.perform(multipart("/admin/product/add")
                        .file(new MockMultipartFile("image", new byte[0]))
                        .flashAttr("product", new ProductDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/products-add"));

        verify(productsRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testProductEditStart() throws Exception {
        long id = 1L;
        ProductDTO productDTO = new ProductDTO("Product 1", "12.5", Type.Clothes.name(), "http://some.link", "image1");

        when(productsRepository.findById(id)).thenReturn(Optional.of(new Product(id, "Product 1", 12.5, Type.Clothes.name(), "http://some.link", "image1")));

        mockMvc.perform(get("/admin/product/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/products-edit"))
                .andExpect(model().attribute("id", id))
                .andExpect(model().attribute("product", productDTO))
                .andExpect(model().attributeExists("types"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testProductEditEnd() throws Exception {
        long id = 1L;
        ProductDTO productDTO = new ProductDTO("Product 1", "12.5", Type.Clothes.name(), "http://some.link", "image1");

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "Test Image".getBytes());

        mockMvc.perform(multipart("/admin/product/edit/{id}", id)
                        .file(image)
                        .flashAttr("product", productDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/product/"));

        mockMvc.perform(multipart("/admin/product/edit/{id}", id)
                        .file(image)
                        .flashAttr("product", new ProductDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/products-edit"));

        verify(productsRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testDeleteById() throws Exception {
        long id = 1L;

        mockMvc.perform(post("/admin/product/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/product/"));

        verify(productsRepository, times(1)).deleteById(id);
    }
}

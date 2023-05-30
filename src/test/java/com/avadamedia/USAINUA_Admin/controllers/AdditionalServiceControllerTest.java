package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.AdditionalService;
import com.avadamedia.USAINUA_Admin.mappers.AdditionalServiceMapper;
import com.avadamedia.USAINUA_Admin.models.AdditionalServiceDTO;
import com.avadamedia.USAINUA_Admin.repositories.AdditionalServicesRepository;
import com.avadamedia.USAINUA_Admin.repositories.UsersRepository;
import com.avadamedia.USAINUA_Admin.services.impl.AdditionalServicesServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdditionalServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AdditionalServicesRepository additionalServicesRepository;
    @MockBean
    private UsersRepository usersRepository;

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testAdditionalServices() throws Exception {
        List<AdditionalService> services = Arrays.asList(
                new AdditionalService(1L, "Service 1", 12.4),
                new AdditionalService(2L, "Service 2", 32.5)
        );
        when(additionalServicesRepository.findAll()).thenReturn(services);

        mockMvc.perform(get("/admin/additional-service/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/additional-services"))
                .andExpect(model().attribute("services", services));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})

    public void testAddAdditionalServicesStart() throws Exception {
        mockMvc.perform(get("/admin/additional-service/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/additional-services-add"))
                .andExpect(model().attributeExists("service"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testAddAdditionalServicesEnd() throws Exception {
        AdditionalServiceDTO additionalServiceDTO = new AdditionalServiceDTO();
        additionalServiceDTO.setName("Service 1");
        additionalServiceDTO.setPrice("12.5");

        mockMvc.perform(post("/admin/additional-service/add")
                        .flashAttr("service", additionalServiceDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/additional-service/"));

        mockMvc.perform(post("/admin/additional-service/add")
                        .flashAttr("service", new AdditionalServiceDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/additional-services-add"));

        verify(additionalServicesRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testAdditionalServicesEditStart() throws Exception {
        Long id = 1L;
        AdditionalServiceDTO additionalServiceDTO = new AdditionalServiceDTO();
        additionalServiceDTO.setName("Service 1");
        additionalServiceDTO.setPrice("23.5");
        when(additionalServicesRepository.findById(id)).thenReturn(Optional.of(new AdditionalService(id, "Service 1", 23.5)));

        mockMvc.perform(get("/admin/additional-service/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/additional-services-edit"))
                .andExpect(model().attribute("id", id))
                .andExpect(model().attribute("service", additionalServiceDTO));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testAdditionalServicesEditEnd() throws Exception {
        Long id = 1L;
        AdditionalServiceDTO additionalServiceDTO = new AdditionalServiceDTO();
        additionalServiceDTO.setName("Service 1");
        additionalServiceDTO.setPrice("23.5");

        mockMvc.perform(post("/admin/additional-service/edit/{id}", id)
                        .flashAttr("service", additionalServiceDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/additional-service/"));
        mockMvc.perform(post("/admin/additional-service/edit/{id}", id)
                        .flashAttr("service", new AdditionalServiceDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/additional-services-edit"));

        verify(additionalServicesRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testAdditionalServicesDeleteEnd() throws Exception {
        Long id = 1L;

        mockMvc.perform(post("/admin/additional-service/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/additional-service/"));

        verify(additionalServicesRepository, times(1)).deleteById(id);
    }
}

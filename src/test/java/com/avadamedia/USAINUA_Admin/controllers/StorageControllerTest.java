package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.Storage;
import com.avadamedia.USAINUA_Admin.models.StorageDTO;
import com.avadamedia.USAINUA_Admin.repositories.StorageRepository;
import com.avadamedia.USAINUA_Admin.repositories.UsersRepository;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StorageRepository storageRepository;
    @MockBean
    private UsersRepository usersRepository;

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testGetStorages() throws Exception {
        List<Storage> storageList = Arrays.asList(
                new Storage(1L, "Storage1","some street", "some city", "some state", "09811", "0987654321"),
                new Storage(2L, "Storage2","street2", "city2", "state2", "09811", "098765d4321")
        );
        when(storageRepository.findAll()).thenReturn(storageList);

        mockMvc.perform(get("/admin/storage/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/storages"))
                .andExpect(model().attribute("storages", storageList));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testStorageAddStart() throws Exception {
        mockMvc.perform(get("/admin/storage/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/storage-add"))
                .andExpect(model().attributeExists("storage"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testStorageAddEnd() throws Exception {
        StorageDTO storageDTO = new StorageDTO();
        storageDTO.setCity("city");
        storageDTO.setPhone("0987654321");
        storageDTO.setState("state");
        storageDTO.setIndex("1234");
        storageDTO.setStreet("street");
        storageDTO.setFullName("full name");

        mockMvc.perform(post("/admin/storage/add")
                        .flashAttr("storage", storageDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/storage/"));

        verify(storageRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testStorageEditStart() throws Exception {
        long id = 1L;
        StorageDTO storageDTO = new StorageDTO();
        storageDTO.setCity("some city");
        storageDTO.setPhone("0987654321");
        storageDTO.setState("some state");
        storageDTO.setIndex("09811");
        storageDTO.setStreet("some street");
        storageDTO.setFullName("Storage1");
        when(storageRepository.findById(id)).thenReturn(Optional.of(new Storage(1L, "Storage1","some street", "some city", "some state", "09811", "0987654321")));

        mockMvc.perform(get("/admin/storage/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/storage-edit"))
                .andExpect(model().attribute("id", id))
                .andExpect(model().attribute("storage", storageDTO));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testStorageEditEnd() throws Exception {
        long id = 1L;
        StorageDTO storageDTO = new StorageDTO();
        storageDTO.setCity("city");
        storageDTO.setPhone("0987654321");
        storageDTO.setState("state");
        storageDTO.setIndex("1234");
        storageDTO.setStreet("street");
        storageDTO.setFullName("full name");
        mockMvc.perform(post("/admin/storage/edit/{id}", id)
                        .flashAttr("storage", storageDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/storage/"));

        verify(storageRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testStorageDeleteById() throws Exception {
        long id = 1L;

        mockMvc.perform(post("/admin/storage/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/storage/"));

        verify(storageRepository, times(1)).deleteById(id);
    }

}

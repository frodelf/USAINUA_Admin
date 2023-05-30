package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.Role;
import com.avadamedia.USAINUA_Admin.entity.User;
import com.avadamedia.USAINUA_Admin.models.UserDTO;
import com.avadamedia.USAINUA_Admin.repositories.RolesRepository;
import com.avadamedia.USAINUA_Admin.repositories.UsersRepository;
import com.avadamedia.USAINUA_Admin.services.impl.UsersServiceImpl;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersServiceImpl usersService;
    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private RolesRepository rolesRepository;

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testGetUsers() throws Exception {
        List<UserDTO> userList = Arrays.asList(
                new UserDTO(1L, "some1.email@email.com", "1200.5", "0987654321", Arrays.asList(new Role(1L, "ROLE_ADMIN"))),
                new UserDTO(2L, "some2.email@email.com", "1250.5", "0987656789", Arrays.asList(new Role(1L, "ROLE_USER")))
        );
        User user1 = new User();
        user1.setId(2L);
        user1.setEmail("some2.email@email.com");
        user1.setMoney(1250.5);
        user1.setPhone("0987656789");
        user1.setRoles(Arrays.asList(new Role(1L, "ROLE_USER")));
        when(usersService.getOnlyUser()).thenReturn(Arrays.asList(user1));

        mockMvc.perform(get("/admin/user/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user"))
                .andExpect(model().attribute("users", Arrays.asList(new UserDTO(2L, "some2.email@email.com", "1250.5", "0987656789", Arrays.asList(new Role(1L, "ROLE_USER"))))));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testGetAdmins() throws Exception {
        List<UserDTO> userList = Arrays.asList(
                new UserDTO(1L, "some1.email@email.com", "1200.5", "0987654321", Arrays.asList(new Role(1L, "ROLE_ADMIN"))),
                new UserDTO(2L, "some2.email@email.com", "1250.5", "0987656789", Arrays.asList(new Role(1L, "ROLE_USER")))
        );
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("some1.email@email.com");
        user1.setMoney(1200.5);
        user1.setPhone("0987654321");
        user1.setRoles(Arrays.asList(new Role(1L, "ROLE_ADMIN")));
        when(usersService.getOnlyAdmin()).thenReturn(Arrays.asList(user1));

        mockMvc.perform(get("/admin/user/admins/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin"))
                .andExpect(model().attribute("admins", Arrays.asList(new UserDTO(1L, "some1.email@email.com", "1200.5", "0987654321", Arrays.asList(new Role(1L, "ROLE_ADMIN"))))));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testEditUserStart() throws Exception {
        long id = 1L;
        UserDTO userDTO = new UserDTO(2L, "some2.email@email.com", "1250.5", "0987656789", Arrays.asList(new Role(1L, "ROLE_USER")));
        List<Role> roleList = Arrays.asList(
                new Role(1L, "ROLE_ADMIN"),
                new Role(2L, "ROLE_USER")
        );
        User user1 = new User();
        user1.setId(2L);
        user1.setEmail("some2.email@email.com");
        user1.setMoney(1250.5);
        user1.setPhone("0987656789");
        user1.setRoles(Arrays.asList(new Role(1L, "ROLE_USER")));
        when(usersService.getById(id)).thenReturn(user1);
        when(rolesRepository.findAll()).thenReturn(roleList);

        mockMvc.perform(get("/admin/user/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user-edit"))
                .andExpect(model().attribute("user", userDTO))
                .andExpect(model().attribute("roles", roleList));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testEditUserEnd() throws Exception {
        long id = 1L;
        Role role = new Role(1L, "ROLE_USER");

        UserDTO userDTO = new UserDTO(1L, "some1.email@email.com", "1200.5", "0987654321", Arrays.asList(role));
        User user1 = new User();
        user1.setId(2L);
        user1.setEmail("some2.email@email.com");
        user1.setMoney(1250.5);
        user1.setPhone("0987656789");
        user1.setRoles(Arrays.asList(role));

        when(usersService.getById(id)).thenReturn(user1);
        when(rolesRepository.findById(id)).thenReturn(Optional.of(role));

        mockMvc.perform(post("/admin/user/edit/{id}", id)
                        .flashAttr("user", userDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/user/admins/"));

        mockMvc.perform(post("/admin/user/edit/{id}", id)
                        .flashAttr("user", new UserDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user-edit"));

        when(usersService.getById(id)).thenReturn(user1);
        when(rolesRepository.findById(id)).thenReturn(Optional.of(new Role(2L, "ROLE_ADMIN")));

        mockMvc.perform(post("/admin/user/edit/{id}", id)
                        .flashAttr("user", userDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/user/"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testDeleteUserById() throws Exception {
        long id = 1L;

        mockMvc.perform(post("/admin/user/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/user/"));

        verify(usersService, times(1)).deleteById(id);
    }

}

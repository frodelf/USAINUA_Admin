package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.News;
import com.avadamedia.USAINUA_Admin.models.NewsDTO;
import com.avadamedia.USAINUA_Admin.repositories.NewsRepository;
import com.avadamedia.USAINUA_Admin.repositories.UsersRepository;
import com.avadamedia.USAINUA_Admin.services.impl.NewsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
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
public class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private UsersRepository usersRepository;
    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testGetNews() throws Exception {
        List<News> newsList = Arrays.asList(
                new News(1L, "News 1", "Content 1", new Date(1)),
                new News(2L, "News 2", "Content 2", new Date(2))
        );
        when(newsRepository.findAll()).thenReturn(newsList);

        mockMvc.perform(get("/admin/news/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/news"))
                .andExpect(model().attribute("news", newsList));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testNewsAddStart() throws Exception {
        mockMvc.perform(get("/admin/news/add/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/news-add"))
                .andExpect(model().attributeExists("news"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testNewsAddEnd() throws Exception {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("News 1");
        newsDTO.setText("Some text 1");

        mockMvc.perform(post("/admin/news/add/")
                        .flashAttr("news", newsDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/news/"));

        mockMvc.perform(post("/admin/news/add/")
                        .flashAttr("news", new NewsDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/news-add"));

        verify(newsRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testNewsEditStart() throws Exception {
        Long id = 1L;
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("News 1");
        newsDTO.setText("Some text 1");
        when(newsRepository.findById(id)).thenReturn(Optional.of(new News(id, "News 1", "Some text 1", new Date(1))));

        mockMvc.perform(get("/admin/news/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/news-edit"))
                .andExpect(model().attribute("id", id))
                .andExpect(model().attribute("news", newsDTO));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testNewsEditEnd() throws Exception {
        Long id = 1L;
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("News 1");
        newsDTO.setText("Some text 1");

        mockMvc.perform(post("/admin/news/edit/{id}", id)
                        .flashAttr("news", newsDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/news/"));

        mockMvc.perform(post("/admin/news/edit/{id}", id)
                        .flashAttr("news", new NewsDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/news-edit"));

        verify(newsRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    public void testNewsDelete() throws Exception {
        Long id = 1L;

        mockMvc.perform(post("/admin/news/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/news/"));

        verify(newsRepository, times(1)).deleteById(id);
    }
}

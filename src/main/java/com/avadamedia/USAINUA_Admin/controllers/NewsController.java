package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.News;
import com.avadamedia.USAINUA_Admin.mappers.NewsMapper;
import com.avadamedia.USAINUA_Admin.models.AdditionalServiceDTO;
import com.avadamedia.USAINUA_Admin.models.NewsDTO;
import com.avadamedia.USAINUA_Admin.repositories.NewsRepository;
import com.avadamedia.USAINUA_Admin.services.impl.NewsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsServiceImpl newsService;
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    @GetMapping("/")
    public String getNews(Model model) {
//        model.addAttribute("current", number);
//        if (number < 1) {
//            return "redirect:/admin/news/1";
//        }
//        int max = (int) Math.ceil(newsService.getAll().size() / 5.0);
//        max = max == 0 ? 1 : max;
//        if (number > max) {
//            return "redirect:/admin/news/" + max;
//        }
//        model.addAttribute("max", max);
//        if (!newsService.getAll().isEmpty()) {
//            Page<News> news = newsRepository.findAll(PageRequest.of((number - 1), 5));
//            model.addAttribute("news", news);
//            return "admin/news";
//        }
        model.addAttribute("news", newsService.getAll());
        return "admin/news";
    }

    @GetMapping("/add/")
    public String newsAddStart(@ModelAttribute("news")NewsDTO newsDTO) {
        return "admin/news-add";
    }

    @PostMapping("/add/")
    public String newsAddEnd(@ModelAttribute("news") @Valid NewsDTO newsDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "admin/news-add";
        }
        newsService.save(newsMapper.toEntity(newsDTO));
        return "redirect:/admin/news/1";
    }

    @GetMapping("/edit/{id}")
    public String newsEditStart(@PathVariable("id") Long id, Model model) {
        NewsDTO newsDTO = newsMapper.toDto(newsService.getById(id));
        model.addAttribute("id", id);
        model.addAttribute("news", newsDTO);
        return "admin/news-edit";
    }

    @PostMapping("/edit/{id}")
    public String newsEditEnd(@ModelAttribute("news") @Valid NewsDTO newsDTO, BindingResult bindingResult, @PathVariable("id") Long id ,Model model) {
        if(bindingResult.hasErrors()){
            return "admin/news-edit";
        }
        News news = newsMapper.toEntity(newsDTO);
        news.setId(id);
        newsService.save(news);
        return "redirect:/admin/news/1";
    }

    @PostMapping("/delete/{id}")
    public String newsDelete(@PathVariable("id") Long id) {
        newsService.deleteById(id);
        return "redirect:/admin/news/1";
    }
}

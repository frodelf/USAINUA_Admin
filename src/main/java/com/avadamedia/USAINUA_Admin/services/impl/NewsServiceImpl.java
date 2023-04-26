package com.avadamedia.USAINUA_Admin.services.impl;

import com.avadamedia.USAINUA_Admin.entity.News;
import com.avadamedia.USAINUA_Admin.repositories.NewsRepository;
import com.avadamedia.USAINUA_Admin.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    public News getById(long id){
        return newsRepository.findById(id).get();
    }

    public List<News> getAll(){
        return newsRepository.findAll();
    }

    public void save(News news){
        newsRepository.save(news);
    }

    public void deleteById(long id){
        newsRepository.deleteById(id);
    }
}

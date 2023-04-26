package com.avadamedia.USAINUA_Admin.services;

import com.avadamedia.USAINUA_Admin.entity.News;

import java.util.List;

public interface NewsService {
    News getById(long id);
    List<News> getAll();
    void save(News news);
    void deleteById(long id);
}

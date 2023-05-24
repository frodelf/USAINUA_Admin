package com.avadamedia.USAINUA_Admin.mappers;

import com.avadamedia.USAINUA_Admin.entity.AdditionalService;
import com.avadamedia.USAINUA_Admin.entity.News;
import com.avadamedia.USAINUA_Admin.models.AdditionalServiceDTO;
import com.avadamedia.USAINUA_Admin.models.NewsDTO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewsMapper {
    public NewsDTO toDto(News news){
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle(news.getTitle());
        newsDTO.setText(news.getText());
        return newsDTO;
    }
    public News toEntity(NewsDTO newsDTO){
        News news = new News();
        news.setText(newsDTO.getText());
        news.setTitle(newsDTO.getTitle());
        news.setDate(Date.valueOf(LocalDate.now()));
        return news;
    }
    public List<NewsDTO> toDtoList(List<News> news){
        ArrayList<NewsDTO> newsDTOS = new ArrayList<>();
        for (News news1 : news) {
            newsDTOS.add(toDto(news1));
        }
        return newsDTOS;
    }
    public List<News> toEntityList(List<NewsDTO> newsDTO){
        ArrayList<News> news = new ArrayList<>();
        for (NewsDTO dto : newsDTO) {
            news.add(toEntity(dto));
        }
        return news;
    }
}

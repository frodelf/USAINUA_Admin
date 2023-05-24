package com.avadamedia.USAINUA_Admin.config;

import com.avadamedia.USAINUA_Admin.mappers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public AdditionalServiceMapper additionalServiceMapper () {
        return new AdditionalServiceMapper();
    }
    @Bean
    public NewsMapper newsMapper () {
        return new NewsMapper();
    }
    @Bean
    public ShopMapper shopMapper () {
        return new ShopMapper();
    }
    @Bean
    public ProductMapper productMapper () {
        return new ProductMapper();
    }
    @Bean
    public StorageMapper storageMapper () {
        return new StorageMapper();
    }
    @Bean
    public UserMapper userMapper () {
        return new UserMapper();
    }
}

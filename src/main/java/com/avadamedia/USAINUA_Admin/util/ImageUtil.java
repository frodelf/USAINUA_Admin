package com.avadamedia.USAINUA_Admin.util;

import com.avadamedia.USAINUA_Admin.entity.Products;
import com.avadamedia.USAINUA_Admin.entity.Shops;
import com.avadamedia.USAINUA_Admin.services.impl.ProductsServiceImpl;
import com.avadamedia.USAINUA_Admin.services.impl.ShopsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
@Log4j2
@RequiredArgsConstructor
public class ImageUtil {
    private final ShopsServiceImpl shopsServiceImpl;
    private final ProductsServiceImpl productsServiceImpl;
    public static void imageForShop(Shops shop, MultipartFile image) throws IOException {
        try {
            Path uploadPath = Paths.get("kino.avada-media-dev1.od.ua/USAINUA_Admin/shops");
            String originalFilename = image.getOriginalFilename();
            String format = originalFilename.substring(originalFilename.lastIndexOf("."));
            String nameImage = generateName() + format;
            Files.copy(image.getInputStream(), uploadPath.resolve(nameImage));
            shop.setImageName(nameImage);
        }catch (Exception e){
            throw new RuntimeException("shop");
        }
    }

    public static void imageForProducts(Products products, MultipartFile image) throws IOException {
        try {
            Path uploadPath = Paths.get("tomcat/webapps/USAINUA_Admin/products");
            String originalFilename = image.getOriginalFilename();
            String format = originalFilename.substring(originalFilename.lastIndexOf("."));
            String nameImage = generateName() + format;
            Files.copy(image.getInputStream(), uploadPath.resolve(nameImage));
            products.setImageName(nameImage);
        }catch (Exception e){
            throw new RuntimeException("products");
        }
    }

    public static String generateName() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString();
    }

    public static void deleteImage(String name){
        File file = new File(name);
        file.delete();
    }
}

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
        Path uploadPath = Paths.get("uploads/shops");
        String originalFilename = image.getOriginalFilename();
        String format = originalFilename.substring(originalFilename.lastIndexOf("."));
        String nameImage = generateName()+format;
        Files.copy(image.getInputStream(), uploadPath.resolve(nameImage));
        log.info(String.valueOf(uploadPath.resolve(shop.getImageName())));
        deleteImage(String.valueOf(uploadPath.resolve(shop.getImageName())));
        shop.setImageName(nameImage);
    }

    public static void imageForProducts(Products products, MultipartFile image) throws IOException {
        Path uploadPath = Paths.get("uploads/products");
        String originalFilename = image.getOriginalFilename();
        String format = originalFilename.substring(originalFilename.lastIndexOf("."));
        String nameImage = generateName()+format;
        Files.copy(image.getInputStream(), uploadPath.resolve(nameImage));
        log.info(String.valueOf(uploadPath.resolve(products.getImageName())));
        deleteImage(String.valueOf(uploadPath.resolve(products.getImageName())));
        products.setImageName(nameImage);
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

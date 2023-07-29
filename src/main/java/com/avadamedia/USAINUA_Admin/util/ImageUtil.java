package com.avadamedia.USAINUA_Admin.util;

import com.avadamedia.USAINUA_Admin.entity.Product;
import com.avadamedia.USAINUA_Admin.entity.Shop;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Log4j2
@RequiredArgsConstructor
public class ImageUtil {
    public static String imageForShop(Shop shop, MultipartFile image) throws IOException {
        String nameImage ="";
        try {
            Path uploadPath = Paths.get("/home/avada/web/slj.avada-media-dev1.od.ua/projects/USAINUA/uploads/shops");
        String originalFilename = image.getOriginalFilename();
        String format = originalFilename.substring(originalFilename.lastIndexOf("."));
        nameImage = generateName() + format;
            Files.copy(image.getInputStream(), uploadPath.resolve(nameImage));
            deleteImage(String.valueOf(uploadPath.resolve(shop.getImageName())));
        } catch (Exception e) {
            log.info(e);
        }
        return nameImage;
    }

    public static String imageForProducts(Product products, MultipartFile image) throws IOException {
        String nameImage = "";
        try {
            Path uploadPath = Paths.get("/home/avada/web/slj.avada-media-dev1.od.ua/projects/USAINUA/uploads/products");
            String originalFilename = image.getOriginalFilename();
            String format = originalFilename.substring(originalFilename.lastIndexOf("."));
            nameImage = generateName() + format;
            Files.copy(image.getInputStream(), uploadPath.resolve(nameImage));
            deleteImage(String.valueOf(uploadPath.resolve(products.getImageName())));
            products.setImageName(nameImage);
        } catch (Exception e) {
            log.info(e);
        }
        return nameImage;
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

    public static void deleteImage(String name) {
        new File(name).delete();
    }
}
package com.avadamedia.USAINUA_Admin.mappers;

import com.avadamedia.USAINUA_Admin.entity.Shop;
import com.avadamedia.USAINUA_Admin.models.ShopDTO;

import java.util.ArrayList;
import java.util.List;

public class ShopMapper {
    public ShopDTO toDto(Shop shop){
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setLink(shop.getLink());
        shopDTO.setImageName(shop.getImageName());
        return shopDTO;
    }
    public Shop toEntity(ShopDTO shopDTO){
        Shop shop = new Shop();
        shop.setLink(shopDTO.getLink());
        shop.setImageName(shopDTO.getImageName());
        return shop;
    }
    public List<ShopDTO> toDtoList(List<Shop> shops){
        ArrayList<ShopDTO> shopDTOS = new ArrayList<>();
        for (Shop shop : shops) {
            shopDTOS.add(toDto(shop));
        }
        return shopDTOS;
    }
    public List<Shop> toEntityList(List<ShopDTO> shopDTOS){
        ArrayList<Shop> shops = new ArrayList<>();
        for (ShopDTO shopDTO : shopDTOS) {
            shops.add(toEntity(shopDTO));
        }
        return shops;
    }
}

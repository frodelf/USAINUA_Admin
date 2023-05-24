package com.avadamedia.USAINUA_Admin.mappers;

import com.avadamedia.USAINUA_Admin.entity.Product;
import com.avadamedia.USAINUA_Admin.models.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {
    public ProductDTO toDto(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice().toString());
        productDTO.setType(product.getType());
        productDTO.setLink(product.getLink());
        productDTO.setImageName(product.getImageName());
        return productDTO;
    }
    public Product toEntity(ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(Double.valueOf(productDTO.getPrice()));
        product.setType(productDTO.getType());
        product.setLink(productDTO.getLink());
        product.setImageName(productDTO.getImageName());
        return product;
    }
    public List<ProductDTO> toDtoList(List<Product> products){
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            productDTOS.add(toDto(product));
        }
        return productDTOS;
    }
    public List<Product> toEntityList(List<ProductDTO> productDTOS){
        List<Product> products = new ArrayList<>();
        for (ProductDTO productDTO : productDTOS) {
            products.add(toEntity(productDTO));
        }
        return products;
    }
}

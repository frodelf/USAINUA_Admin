package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.Product;
import com.avadamedia.USAINUA_Admin.entity.Shop;
import com.avadamedia.USAINUA_Admin.enums.Type;
import com.avadamedia.USAINUA_Admin.mappers.ProductMapper;
import com.avadamedia.USAINUA_Admin.models.ProductDTO;
import com.avadamedia.USAINUA_Admin.repositories.ProductsRepository;
import com.avadamedia.USAINUA_Admin.services.impl.ProductsServiceImpl;
import com.avadamedia.USAINUA_Admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductsServiceImpl productsService;
    private final ProductsRepository productsRepository;
    private final ProductMapper productMapper;
    @GetMapping("/")
    public String products(Model model) {
        model.addAttribute("products", productsService.getAll());
        return "admin/products";
    }

    @GetMapping("/add")
    public String productAddStart(@ModelAttribute("product") ProductDTO productDTO, Model model) {
        model.addAttribute("types", Type.values());
        return "admin/products-add";
    }

    @PostMapping("/add")
    public String productAddEnd(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult bindingResult,
                                @RequestParam("image") MultipartFile image, Model model) throws IOException {
        if(bindingResult.hasErrors() || image.isEmpty()){
            if(image.isEmpty()){
                model.addAttribute("error", "Фото повино бути завантажено");
            }
            model.addAttribute("types", Type.values());
            return "admin/products-add";
        }
        Product product = productMapper.toEntity(productDTO);
        product.setImageName(ImageUtil.imageForProducts(product, image));
        productsService.save(product);
        return "redirect:/admin/product/";
    }


    @GetMapping("/edit/{id}")
    public String productEditStart(@PathVariable("id") long id, Model model) {
        ProductDTO productDTO = productMapper.toDto(productsService.getById(id));
        model.addAttribute("types", Type.values());
        model.addAttribute("id", id);
        model.addAttribute("product", productDTO);
        return "admin/products-edit";
    }

    @PostMapping("/edit/{id}")
    public String productEditEnd(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult bindingResult,
                                 @RequestParam("image")MultipartFile image, @PathVariable("id") long id, Model model) throws IOException {
        if(bindingResult.hasErrors()){
            model.addAttribute("types", Type.values());
            return "admin/products-edit";
        }
        Product product = productMapper.toEntity(productDTO);
        product.setId(id);
        if(image.isEmpty()) product.setImageName(productsService.getById(id).getImageName());
        else product.setImageName(ImageUtil.imageForProducts(product, image));
        productsService.save(product);
        return "redirect:/admin/product/";
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") long id) {
        productsService.deleteById(id);
        return "redirect:/admin/product/";
    }
}

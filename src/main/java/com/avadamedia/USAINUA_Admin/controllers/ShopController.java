package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.Shop;
import com.avadamedia.USAINUA_Admin.mappers.ShopMapper;
import com.avadamedia.USAINUA_Admin.models.ShopDTO;
import com.avadamedia.USAINUA_Admin.services.impl.ShopsServiceImpl;
import com.avadamedia.USAINUA_Admin.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/admin/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopsServiceImpl shopsService;
    private final ShopMapper shopMapper;
    @GetMapping("/")
    public String shop(Model model) {
        model.addAttribute("shops", shopsService.getAll());
        return "admin/shops";
    }
    @GetMapping("/add/")
    public String shopAddStart(@ModelAttribute("shop") ShopDTO shop) {
        return "admin/shops-add";
    }
    @PostMapping("/add/")
    public String shopAddEnd(@ModelAttribute("shop") @Valid ShopDTO shop, BindingResult bindingResult, @RequestParam("image")MultipartFile image, Model model) throws IOException {
        String format = "";
        if(!image.isEmpty())format = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
        if(bindingResult.hasErrors() || image.isEmpty() ||  (image.isEmpty()  ||  (!format.equals(".jpg")  &&  (!format.equals(".jpeg")  && (!format.equals(".png")))))){
            if (image.isEmpty()  ||  (!format.equals(".jpg")  &&  (!format.equals(".jpeg")  && (!format.equals(".png"))))) {
                model.addAttribute("error", "Фото повино бути завантажено і мати формат jpg, jpeg і png");
            }
            return "admin/shops-add";
        }
        Shop shop1 = shopMapper.toEntity(shop);
        shop1.setImageName(ImageUtil.imageForShop(shop1, image));
        shopsService.save(shop1);
        return "redirect:/admin/shop/";
    }

    @GetMapping("/edit/{id}")
    public String shopEditByIdStart(@PathVariable("id") long id, Model model) {
        ShopDTO shopDTO = shopMapper.toDto(shopsService.getById(id));
        model.addAttribute("shop", shopDTO);
        model.addAttribute("id", id);
        return "admin/shops-edit";
    }
    @PostMapping("/edit/{id}")
    public String shopEditByIdEnd(@ModelAttribute("shop") @Valid ShopDTO shop, BindingResult bindingResult,
                                  @RequestParam("image")MultipartFile image, @PathVariable("id") long id, Model model) throws IOException {
        String format = "";
        if(!image.isEmpty())format = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
        if(bindingResult.hasErrors()  ||  (!format.equals(".jpg")  &&  !format.equals(".jpeg")  && (!format.equals(".png")  &&  (!format.isBlank())))) {
            if(!format.equals(".jpg")  &&  !format.equals(".jpeg")  && (!format.equals(".png")  &&  (!format.isBlank())))model.addAttribute("error", "Фото повино бути завантажено і мати формат jpg, jpeg і png");
            return "admin/shops-edit";
        }
        Shop shop1 = shopMapper.toEntity(shop);
        shop1.setId(id);
        if(!image.isEmpty()){
            shop1.setImageName(ImageUtil.imageForShop(shop1, image));
        }else shop1.setImageName(shopsService.getById(id).getImageName());
        shopsService.save(shop1);
        return "redirect:/admin/shop/";
    }
    @PostMapping("/delete/{id}")
    public String shopDeleteById(@PathVariable("id") long id) {
        shopsService.deleteById(id);
        return "redirect:/admin/shop/";
    }
}

package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.AdditionalService;
import com.avadamedia.USAINUA_Admin.mappers.AdditionalServiceMapper;
import com.avadamedia.USAINUA_Admin.models.AdditionalServiceDTO;
import com.avadamedia.USAINUA_Admin.repositories.AdditionalServicesRepository;
import com.avadamedia.USAINUA_Admin.services.impl.AdditionalServicesServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/additional-service")
@RequiredArgsConstructor
public class AdditionalServiceController {
    private final AdditionalServicesServiceImpl additionalServicesService;
    private final AdditionalServiceMapper additionalServiceMapper;
    @GetMapping("/")
    public String additionalServices(Model model) {
        model.addAttribute("services", additionalServicesService.getAll());
        return "admin/additional-services";
    }

    @GetMapping("/add")
    public String addAdditionalServicesStart(@ModelAttribute("service") AdditionalServiceDTO additionalServiceDTO) {
        return "admin/additional-services-add";
    }

    @PostMapping("/add")
    public String addAdditionalServicesEnd(@ModelAttribute("service") @Valid AdditionalServiceDTO additionalServiceDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/additional-services-add";
        }
        additionalServicesService.save(additionalServiceMapper.toEntity(additionalServiceDTO));
        return "redirect:/admin/additional-service/";
    }


    @GetMapping("/edit/{id}")
    public String additionalServicesEditStart(Model model, @PathVariable("id") Long id) {
        AdditionalServiceDTO additionalServiceDTO = additionalServiceMapper.toDto(additionalServicesService.getById(id));
        model.addAttribute("id", id);
        model.addAttribute("service", additionalServiceDTO);
        return "admin/additional-services-edit";
    }

    @PostMapping("/edit/{id}")
    public String additionalServicesEditEnd(@ModelAttribute("service") @Valid AdditionalServiceDTO additionalServiceDTO,
                                            BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "admin/additional-services-edit";
        }
        AdditionalService additionalService = additionalServiceMapper.toEntity(additionalServiceDTO);
        additionalService.setId(id);
        additionalServicesService.save(additionalService);
        return "redirect:/admin/additional-service/";
    }

    @PostMapping("/delete/{id}")
    public String additionalServicesDeleteEnd(@PathVariable("id") Long id) {
        additionalServicesService.deleteById(id);
        return "redirect:/admin/additional-service/";
    }
}

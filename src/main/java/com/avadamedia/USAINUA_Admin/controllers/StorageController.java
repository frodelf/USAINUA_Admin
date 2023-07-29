package com.avadamedia.USAINUA_Admin.controllers;

import com.avadamedia.USAINUA_Admin.entity.Storage;
import com.avadamedia.USAINUA_Admin.mappers.StorageMapper;
import com.avadamedia.USAINUA_Admin.models.StorageDTO;
import com.avadamedia.USAINUA_Admin.repositories.StorageRepository;
import com.avadamedia.USAINUA_Admin.services.impl.StorageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageServiceImpl storageService;
    private final StorageRepository storageRepository;
    private final StorageMapper storageMapper;
    @GetMapping("/")
    public String storage(Model model) {

        model.addAttribute("storages", storageService.getAll());
        return "admin/storages";
    }
    @GetMapping("/add")
    public String storageAddStart(@ModelAttribute("storage") StorageDTO storage) {
        return "admin/storage-add";
    }

    @PostMapping("/add")
    public String storageAddEnd(@ModelAttribute("storage") @Valid StorageDTO storage, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "admin/storage-add";
        storageService.save(storageMapper.toEntity(storage));
        return "redirect:/admin/storage/";
    }
    @GetMapping("/edit/{id}")
    public String storageEditStart(@PathVariable("id") Long id, Model model) {
        StorageDTO storageDTO = storageMapper.toDto(storageService.getById(id));
        model.addAttribute("storage", storageDTO);
        model.addAttribute("id", id);
        return "admin/storage-edit";
    }

    @PostMapping("/edit/{id}")
    public String storageAddEnd(@ModelAttribute("storage")@Valid StorageDTO storageDTO, BindingResult bindingResult, @PathVariable("id")Long id, Model model) {
        if(bindingResult.hasErrors())return "admin/storage-edit";
        Storage storage = storageMapper.toEntity(storageDTO);
        storage.setId(id);
        storageService.save(storage);
        return "redirect:/admin/storage/";
    }

    @PostMapping("/delete/{id}")
    public String storage(@PathVariable("id") Long id) {
        storageService.deleteById(id);
        return "redirect:/admin/storage/";
    }
}

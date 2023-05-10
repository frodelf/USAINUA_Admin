package com.avadamedia.USAINUA_Admin.services;

import com.avadamedia.USAINUA_Admin.entity.AdditionalService;

import java.util.List;

public interface AdditionalServicesService {
    void save(AdditionalService additionalService);
    AdditionalService getByName(String name);
    AdditionalService getById(long id);
    List<AdditionalService> getAll();
    void deleteById(long id);
}

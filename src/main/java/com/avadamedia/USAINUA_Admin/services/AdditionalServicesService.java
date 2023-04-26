package com.avadamedia.USAINUA_Admin.services;

import com.avadamedia.USAINUA_Admin.entity.AdditionalServices;

import java.util.List;

public interface AdditionalServicesService {
    void save(AdditionalServices additionalServices);
    AdditionalServices getByName(String name);
    AdditionalServices getById(long id);
    List<AdditionalServices> getAll();
    void deleteById(long id);
}

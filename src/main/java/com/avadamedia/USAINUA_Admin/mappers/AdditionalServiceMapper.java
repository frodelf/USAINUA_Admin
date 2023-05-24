package com.avadamedia.USAINUA_Admin.mappers;

import com.avadamedia.USAINUA_Admin.entity.AdditionalService;
import com.avadamedia.USAINUA_Admin.models.AdditionalServiceDTO;

import java.util.ArrayList;
import java.util.List;

public class AdditionalServiceMapper {
    public AdditionalServiceDTO toDto(AdditionalService additionalService){
        AdditionalServiceDTO additionalServiceDTO = new AdditionalServiceDTO();
        additionalServiceDTO.setName(additionalService.getName());
        additionalServiceDTO.setPrice(String.valueOf(additionalService.getPrice()));
        return additionalServiceDTO;
    }
    public AdditionalService toEntity(AdditionalServiceDTO additionalServiceDTO){
        AdditionalService additionalService = new AdditionalService();
        additionalService.setName(additionalServiceDTO.getName());
        additionalService.setPrice(Double.valueOf(additionalServiceDTO.getPrice()));
        return additionalService;
    }
    public List<AdditionalServiceDTO> toDtoList(List<AdditionalService> additionalServices){
        ArrayList<AdditionalServiceDTO> additionalServiceDTO = new ArrayList<>();
        for (AdditionalService additionalService : additionalServices) {
            additionalServiceDTO.add(toDto(additionalService));
        }
        return additionalServiceDTO;
    }
    public List<AdditionalService> toEntityList(List<AdditionalServiceDTO> additionalServicesDTO){
        ArrayList<AdditionalService> additionalService = new ArrayList<>();
        for (AdditionalServiceDTO additionalServiceDTO : additionalServicesDTO) {
            additionalService.add(toEntity(additionalServiceDTO));
        }
        return additionalService;
    }
}

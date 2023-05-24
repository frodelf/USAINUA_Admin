package com.avadamedia.USAINUA_Admin.mappers;

import com.avadamedia.USAINUA_Admin.entity.Storage;
import com.avadamedia.USAINUA_Admin.models.StorageDTO;

import java.util.ArrayList;
import java.util.List;

public class StorageMapper {
    public StorageDTO toDto(Storage storage){
        StorageDTO storageDTO = new StorageDTO();
        storageDTO.setFullName(storage.getFullName());
        storageDTO.setCity(storage.getCity());
        storageDTO.setStreet(storage.getStreet());
        storageDTO.setState(storage.getState());
        storageDTO.setIndex(storage.getIndex());
        storageDTO.setPhone(storage.getPhone());
        return storageDTO;
    }
    public Storage toEntity(StorageDTO storageDTO){
        Storage storage = new Storage();
        storage.setFullName(storageDTO.getFullName());
        storage.setCity(storageDTO.getCity());
        storage.setStreet(storageDTO.getStreet());
        storage.setState(storageDTO.getState());
        storage.setIndex(storageDTO.getIndex());
        storage.setPhone(storageDTO.getPhone());
        return storage;
    }
    public List<StorageDTO> toDtoList(List<Storage> storages){
        List<StorageDTO> storageDTOS = new ArrayList<>();
        for (Storage storage : storages) {
            storageDTOS.add(toDto(storage));
        }
        return storageDTOS;
    }
    public List<Storage> toEntityList(List<StorageDTO> storageDTOS){
        List<Storage> storages = new ArrayList<>();
        for (StorageDTO storageDTO : storageDTOS) {
            storages.add(toEntity(storageDTO));
        }
        return storages;
    }
}
package com.avadamedia.USAINUA_Admin.repositories;

import com.avadamedia.USAINUA_Admin.entity.AdditionalServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdditionalServicesRepository extends JpaRepository<AdditionalServices, Long> {
    Optional<AdditionalServices> findById(Long id);
    Optional<AdditionalServices> findByName(String name);
    List<AdditionalServices> findAll();
    void deleteById(Long id);
}

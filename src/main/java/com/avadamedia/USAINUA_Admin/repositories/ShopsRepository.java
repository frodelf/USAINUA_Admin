package com.avadamedia.USAINUA_Admin.repositories;

import com.avadamedia.USAINUA_Admin.entity.Shops;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopsRepository extends JpaRepository<Shops, Long> {
    List<Shops> findAll();
    Optional<Shops> findByLink(String link);
    Optional<Shops> findById(Long id);
    void deleteById(Long id);
}

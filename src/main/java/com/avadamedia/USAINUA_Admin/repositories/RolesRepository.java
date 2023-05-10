package com.avadamedia.USAINUA_Admin.repositories;

import com.avadamedia.USAINUA_Admin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, Long> {
    @Override
    Optional<Role> findById(Long id);
}

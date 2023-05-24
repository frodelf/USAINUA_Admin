package com.avadamedia.USAINUA_Admin.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "additional_services")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique = true)
    private String name;
    private Double price;
}

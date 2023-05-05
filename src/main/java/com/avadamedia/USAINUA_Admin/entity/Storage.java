package com.avadamedia.USAINUA_Admin.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "storage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    @NotBlank(message = "Ім'я складу не може бути порожнім")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я іїІЇ'`-]+$", message = "Некоректне ім'я складу")
    private String fullName;
    @NotBlank(message = "Назва вулиці не може бути порожньою")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я іїІЇ'`-]+$", message = "Некоректна назва вулиці")
    private String street;
    @NotBlank(message = "Назва міста не може бути порожньою")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я іїІЇ'`-]+$", message = "Некоректна назва міста")
    private String city;
    @NotBlank(message = "Назва штату не може бути порожньою")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я щЩШшіїІЇ'`-]+$", message = "Некоректна назва штату")
    private String state;

    @Column(name = "composition_index")
    @NotBlank(message = "Індекс складу не може бути порожнім")
    @Pattern(regexp = "^[0-9]+$", message = "Некоректний індекс складу")
    private String index;
    @NotBlank(message = "Телефон складу не може бути порожнім")
    @Pattern(regexp = "^[0-9]+$", message = "Некоректний телефон складу")
    private String phone;
}
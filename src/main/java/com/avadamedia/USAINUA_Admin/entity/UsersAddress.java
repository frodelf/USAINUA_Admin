package com.avadamedia.USAINUA_Admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Назву адреси не було вказано")
    private String addressName;
    @NotBlank(message = "Ім'я не було вказано")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я і]+$", message = "Некоректне ім'я")
    private String usersName;
    @NotBlank(message = "Прізвище не було вказано")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я і]+$", message = "Некоректне прізвище користувача")
    private String usersSurname;
    @NotBlank(message = "Телефоний номер не було вказано")
    @Pattern(regexp = "^[0-9]+$", message = "Некоректний телефоний номер")
    private String phone;
    @NotBlank(message = "Назву реніону не було вказано")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я і]+$", message = "Некоректний регіон")
    private String region;
    @NotBlank(message = "Назву міста не було вказано")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я і]+$", message = "Некоректна назва міста")
    private String city;
    @NotBlank(message = "Назву відділення не було вказано")
    private String department;
}

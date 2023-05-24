package com.avadamedia.USAINUA_Admin.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class StorageDTO {
    @NotBlank(message = "Ім'я складу не може бути порожнім")
    @Length(max = 40, message = "Довжина поля повинна бути до 40 символів")
    private String fullName;
    @NotBlank(message = "Назва вулиці не може бути порожньою")
    @Length(max = 100, message = "Довжина поля повинна бути до 100 символів")
    private String street;
    @NotBlank(message = "Назва міста не може бути порожньою")
    @Length(max = 40, message = "Довжина поля повинна бути до 40 символів")
    private String city;
    @NotBlank(message = "Назва штату не може бути порожньою")
    @Length(max = 40, message = "Довжина поля повинна бути до 40 символів")
    private String state;
    @NotBlank(message = "Індекс складу не може бути порожнім")
    @Pattern(regexp = "^[0-9]+$", message = "Некоректний індекс складу")
    @Length(max = 10, message = "Довжина поля повинна бути до 10 символів")
    private String index;
    @NotBlank(message = "Телефон складу не може бути порожнім")
    @Pattern(regexp = "^[0-9]+$", message = "Некоректний телефон складу")
    @Length(max = 20, message = "Довжина поля повинна бути до 20 символів")
    private String phone;
}

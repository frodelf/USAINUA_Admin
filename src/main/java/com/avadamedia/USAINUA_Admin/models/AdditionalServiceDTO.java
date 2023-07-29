package com.avadamedia.USAINUA_Admin.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
public class AdditionalServiceDTO {
    @NotBlank(message = "Назва повина бути вказана")
    @Length(max = 40, message = "Довжина поля повинна бути до 40 символів")
    private String name;
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Поле повинно містити десяткове число")
    @NotBlank(message = "Ціна повина бути вказана ")
    @Length(max = 10, message = "Довжина поля повинна бути до 10 символів")
    private String price;
}

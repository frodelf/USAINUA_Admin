package com.avadamedia.USAINUA_Admin.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
@Data
public class NewsDTO {
    @NotBlank(message = "Назва новини не була вказана")
    @Length(max = 40, message = "Довжина поля повинна бути до 40 символів")
    private String title;
    @NotBlank(message = "Текст новини не був вказаний")
    @Length(max = 1000, message = "Довжина поля повинна бути до 1000 символів")
    private String text;
}

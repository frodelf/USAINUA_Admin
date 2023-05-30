package com.avadamedia.USAINUA_Admin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Назва повина бути вказана")
    @Length(max = 40, message = "Довжина поля повинна бути до 40 символів")
    private String name;
    @NotNull(message = "Ціна повина бути вказана ")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Поле повинно містити десяткове число")
    @Length(max = 10, message = "Довжина поля повинна бути до 10 символів")
    private String price;
    @NotBlank(message = "Тип повинен бути вказаний")
    private String type;
    @NotBlank(message = "Посилання на продукт повине бути вказане")
    @URL(message = "Посилання має бути URL-адресою")
    private String link;
    private String imageName;
}

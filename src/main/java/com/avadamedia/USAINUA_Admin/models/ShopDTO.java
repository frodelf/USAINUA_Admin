package com.avadamedia.USAINUA_Admin.models;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
public class ShopDTO {
    @URL(message = "Посилання на магазин має бути URL-адресою")
    @NotBlank(message = "Посилання повино бути вказане")
    private String link;
    private String imageName;
}

package com.avadamedia.USAINUA_Admin.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Поле 'name' не може бути порожнім")
    private String name;

    @NotNull(message = "Поле 'price' не може бути порожнім")
    @Min(value = 0, message = "Ціна не може бути від'ємною")
    private double price;

    @NotBlank(message = "Поле тип продукту не може бути порожнім")
    @Pattern(regexp = "^(Clothes|Sport|Gadgets|Another)$", message = "Тип продукту може мати тільки значення: 'Clothes', 'Sport', 'Gadgets', 'Another'")
    private String type;


    @NotBlank(message = "Посилання на продукт не може бути порожнім")
    @Column(unique = true, length = 1000)
    @URL(message = "Посилання має бути URL-адресою")
    private String link;

    @NotBlank(message = "Фотографія для продукту не може бути порожньою")
    @Column(name = "image_name", unique = true)
    private String imageName;
}

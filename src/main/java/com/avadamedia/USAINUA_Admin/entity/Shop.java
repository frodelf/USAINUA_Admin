package com.avadamedia.USAINUA_Admin.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "shops")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique = true)
    @URL(message = "Посилання на магазин має бути URL-адресою")
    private String link;
    @Column(name = "image_name",unique = true)
    @NotBlank(message = "Фотографія магазину не може бути порожньою")
    private String imageName;
}
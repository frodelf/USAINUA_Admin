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
    @NotBlank(message = "Назва додаткового сервісу не була вказана")
    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я і]+$", message = "Некоректна додаткового сервісу")
    private String name;
    @DecimalMin(value = "0.0", message = "Ціна додаткового сервісу повинна бути більше або дорівнювати нулю")
    @NotNull(message = "Ціна додаткового сервісу не була вказана, ціна повина містити тільки цифри")
    private Double price;
}

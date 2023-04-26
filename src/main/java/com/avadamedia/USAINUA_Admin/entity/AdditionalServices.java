package com.avadamedia.USAINUA_Admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "additional_services")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Назва додаткового сервісу не була вказана")
    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я і]+$", message = "Некоректна додаткового сервісу")
    private String name;
    @DecimalMin(value = "0.0", message = "Ціна додаткового сервісу повинна бути більше або дорівнювати нулю")
    @NotNull(message = "Ціна додаткового сервісу не була вказана")
    private double price;
}

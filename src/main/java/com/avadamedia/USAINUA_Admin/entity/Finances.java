package com.avadamedia.USAINUA_Admin.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Table(name = "finances")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Finances {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Дата оплачення посилки(Finances) не була вказана")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @DecimalMin(value = "0.0", message = "Загальна ціна посилки (Finances) повинна бути більша нуля")
    @NotNull(message = "Ціна посилки (Finances) не була вказана")
    private double sum;

    public Finances(Date date, double sum) {
        this.date = date;
        this.sum = sum;
    }
}

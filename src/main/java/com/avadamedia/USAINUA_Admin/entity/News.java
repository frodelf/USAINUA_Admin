package com.avadamedia.USAINUA_Admin.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Table(name = "news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Назва новини не була вказана")
    private String title;
    @Column(name = "text", columnDefinition = "text")
    @NotBlank(message = "Текст новини не був вказаний")
    private String text;
    @Column(name = "registration_date")
    @NotNull(message = "Дата випуску новини не була вказана")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}

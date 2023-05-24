package com.avadamedia.USAINUA_Admin.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Статус не може бути порожнім")
    @Pattern(regexp = "^(розрахунок цінності|готово до оплати|оплачено)$", message = "Статус не коректний")
    private String status;

    @NotBlank(message = "Транспортне засіб не може бути порожнім")
    @Pattern(regexp = "^(plane|ship|another)$", message = "Транспорт не коректний")
    private String transport;

    private String description;

    @URL(message = "Посилання має бути URL-адресою")
    private String link;

    private String trackNumber;

    @NotNull(message = "Вага не може бути порожньою")
    @Positive(message = "Вага повинна бути більшою за нуль")
    private Double weight;

    @Min(value = 0, message = "Ціна повинна бути більшою або дорівнювати нулю")
    private Double price;

    @Min(value = 0, message = "Загальна ціна повинна бути більшою або дорівнювати нулю")
    private Double totalPrice;

    private int number;
    @Column(name = "only_delivery")
    private boolean isOnlyDelivery;
    @Column(name = "data_registration")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataRegistration;
    @Column(name = "date_receiving")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateReceiving;


    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<AdditionalService> additionalServices;
    @ManyToOne
    UsersAddress usersAddresses;
}

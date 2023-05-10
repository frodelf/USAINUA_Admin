package com.avadamedia.USAINUA_Admin.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Назва користувача не була вказана")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я і]+$", message = "Некоректне ім'я користувача")
    private String name;
    @NotBlank(message = "Прізвище користувача не було вказано")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я і]+$", message = "Некоректне прізвище користувача")
    private String surname;
    @NotBlank(message = "Пароль користувача не був вказаний")
    private String password;
    @NotNull(message = "Сума користувача не була вказана")
    private double money;
    @Column(name = "is_man")
    @NotNull(message = "Стать користувача не була вказана")
    private Boolean isMan;
    @NotNull(message = "Дата дня народження не була вказана")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @Column(unique = true)
    @Email(message = "Електрона адреса не коректна")
    @NotBlank(message = "Електроний адрес не був вказаний")
    private String email;
    @NotBlank(message = "Телефоний номер не був вказаний")
    @Pattern(regexp = "^[0-9]+$", message = "Некоректний номер телефону")
    private String phone;
    @OneToMany
    List<Order> orders;

    @OneToMany
    List<CreditCard> creditCards;

    @OneToMany
    List<Finances> finances;

    @OneToMany
    List<UsersAddress> usersAddresses;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Role> roles;
}
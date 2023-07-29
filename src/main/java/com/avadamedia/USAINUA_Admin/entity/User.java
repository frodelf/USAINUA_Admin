package com.avadamedia.USAINUA_Admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String name;
    private String surname;
    private String password;
    private double money;
    @Column(name = "is_man")
    private Boolean isMan;
    private Date birthday;
    @Column(unique = true)
    private String email;
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
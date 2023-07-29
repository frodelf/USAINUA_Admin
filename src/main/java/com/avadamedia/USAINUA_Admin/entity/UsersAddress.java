package com.avadamedia.USAINUA_Admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String addressName;
    private String usersName;
    private String usersSurname;
    private String phone;
    private String region;
    private String city;
    private String department;
}

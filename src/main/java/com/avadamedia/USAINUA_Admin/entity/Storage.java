package com.avadamedia.USAINUA_Admin.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "storage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;
    private String street;
    private String city;
    private String state;

    @Column(name = "composition_index")
    private String index;
    private String phone;
}
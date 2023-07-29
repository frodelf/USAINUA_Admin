package com.avadamedia.USAINUA_Admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cards_number", length = 16)
    private String cardsNumber;

    @Column(name = "validity_period", length = 5)
    private String validityPeriod;

    @Column(name = "CVV", length = 3)
    private String cvv;

    public CreditCard(String cardsNumber, String validityPeriod, String cvv) {
        this.cardsNumber = cardsNumber;
        this.validityPeriod = validityPeriod;
        this.cvv = cvv;
    }
}


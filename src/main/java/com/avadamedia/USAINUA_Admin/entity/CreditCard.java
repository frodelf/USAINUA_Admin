package com.avadamedia.USAINUA_Admin.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @NotBlank(message = "Номер кредитної карти не був вказаний")
    @Pattern(regexp = "^[0-9]+$", message = "Некоректний номер карти, повинен бути формат \"0000111122223333\"")
    private String cardsNumber;

    @Column(name = "validity_period", length = 5)
    @NotBlank(message = "Період дії кредитної карти не був вказаний")
    @Pattern(regexp = "^[0-9 /]+$", message = "Некоректний період дії карти, повинен бути формат \"00/11\"")
    private String validityPeriod;

    @Column(name = "CVV", length = 3)
    @NotBlank(message = "CVV код не був вказаний")
    @Pattern(regexp = "^[0-9]+$", message = "Некоректний CVV код карти, повинен бути формат \"123\"")
    private String cvv;

    public CreditCard(String cardsNumber, String validityPeriod, String cvv) {
        this.cardsNumber = cardsNumber;
        this.validityPeriod = validityPeriod;
        this.cvv = cvv;
    }
}


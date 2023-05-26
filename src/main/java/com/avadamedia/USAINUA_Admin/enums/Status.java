package com.avadamedia.USAINUA_Admin.enums;

import java.util.Arrays;
import java.util.List;

public enum Status {
    CALCULATING_VALUE("розрахунок цінності"),
    READY_FOR_PAYMENT("готово до оплати"),
    PAID("оплачено"),
    DELIVERED("доставлено"),
    CANCEL("відмінено");
    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static List<String> getAll(){
        return Arrays.asList(CALCULATING_VALUE.status, READY_FOR_PAYMENT.status, PAID.status, DELIVERED.status);
    }
}

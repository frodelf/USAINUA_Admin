package com.avadamedia.USAINUA_Admin.enums;

import java.util.Arrays;
import java.util.List;

public class Status {
    public static final String CALCULATING_VALUE = "розрахунок цінності";
    public static final String READY_FOR_PAYMENT = "готово до оплати";
    public static final String PAID = "оплачено";
    public static final String DELIVERED = "доставлено";
    public static List<String> getAll(){
        return Arrays.asList(CALCULATING_VALUE, READY_FOR_PAYMENT, PAID, DELIVERED);
    }
}

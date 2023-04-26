package com.avadamedia.USAINUA_Admin.enums;

import java.util.Arrays;
import java.util.List;

public class Transport {
    public static final String PLANE = "plane";
    public static final String SHIP = "ship";
    public static final String ANOTHER = "another";
    public static List<String> getAll(){
        return Arrays.asList(PLANE, SHIP, ANOTHER);
    }

}

package com.avadamedia.USAINUA_Admin.enums;

import java.util.Arrays;
import java.util.List;

public enum Transport {
    PLANE("plane"),
    SHIP("ship"),
    ANOTHER("another");
    private String transport;
    Transport(String transport) {
        this.transport = transport;
    }
    public String getTransport() {
        return transport;
    }
    public static List<String> getAll(){
        return Arrays.asList(PLANE.transport, SHIP.transport, ANOTHER.transport);
    }

}

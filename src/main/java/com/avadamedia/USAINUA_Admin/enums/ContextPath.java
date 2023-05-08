package com.avadamedia.USAINUA_Admin.enums;

public enum ContextPath {
    PATH("/USAINUA_Admin");
    private String url;
    public String getUrl() {
        return url;
    }
    ContextPath(String url) {
        this.url = url;
    }
}

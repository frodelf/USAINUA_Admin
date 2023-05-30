package com.avadamedia.USAINUA_Admin.util;

import com.avadamedia.USAINUA_Admin.entity.AdditionalService;

import java.util.List;

public class CalculatorUtil {
    public static double deliveryApproximatePrice(double weight, List<AdditionalService> additionalServices, String transport){
        double approximatePrice = 0;
        for (AdditionalService additionalService : additionalServices) {
            approximatePrice+=additionalService.getPrice();}
        if(transport.equals("plane"))approximatePrice += 0.5*weight+1000;
        else if(transport.equals("ship"))approximatePrice += 0.3*weight+500;
        else approximatePrice += 800;
        return approximatePrice;
    }
    public static double purchaseAndDeliveryApproximatePrice(double weight, List<AdditionalService> additionalServices, String transport, double price){
        double approximatePrice = 0;

        for (AdditionalService additionalService : additionalServices) {
            approximatePrice+=additionalService.getPrice();
        }
        if(transport.equals("plane"))approximatePrice += 1*price+0.5*weight+1000;
        else if(transport.equals("ship"))approximatePrice += 5*price+0.3*weight+500;
        else approximatePrice += 800;
        return approximatePrice;
    }
}

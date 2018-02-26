package com.epam.geocoder.core.Data;

public enum GeoData {
    SPB_EPAM_CHR("Санкт-Петербург, набережная Чёрной речки, 41В"),
    LENINA_SQARE("Площадь Ленина");
    private String request;

    GeoData(String requesr) {
        this.request = requesr;
    }

    @Override
    public String toString() {
        return request;
    }
}

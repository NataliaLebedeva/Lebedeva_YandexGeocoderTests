package com.epam.geocoder.core.data;

public enum YaGeoTestData {
    SPB_EPAM_CHR("Санкт-Петербург, набережная Чёрной речки, 41В"),
    LENINA_SQARE("Площадь Ленина");
    private String request;

    YaGeoTestData(String requesr) {
        this.request = requesr;
    }

    @Override
    public String toString() {
        return request;
    }
}

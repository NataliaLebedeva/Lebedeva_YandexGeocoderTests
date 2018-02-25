package com.epam.geocoder.core;

import lombok.Getter;

public class YaGeocoderConstants {
    public static final String YA_GEOCODER_API_URI = "https://geocode-maps.yandex.ru/1.x/";
    public static final String FORMAT = "format";
    public static final String GEOCODE = "geocode";
    public static final String SCO = "sco";
    public static final String KIND = "kind";
    public static final String LANG = "lang";
    public static final String SKIP = "skip";
    public static final String RSPN = "rspn";
    public static final String BBOX = "bbox";
    public static final String CALLBACK = "callback";
    public static final String APIKEY = "apikey";

    public enum LanguageRegion {
        ru_RU,
        uk_UA,
        be_BY,
        en_RU,
        en_US,
        tr_TR
    }

    public enum ResponseKind {
        HOUSE("house"),
        STREET("street"),
        METRO("metro"),
        DISTRICT("district"),
        LOCALITY("locality");
        private String kind;

        ResponseKind(String kind) {
            this.kind = kind;
        }

        @Override
        public String toString() {
            return kind;
        }
    }

    @Getter
    public enum ResponseFormat {
        JSON("json"),
        XML("xml");
        private String format;

        ResponseFormat(String format) {
            this.format = format;
        }
    }

    @Getter
    public enum ResponseSCO {
        LONGLAT("longlat"),
        LATLONG("latlong");
        private String longLat;

        ResponseSCO(String longLat) {
            this.longLat = longLat;
        }
    }


}

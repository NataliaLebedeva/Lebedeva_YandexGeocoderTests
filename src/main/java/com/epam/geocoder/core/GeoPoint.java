package com.epam.geocoder.core;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GeoPoint {

    public static GeoPoint EPAM = new GeoPoint(59.985727f, 30.307940f);

    Float lon;
    Float lat;

    @Override
    public String toString() {
        return String.format("%s %s", lon, lat);
    }
}

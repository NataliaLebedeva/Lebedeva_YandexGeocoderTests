package com.epam.geocoder.core;

import beans.YaGeocoderResponse;
import com.detectlanguage.DetectLanguage;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.geocoder.core.YaGeocoderConstants.LanguageRegion.en_RU;
import static com.epam.geocoder.core.YaGeocoderConstants.ResponseFormat.JSON;
import static com.epam.geocoder.core.YaGeocoderConstants.ResponseKind.STREET;
import static com.epam.geocoder.core.YaGeocoderConstants.ResponseSCO.LATLONG;
import static com.epam.geocoder.core.YandexGeocoderApi.getYandexGeocoderAnswer;
import static com.epam.geocoder.core.YandexGeocoderApi.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class YandexGeocoderTests {

    static {
        DetectLanguage.apiKey = "3a21506d3d2013fc2850b042c3a1732c";
    }

    @Test
    public void simpleSmokeStatusTest() {
        with().format(JSON)
                .geocode("город Москва, ул. Тверская, дом 6").callApi()
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void successResponseTest() {
        with().format(JSON)
                .geocode("Площадь Ленина").callApi()
                .then()
                .specification(YandexGeocoderApi.successResponse());
    }

    @Test
    public void answerSimpleTest() {
        Response response = with().format(JSON).geocode("Площадь Ленина").callApi();
        response.then().specification(YandexGeocoderApi.successResponse());

        YaGeocoderResponse yaResponse = getYandexGeocoderAnswer(response);

        assertThat(yaResponse.getResponse().getGeoObjectCollection().getFeatureMember(), not(empty()));
    }

    @Test
    public void skipLanguageTest() {
        Response response = with().format(JSON).geocode("Площадь Ленина").skip(2).lang(en_RU).callApi();
        response.then().specification(YandexGeocoderApi.successResponse());

        YaGeocoderResponse yaResponse = getYandexGeocoderAnswer(response);
        assertThat(yaResponse.getResponse().getGeoObjectCollection().getMetaDataProperty().getGeocoderResponseMetaData(),
                hasProperty("skip", equalTo(Integer.toString(2))));

        // TODO assert that all response data match to en_RU regex
        // TODO I'v tried to use com.detectlanguage, but the text is in translit, unfortunately
    }

    @Test
    public void getAddressesByCoordinatesTest() {
        Response response = with().format(JSON).sco(LATLONG).geocode(GeoPoint.EPAM).callApi();
        response.then().specification(YandexGeocoderApi.successResponse());
        YaGeocoderResponse yaResponse = getYandexGeocoderAnswer(response);
        List<String> addressesDetails = yaResponse.getResponse().getGeoObjectCollection().getFeatureMember().stream().map(fm -> fm.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getAddressDetails().getCountry().getAddressLine()).collect(Collectors.toList());

        assertThat(addressesDetails, hasItem("Санкт-Петербург, набережная Чёрной речки, 41В"));
    }

    @Test
    public void testAnswer4() {
        YaGeocoderResponse yaResponse = getYandexGeocoderAnswer(
                with().format(JSON).sco(LATLONG).kind(STREET).geocode(GeoPoint.EPAM).callApi()
        );

        assertThat(yaResponse.getResponse().getGeoObjectCollection().getMetaDataProperty().getGeocoderResponseMetaData(),
                hasProperty("kind", equalToIgnoringCase(STREET.toString())));
    }
}

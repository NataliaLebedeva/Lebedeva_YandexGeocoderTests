package com.epam.geocoder.core;

import beans.YaGeocoderResponse;
import com.epam.geocoder.core.data.YaGeoTestData;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang.NotImplementedException;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.geocoder.core.YaGeocoderConstants.*;

public class YandexGeocoderApi {

    private Map<String, Object> params = new HashMap<>();

    private YandexGeocoderApi() {
    }

    public static ApiBuilder with() {
        return new ApiBuilder(new YandexGeocoderApi());
    }

    public static YaGeocoderResponse getYandexGeocoderAnswer(Response response) {
        return new Gson().fromJson(response.asString().trim(), YaGeocoderResponse.class);
    }

    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE)
                .expectResponseTime(Matchers.lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static RequestSpecification baseRequestConfig() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setBaseUri(YA_GEOCODER_API_URI)
                .build();
    }

    public static class ApiBuilder {
        YandexGeocoderApi geoApi;

        private ApiBuilder(YandexGeocoderApi api) {
            geoApi = api;
        }

        public ApiBuilder geocode(YaGeoTestData geocode) {
            geoApi.params.put(GEOCODE, geocode);
            return this;
        }

        public ApiBuilder geocode(GeoPoint point) {
            geoApi.params.put(GEOCODE, point);
            return this;
        }

        public ApiBuilder format(ResponseFormat format) {
            geoApi.params.put(FORMAT, format.getFormat());
            return this;
        }

        public ApiBuilder sco(ResponseSCO longLat) {
            geoApi.params.put(SCO, longLat.getLongLat());
            return this;
        }

        public ApiBuilder kind(ResponseKind kind) {
            geoApi.params.put(KIND, kind);
            return this;
        }

        public ApiBuilder lang(LanguageRegion lang) {
            geoApi.params.put(LANG, lang);
            return this;
        }

        public ApiBuilder skip(Integer skip) {
            geoApi.params.put(SKIP, skip);
            return this;
        }

        // TODO
        public ApiBuilder rspn(Boolean rspn) {
            throw new NotImplementedException("Depends on BBOX parameter !");
//            geoApi.params.put(RSPN, rspn ? 1 : 0);
//            return this;
        }

        public ApiBuilder callback(String callbackFuncName) {
            throw new NotImplementedException();
        }

        public ApiBuilder apikey(String myApikey) {
            throw new NotImplementedException();
        }

        public Response callApi() {
            Map<String, String> collect = geoApi.params.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().toString()));
            return RestAssured.with()
                    .queryParams(collect)
                    .log().all()
                    .get(YA_GEOCODER_API_URI).prettyPeek();
        }
    }

}

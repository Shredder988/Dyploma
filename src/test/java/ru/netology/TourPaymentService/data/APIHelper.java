package ru.netology.TourPaymentService.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;;

public class APIHelper {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void cardPayment(DataHelper.CardInfo info) {
        given()
                .spec(requestSpec)
                .body(info)
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(200);
    }

    public static void cardPaymentWhenUsedFiledCard(DataHelper.CardInfo info) {
        given()
                .spec(requestSpec)
                .body(info)
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(500);
    }

    public static void creditPayment(DataHelper.CardInfo info) {
        given()
                .spec(requestSpec)
                .body(info)
                .when()
                .post("api/v1/credit")
                .then()
                .statusCode(200);

    }

    public static void creditPaymentWhenUsedFiledCard(DataHelper.CardInfo info) {
        given()
                .spec(requestSpec)
                .body(info)
                .when()
                .post("api/v1/credit")
                .then()
                .statusCode(500);
    }
}

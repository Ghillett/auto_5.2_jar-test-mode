package ru.netology.auto.util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CreateUser {

    public CreateUser(String login, String password, String status) {

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost:9999")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        given()
                .spec(requestSpec)
                .body(new RegistrationDto().convert(login, password, status))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}

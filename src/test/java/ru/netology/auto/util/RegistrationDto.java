package ru.netology.auto.util;

import com.google.gson.Gson;

import static io.restassured.RestAssured.given;

public class RegistrationDto {

    private String login;
    private String password;
    private String status;

    RegistrationDto() {
    }

    RegistrationDto(String login, String password, String status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public String convert(String login, String password, String status) {

        RegistrationDto userData = new RegistrationDto(login, password, status);
        Gson gson = new Gson();
        String json = gson.toJson(userData);

        return json;
    }
}
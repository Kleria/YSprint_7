package pageObjects;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.Courier;
import pojos.LoginRequest;

public class CourierPageObject {

    public CourierPageObject() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }
    @Step("make a courier")
    public Response createCourier(Courier courier) {
        return
                RestAssured.given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
    }
    @Step("login as a courier")
    public Response loginCourier(LoginRequest loginRequest) {
        return
                RestAssured.given()
                        .contentType("application/json")
                        .body(loginRequest)
                        .when()
                        .post("/api/v1/courier/login");
    }
    @Step("delete a courier")
    public Response deleteCourier(String courierId) {
        return
                RestAssured.given()
                        .when()
                        .delete("/api/v1/courier/" + courierId);
    }
}


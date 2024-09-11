package pageObjects;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.Courier;
import pojos.LoginRequest;

public class CourierPageObject {

    public CourierPageObject() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    public Response createCourier(Courier courier) {
        return
                RestAssured.given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
    }

    public Response loginCourier(LoginRequest loginRequest) {
        return
                RestAssured.given()
                        .contentType("application/json")
                        .body(loginRequest)
                        .when()
                        .post("/api/v1/courier/login");
    }

    public Response deleteCourier(String courierId) {
        return
                RestAssured.given()
                        .when()
                        .delete("/api/v1/courier/" + courierId);
    }
}


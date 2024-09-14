package pageObjects;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.Order;

import java.util.List;
import java.util.Collections;

public class OrderPageObject {

    static {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
    @Step("make an order")
    public static Response createOrder(String firstName, String lastName, String address, int metroStation,
                                       String phone, int rentTime, String deliveryDate, String comment,
                                       List<String> color) {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
    }
    @Step("make an order")
    public static Response createOrderWithNoColor(String firstName, String lastName, String address, int metroStation,
                                                  String phone, int rentTime, String deliveryDate, String comment) {
        return createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, Collections.emptyList());
    }
}

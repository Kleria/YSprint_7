import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class CheckOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @Step("check an order list")
    public void getOrdersListTest() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");

        response.then()
                .assertThat()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders", hasSize(not(0)));
    }
}

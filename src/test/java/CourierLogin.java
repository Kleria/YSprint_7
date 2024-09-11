import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageObjects.CourierPageObject;
import pojos.Courier;
import pojos.LoginRequest;

import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLogin {
    private CourierPageObject courierPageObject;
    private String courierId;

    @Before
    public void setUp() {
        courierPageObject = new CourierPageObject();
    }

    //курьер может авторизоваться;
    @Test
    @DisplayName("Check status code of /api/v1/courier/login")
    public void successfulLoginTest() {
        Courier courier = new Courier("eddy12", "Qwerty123", "Егор");
        Response response = courierPageObject.createCourier(courier);

        Response loginResponse = courierPageObject.loginCourier(new LoginRequest("eddy12", "Qwerty123"));
        loginResponse.then().assertThat().statusCode(200);
        courierId = loginResponse.jsonPath().getString("id");
    }

    //система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    @DisplayName("Check 404 of /api/v1/courier/login with wrong password")
    public void unsuccessfulLoginWithWrongPasswordTest() {
        Courier courier = new Courier("eddy12", "Qwerty123", "Егор");
        Response response = courierPageObject.createCourier(courier);

        Response loginResponse = courierPageObject.loginCourier(new LoginRequest("eddy12", "123"));
        loginResponse.then().assertThat().statusCode(404);

        Response loginResponseWithNeededPassword = courierPageObject.loginCourier(new LoginRequest("eddy12", "Qwerty123"));
        courierId = loginResponseWithNeededPassword.jsonPath().getString("id");
    }

    //система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    @DisplayName("Check 404 of /api/v1/courier/login with wrong login")
    public void unsuccessfulLoginWithWrongLoginTest() {
        Courier courier = new Courier("eddy12", "Qwerty123", "Егор");
        Response response = courierPageObject.createCourier(courier);

        Response loginResponse = courierPageObject.loginCourier(new LoginRequest("12", "Qwerty123"));
        loginResponse.then().assertThat().statusCode(404);

        Response loginResponseWithNeededPassword = courierPageObject.loginCourier(new LoginRequest("eddy12", "Qwerty123"));
        courierId = loginResponseWithNeededPassword.jsonPath().getString("id");
    }

    //для авторизации нужно передать все обязательные поля + если какого-то поля нет, запрос возвращает ошибку;
    @Test
    @DisplayName("Check 400 of /api/v1/courier/login with only login sent")
    public void unsuccessfulLoginWithOnlyLoginTest() {
        Courier courier = new Courier("eddy12", "Qwerty123", "Егор");
        Response response = courierPageObject.createCourier(courier);

        Response loginResponse = courierPageObject.loginCourier(new LoginRequest("eddy12"));
        loginResponse.then().assertThat().statusCode(400);

        Response loginResponseWithNeededPassword = courierPageObject.loginCourier(new LoginRequest("eddy12", "Qwerty123"));
        courierId = loginResponseWithNeededPassword.jsonPath().getString("id");
    }

    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("Check 404 of /api/v1/courier/login if courier does not exist")
    public void unsuccessfulLoginWithNonExistentCourierTest() {
        Response loginResponse = courierPageObject.loginCourier(new LoginRequest("courier", "Qwerty123"));
        loginResponse.then().assertThat().statusCode(404);
    }

    //успешный запрос возвращает id
    @Test
    @DisplayName("Check body of /api/v1/courier/login")
    public void successfulLoginBodyTest() {
        Courier courier = new Courier("eddy12", "Qwerty123", "Егор");
        Response response = courierPageObject.createCourier(courier);

        Response loginResponse = courierPageObject.loginCourier(new LoginRequest("eddy12", "Qwerty123"));
        loginResponse.then().assertThat().statusCode(200).body("id", notNullValue());
        courierId = loginResponse.jsonPath().getString("id");
    }

    @After
    public void tearDown() {
        // Удаление курьера после выполнения теста
        if (courierId != null) {
            Response deleteResponse = courierPageObject.deleteCourier(courierId);
            deleteResponse.then().assertThat().statusCode(200);
        }
    }
}

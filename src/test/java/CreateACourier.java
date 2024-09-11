import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageObjects.CourierPageObject;
import pojos.Courier;
import pojos.LoginRequest;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateACourier {

    private CourierPageObject courierPageObject;
    private String courierId;

    @Before
    public void setUp() {
        courierPageObject = new CourierPageObject();
    }

    //курьера можно создать + запрос возвращает правильный код ответа; (не понимаю, как иначе мы поймем, что курьер создан без доступа в БД)
    @Test
    @DisplayName("Check status code of /api/v1/courier")
    public void createACourierTest() {
        Courier courier = new Courier("eddy12", "Qwerty123", "Егор");
        Response response = courierPageObject.createCourier(courier);
        response.then().assertThat().statusCode(201);

        Response loginResponse = courierPageObject.loginCourier(new LoginRequest("eddy12", "Qwerty123"));
        loginResponse.then().assertThat().statusCode(200);
        courierId = loginResponse.jsonPath().getString("id");
    }

    //нельзя создать двух одинаковых курьеров + если создать пользователя с логином, который уже есть, возвращается ошибка. (тоже не поняла, в чем разница двух этих проверок)
    @Test
    @DisplayName("It's impossible to create 2 similar couriers")
    public void canNotCreateADoubleTest() {
        Courier courier = new Courier("eddy12", "Qwerty123", "Егор");
        Response response = courierPageObject.createCourier(courier);
        response.then().assertThat().statusCode(201);

        Response response1 = courierPageObject.createCourier(courier);
        response1.then().assertThat().statusCode(409);

        Response loginResponse = courierPageObject.loginCourier(new LoginRequest("eddy12", "Qwerty123"));
        loginResponse.then().assertThat().statusCode(200);
        courierId = loginResponse.jsonPath().getString("id");
    }

    //чтобы создать курьера, нужно передать в ручку все обязательные поля
    @Test
    @DisplayName("It is possible to create a courier with only login and password")
    public void createACourierWithOnlyRequiredDataTest() {
        Courier courier = new Courier("eddy12", "Qwerty123");
        Response response = courierPageObject.createCourier(courier);
        response.then().assertThat().statusCode(201);

        Response loginResponse = courierPageObject.loginCourier(new LoginRequest("eddy12", "Qwerty123"));
        loginResponse.then().assertThat().statusCode(200);
        courierId = loginResponse.jsonPath().getString("id");
    }

    //успешный запрос возвращает ok: true;
    @Test
    @DisplayName("Check body of /api/v1/courier")
    public void createACourierAndCheckBodyTest() {
        Courier courier = new Courier("eddy12", "Qwerty123");
        Response response = courierPageObject.createCourier(courier);
        response.then().assertThat().statusCode(201).body("ok", equalTo(true));

        Response loginResponse = courierPageObject.loginCourier(new LoginRequest("eddy12", "Qwerty123"));
        loginResponse.then().assertThat().statusCode(200);
        courierId = loginResponse.jsonPath().getString("id");
    }

    //если одного из полей нет, запрос возвращает ошибку;
    @Test
    @DisplayName("It is impossible to create a courier with only password")
    public void canNotCreateWithoutPasswordTest() {
        Courier courier = new Courier("eddy12");
        Response response = courierPageObject.createCourier(courier);
        response.then().assertThat().statusCode(400);
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

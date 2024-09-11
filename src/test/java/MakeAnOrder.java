import io.restassured.response.Response;
import org.junit.Test;
import pageObjects.OrderPageObject;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;

public class MakeAnOrder {

    @Test
    public void makeAnOrderUsingOneColor() {
        List<String> colors = Arrays.asList("BLACK");

        for (String color : colors) {
            Response response = OrderPageObject.createOrder(
                    "Naruto",
                    "Uchiha",
                    "Konoha, 142 apt.",
                    4,
                    "+7 800 355 35 35",
                    5,
                    "2020-06-06",
                    "Saske, come back to Konoha",
                    Arrays.asList(color)
            );

            response.then().assertThat().statusCode(201);
        }
    }

    @Test
    public void makeAnOrderUsingTwoColors() {
        List<String> colors = Arrays.asList("BLACK", "GREY");

        Response response = OrderPageObject.createOrder(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                colors
        );

        response.then().assertThat().statusCode(201);
    }

    @Test
    public void makeAnOrderNoColor() {
        Response response = OrderPageObject.createOrderWithNoColor(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha"
        );

        response.then().assertThat().statusCode(201);
    }

    @Test
    public void makeAnOrderAndCheckBody() {
        Response response = OrderPageObject.createOrderWithNoColor(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha"
        );

        response.then().assertThat().statusCode(201).and().body("track", notNullValue());
    }
}

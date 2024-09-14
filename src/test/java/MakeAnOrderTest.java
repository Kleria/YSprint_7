import faker.DataGenerator;
import io.restassured.response.Response;
import org.junit.Test;
import pageObjects.OrderPageObject;
import pojos.Order;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;

public class MakeAnOrderTest {

    @Test
    public void makeAnOrderUsingOneColorTest() {
        List<String> colors = DataGenerator.getRandomColors();

        for (String color : colors) {
            Order order = new Order(
                    DataGenerator.getRandomFirstName(),
                    DataGenerator.getRandomLastName(),
                    DataGenerator.getRandomAddress(),
                    DataGenerator.getRandomMetroStation(),
                    DataGenerator.getRandomPhoneNumber(),
                    DataGenerator.getRandomRentTime(),
                    DataGenerator.getRandomDeliveryDate(),
                    DataGenerator.getRandomComment(),
                    Arrays.asList(color)
            );

            System.out.println("Sending request with body: " + order.toString());

            Response response = OrderPageObject.createOrder(
                    order.getFirstName(),
                    order.getLastName(),
                    order.getAddress(),
                    order.getMetroStation(),
                    order.getPhone(),
                    order.getRentTime(),
                    order.getDeliveryDate(),
                    order.getComment(),
                    order.getColor()
            );

            System.out.println("Received response with status code: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody().asString());

            response.then().assertThat().statusCode(201);
        }
    }

    @Test
    public void makeAnOrderUsingTwoColorsTest() {
        List<String> colors = DataGenerator.getRandomColors();

        Response response = OrderPageObject.createOrder(
                DataGenerator.getRandomFirstName(),
                DataGenerator.getRandomLastName(),
                DataGenerator.getRandomAddress(),
                DataGenerator.getRandomMetroStation(),
                DataGenerator.getRandomPhoneNumber(),
                DataGenerator.getRandomRentTime(),
                DataGenerator.getRandomDeliveryDate(),
                DataGenerator.getRandomComment(),
                colors
        );

        response.then().assertThat().statusCode(201);
    }

    @Test
    public void makeAnOrderNoColorTest() {
        Response response = OrderPageObject.createOrderWithNoColor(
                DataGenerator.getRandomFirstName(),
                DataGenerator.getRandomLastName(),
                DataGenerator.getRandomAddress(),
                DataGenerator.getRandomMetroStation(),
                DataGenerator.getRandomPhoneNumber(),
                DataGenerator.getRandomRentTime(),
                DataGenerator.getRandomDeliveryDate(),
                DataGenerator.getRandomComment()
        );

        response.then().assertThat().statusCode(201);
    }

    @Test
    public void makeAnOrderAndCheckBodyTest() {
        Response response = OrderPageObject.createOrderWithNoColor(
                DataGenerator.getRandomFirstName(),
                DataGenerator.getRandomLastName(),
                DataGenerator.getRandomAddress(),
                DataGenerator.getRandomMetroStation(),
                DataGenerator.getRandomPhoneNumber(),
                DataGenerator.getRandomRentTime(),
                DataGenerator.getRandomDeliveryDate(),
                DataGenerator.getRandomComment()
        );

        response.then().assertThat().statusCode(201).and().body("track", notNullValue());
    }
}
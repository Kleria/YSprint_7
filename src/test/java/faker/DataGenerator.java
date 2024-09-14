package faker;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class DataGenerator {

    private static final Faker faker = new Faker();

    public static String getRandomFirstName() {
        return faker.name().firstName();
    }

    public static String getRandomLastName() {
        return faker.name().lastName();
    }

    public static String getRandomAddress() {
        return faker.address().streetAddress() + ", " + faker.address().buildingNumber() + " apt.";
    }

    public static int getRandomMetroStation() {
        return faker.number().numberBetween(1, 40); // Генерация станции метро
    }

    public static String getRandomPhoneNumber() {
        return faker.phoneNumber().phoneNumber(); // Генерация номера телефона в формате "+1234567890"
    }

    public static int getRandomRentTime() {
        return faker.number().numberBetween(1, 4); // Генерация количества дней аренды
    }


    public  static String getRandomDeliveryDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate randomDate = currentDate.plusDays(faker.number().numberBetween(1, 30));
        return randomDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

    public static String getRandomComment() {
        return faker.lorem().sentence(); // Генерация фейкового предложения
    }

    public static List<String> getRandomColors() {
        List<String> colors = Arrays.asList("BLACK", "GREY"); // Список доступных цветов
        Collections.shuffle(colors); // Перемешивание списка цветов
        return colors.subList(0, faker.number().numberBetween(1, 2)); // Возвращает случайное количество цветов (от 1 до 2)
    }
}

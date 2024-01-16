import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.OrderClient;
import org.example.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateOrderWithAuthTest {
    UserClient userClient = new UserClient();
    OrderClient orderClient = new OrderClient();
    private static String baseURI = "https://stellarburgers.nomoreparties.site";

    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = baseURI;
        userClient.createUser();

    }

    @DisplayName("Cоздание заказа пользователем с авторизацией")
    @Description("Позитивная проверка - создание заказа пользователем с авторизацией")
    @Test
    public void createOrderWithAuth() {
        accessToken = userClient.authUser().extract().jsonPath().getString("accessToken");
        orderClient.createOrderWithAuth(1, 2,  "Бессмертный метеоритный бургер",accessToken);
    }


    @After
    public void deleteUser() {
        userClient.deleteUser(accessToken);
    }


}

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.example.OrderClient;
import org.example.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

public class GetOrderWithAuthTest {
    UserClient userClient = new UserClient();
    OrderClient orderClient = new OrderClient();
    private static String baseURI = "https://stellarburgers.nomoreparties.site";

    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = baseURI;
        userClient.createUser();
        accessToken = userClient.authUser().extract().jsonPath().getString("accessToken");
        orderClient.createOrderWithAuth(1, 2,  "Бессмертный метеоритный бургер",accessToken);
    }

    @DisplayName("Получение заказа пользователя с авторизацией")
    @Description("Позитивная проверка - получение заказа пользователя с авторизацией")
    @Test
    public void getOrderWithAuth() {

        orderClient.getOrderWithAuth(accessToken);
    }

    @After
    public void deleteUser() {
        userClient.deleteUser(accessToken);
    }
}

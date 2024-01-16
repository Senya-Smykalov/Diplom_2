import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.example.OrderClient;
import org.example.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

public class CreateOrderWithoutAuthTest {
    UserClient userClient = new UserClient();
    OrderClient orderClient = new OrderClient();
    private static String baseURI = "https://stellarburgers.nomoreparties.site";


    @Before
    public void setUp() {
        RestAssured.baseURI = baseURI;
        userClient.createUser();
    }

    @DisplayName("Создание заказа пользователем без авторизации")
    @Description("Позитивная проверка - создание заказа пользователем без авторизации")
    @Test
    public void createOrderWithoutAuth() {
        orderClient.createOrderWithoutAuth(1, 2,  "Бессмертный метеоритный бургер");
    }

    @After
    public void deleteUser() {
        String accessToken = userClient.authUser().extract().jsonPath().getString("accessToken");
        userClient.deleteUser(accessToken);
    }


}

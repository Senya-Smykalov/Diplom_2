import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.example.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

public class ChangeDataUserWithAuthTest {
    UserClient userClient = new UserClient();
    private static String baseURI = "https://stellarburgers.nomoreparties.site";

    private static String accessToken;


    @Before
    public void setUp() {
        RestAssured.baseURI = baseURI;
        userClient.createUser();
    }

    @DisplayName("Изменения данных пользователя с авторизацией")
    @Description("Позитивный сценарий - Изменения данных пользователя с авторизацией")
    @Test
    public void changeDataUserWithAuthTest() {
        accessToken = userClient.authUser().extract().jsonPath().getString("accessToken");
        userClient.changeDataUserWithAuth(accessToken);
    }

    @After
    public void deleteUser() {
        userClient.deleteUser(accessToken);
    }
}
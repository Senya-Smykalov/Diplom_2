import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.example.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

public class LoginUserFalseTest {
    UserClient userClient = new UserClient();

    private static String baseURI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        RestAssured.baseURI = baseURI;
        userClient.createUser();
    }

    @DisplayName("Авторизация пользователя c некорректными данными")
    @Description("Негативный сценарий - авторизация пользователя с некорректными данными")
    @Test
    public void loginUserFalseTest() {
        userClient.badAuthUser();
    }

    @After
    public void deleteUser() {
        String accessToken = userClient.authUser().extract().jsonPath().getString("accessToken");
        userClient.deleteUser(accessToken);
    }
}

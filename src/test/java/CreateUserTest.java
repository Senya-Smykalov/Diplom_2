import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CreateUserTest {
    UserClient userClient = new UserClient();
    private static String baseURI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        RestAssured.baseURI = baseURI;
    }


    @DisplayName("Создание пользователя")
    @Description("Позитивный сценарий - создание пользователя")
    @Test
    public void createUser() {
        userClient.createUser();
    }

    @After
    public void deleteUser() {
        String accessToken = userClient.authUser().extract().jsonPath().getString("accessToken");
        userClient.deleteUser(accessToken);
    }

}
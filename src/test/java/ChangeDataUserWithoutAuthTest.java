import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.example.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

public class ChangeDataUserWithoutAuth {
    UserClient userClient = new UserClient();
    private static String baseURI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        RestAssured.baseURI = baseURI;
        userClient.createUser();
    }

    @DisplayName("Попытка изменения данных пользователя без авторизации")
    @Description("Негативный сценарий -Попытка изменения данных пользователя без авторизации")
    @Test
    public void changeDataUserWithoutAuth() {
        userClient.changeDataUserWithoutAuth();
    }

    @After
    public void deleteUser() {
        String accessToken = userClient.authUser().extract().jsonPath().getString("accessToken");
        userClient.deleteUser(accessToken);
    }

}

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.example.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

public class CreateDuplicateUserTest {
    UserClient userClient = new UserClient();
    private static String baseURI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        RestAssured.baseURI = baseURI;
        userClient.createUser();
    }

    @DisplayName("Повторное создание пользователя")
    @Description("Негативный сценарий - попытка создать дубль уже существующего пользователя")
    @Test

    public void createDuplicateUser() {
        userClient.createDuplicateUser();
    }

    @After
    public void deleteUser() {
        String accessToken = userClient.authUser().extract().jsonPath().getString("accessToken");
        userClient.deleteUser(accessToken);
    }

}

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.example.UserClient;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

public class CreateUserWithoutRequiredFieldTest {
    UserClient userClient = new UserClient();
    private static String baseURI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        RestAssured.baseURI = baseURI;
    }

    @DisplayName("Создание пользователя без обязательного поля")
    @Description("Негативный сценарий - попытка создать клиента без обязательного поля")
    @Test
    public void createUserWithoutRequiredField() {
        userClient.createUserWithoutRequiredField();
    }

}

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.example.User;
import org.example.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class createDuplicateUser {
    UserClient userClient = new UserClient();
    private static String baseURI = "https://stellarburgers.nomoreparties.site";
    @Before
    public void setUp() {
        RestAssured.baseURI = baseURI;
    }

    @DisplayName("Повторное создание пользователя")
    @Description("Негативный сценарий - попытка создать дубль уже существующего пользователя")
    @Test

    public void createDuplicateUser(){
        User user = new User("muBest@exmaple.com", "zxcvbnm", "Jonny  ");
        userClient.createUser(user);
        userClient.createDuplicateUser(user);
    }

    @After
    public void deleteUser(){
        User user = new User("muBest@exmaple.com", "zxcvbnm", "Jonny  ");
        String accessToken = userClient.authUser(user).extract().jsonPath().getString("accessToken");
        userClient.deleteUser(accessToken);
    }

}

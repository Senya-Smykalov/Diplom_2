import io.qameta.allure.Description;
import io.restassured.RestAssured;
import model.User;
import org.example.UserClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CreateUserWithoutRequiredField {
    UserClient userClient = new UserClient();
    private static String baseURI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        RestAssured.baseURI = baseURI;
    }
    @DisplayName("Создание пользователя без обязательного поля")
    @Description("Негативный сценарий - попытка создать клиента без обязательного поля")
    @Test
    public void createUserWithoutRequiredField(){
        User user = new User("muBest@exmaple.com", "zxcvbnm", "");
        userClient.createUserWithoutRequiredField(user);
    }

}

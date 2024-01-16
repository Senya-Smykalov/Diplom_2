package org.example;

import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import model.User;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class UserClient {
    public static final String createUserEndPoint = "/api/auth/register";
    public static final String loginUserEndPoint = "/api/auth/login";
    public static final String deleteUserEndPoint = "/api/auth/user";
    public static final String dataChangeEndPoint = "/api/auth/user";
    private static final File newDataUser = new File("src/test/resources/newDataUser.json");
    private static User user = new User("muBest456@exmaple.com", "zxcvbnmzz", "JonnyBravo");
    static Header header = new Header("Content-type", "application/json");
    private static final User badUser = new User("muBesttttt@exmaple.com", "zxcvbnm123", "Smitt");
    private static final User userWithoutField = new User("muBest@exmaple.com", "zxcvbnm", "");


    @Step("Создание пользователя")
    public void createUser() {
        given()
                .header(header)
                .body(user)
                .post(createUserEndPoint)
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    @Step("Попытка создать одинакового пользователя")
    public void createDuplicateUser() {
        given()
                .header(header)
                .body(user)
                .post(createUserEndPoint)
                .then()
                .log()
                .all()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));

    }

    @Step("Создание пользователя без обязательного поля ")

    public void createUserWithoutRequiredField() {
        given()
                .header(header)
                .body(userWithoutField)
                .post(createUserEndPoint)
                .then()
                .log()
                .all()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse authUser() {
        return given()
                .header(header)
                .body(user)
                .post(loginUserEndPoint)
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    @Step("Авторизация пользователя с некорректными данными")
    public void badAuthUser() {
        given()
                .header(header)
                .body(badUser)
                .post(loginUserEndPoint)
                .then()
                .log()
                .all()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Обновление данных пользователя с авторизацией")
    public void changeDataUserWithAuth(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .header(header)
                .when()
                .body(newDataUser)
                .patch(dataChangeEndPoint)
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo("mubestqqq@exmaple.com"))
                .body("user.name", equalTo("Smitt"));
    }

    @Step("Попытка обновления данных пользователя без авторизации")
    public void changeDataUserWithoutAuth() {
        given()
                .header(header)
                .when()
                .body(newDataUser)
                .patch(dataChangeEndPoint)
                .then()
                .log()
                .all()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));

    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .delete(deleteUserEndPoint)
                .then()
                .log()
                .all()
                .statusCode(202);
    }
}

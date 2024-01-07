package org.example;

import io.qameta.allure.Step;
import io.restassured.http.Header;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderClient {
    public static final String createOrderEndPoint = "/api/orders";
    public static final String getUserOrderEndPoint = "/api/orders";
    static Header header = new Header("Content-type", "application/json");
    private static final File createOrder = new File("src/test/resources/createOrder.json");
    private static final File createEmptyOrder = new File("src/test/resources/createEmptyOrder.json");
    private static final File createBadOrder = new File("src/test/resources/createBadOrder.json");


    @Step("Создание заказа авторизованным пользователем")
    public void createOrderWithAuth(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .header(header)
                .body(createOrder)
                .post(createOrderEndPoint)
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("name", equalTo("Метеоритный бургер"))
                .body("order.number", notNullValue())
                .body("success", equalTo(true));
    }

    @Step("Создание заказа пользователем без авторизации")
    public void createOrderWithoutAuth() {
        given()
                .header(header)
                .body(createOrder)
                .post(createOrderEndPoint)
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("name", equalTo("Метеоритный бургер"))
                .body("order.number", notNullValue())
                .body("success", equalTo(true));
    }

    @Step("Создание заказа,список ингредиентов - пуст")
    public void createOrderWithoutIngredients() {
        given()
                .header(header)
                .body(createEmptyOrder)
                .post(createOrderEndPoint)
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Создание заказа из несуществующих ингредиентов")
    public void createBadOrder() {
        given()
                .header(header)
                .body(createBadOrder)
                .post(createOrderEndPoint)
                .then()
                .log()
                .all()
                .statusCode(500);

    }

    @Step("Получение списка заказов пользователя с авторизацией")
    public void getOrderWithAuth(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .header(header)
                .get(getUserOrderEndPoint)
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("orders", notNullValue());
    }

    @Step("Получение списка заказов пользователем без авторизации")
    public void gerOrderWithoutAuth() {
        given()
                .header(header)
                .get(getUserOrderEndPoint)
                .then()
                .log()
                .all()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
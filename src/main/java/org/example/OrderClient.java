package org.example;

import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.io.File;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderClient {
    public static final String createOrderEndPoint = "/api/orders";
    public static final String getUserOrderEndPoint = "/api/orders";

    public static final String getIngredientsEndPoint = "/api/ingredients";

    static Header header = new Header("Content-type", "application/json");

    private static final File createOrder = new File("src/test/resources/createOrder.json");
    private static final File createEmptyOrder = new File("src/test/resources/createEmptyOrder.json");
    private static final File createBadOrder = new File("src/test/resources/createBadOrder.json");

    @Step("Создание заказа авторизованным пользователем")
    public void createOrderWithAuth(int firstIngredient, int twoIngredient, String name, String accessToken) {
        String firstComponent = getIngredient(firstIngredient);
        String twoComponent = getIngredient(twoIngredient);
        String bodyOrder = "{ \"ingredients\": [\"" + firstComponent + "\",\"" + twoComponent + "\"] }";
        Response response = given()
                .header(header)
                .header("Authorization", accessToken)
                .body(bodyOrder)
                .when()
                .post(createOrderEndPoint);
        response
                .then()
                .statusCode(200)
                .body("success", equalTo(true), "name", equalTo(name), "order.number", notNullValue());
    }

    @Step("Получение списка ингредиентов")
    public String getIngredient(int id) {

        Response response = given()
                .header(header)
                .get(getIngredientsEndPoint);
        response
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data._id", notNullValue());


        ArrayList<String> allIdsIngr = response.jsonPath().get("data._id");
        String idIngr = allIdsIngr.get(id);
        System.out.println(idIngr);
        return idIngr;

    }

    @Step("Создание заказа пользователем без авторизации")
    public void createOrderWithoutAuth(int firstIngredient, int twoIngredient, String name) {
        String firstComponent = getIngredient(firstIngredient);
        String twoComponent = getIngredient(twoIngredient);
        String bodyOrder = "{ \"ingredients\": [\"" + firstComponent + "\",\"" + twoComponent + "\"] }";
        Response response = given()
                .header(header)
                .body(bodyOrder)
                .when()
                .post(createOrderEndPoint);
        response
                .then()
                .statusCode(200)
                .body("success", equalTo(true), "name", equalTo(name), "order.number", notNullValue());
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
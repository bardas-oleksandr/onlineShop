package ua.levelup.controller.rest;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import ua.levelup.web.dto.create.OrderCreateDto;

import java.sql.Timestamp;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class OrderRestControllerIntegrationTest {

    private static final String PATH = "onlineShop/rest/order";
    private static final String USER = "/user";
    private static final String ID = "/{id}";

    /*Сценарий: сохранение заказа товаров в базу данных;
    *           заказ имеет не валидные параметры.
    *   Дано:
    *       -   объект OrderCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order"
    *   Результат:
    *       -   статус ответа = UNPROCESSABLE_ENTITY
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndOrderIsNotValid_whenPost_thenUnprocessable()
            throws Exception {
        final String ADDRESS = null;
        OrderCreateDto order = new OrderCreateDto(1,ADDRESS,new Timestamp(1)
                ,true,0,0);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(order)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    /*Сценарий: сохранение заказа товаров в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект OrderCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void createTest_givenTextPlainAcceptAndJSONContentType_whenPost_thenNotAcceptable()
            throws Exception {
        final String ADDRESS = "address";
        OrderCreateDto order = new OrderCreateDto(1,ADDRESS,new Timestamp(1)
                ,true,0,0);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(order)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: сохранение заказа товаров в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект OrderCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/order"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void createTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String ADDRESS = null;
        OrderCreateDto order = new OrderCreateDto(1,ADDRESS,new Timestamp(1)
                ,true,0,0);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(order)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: сохранение заказа товаров в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект OrderCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определен
    *       -   url = "/rest/order"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void createTest_givenTextPlainAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String ADDRESS = null;
        OrderCreateDto order = new OrderCreateDto(1,ADDRESS,new Timestamp(1)
                ,true,0,0);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(order)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение заказа по его id;
    *           заказ с таким id есть в БД.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   OrderViewDto c id=1;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getTest_givenJSONAcceptAndJSONContentTypeAndCorrectId_whenGet_thenOk()
            throws Exception {
        int id = 1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH + ID)
        .then()
                .log().body()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .log().all()
                .body("id", is(id));
    }

    /*Сценарий: получение заказа по его id;
    *           заказа с таким id нет в БД.
    *   Дано:
    *       -   id = 999
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getTest_givenJSONAcceptAndJSONContentTypeAndNotCorrectId_whenGet_thenNotFound()
            throws Exception {
        int id = 999;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH + ID)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    /*Сценарий: получение заказа по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getTest_givenTextPlainAcceptAndJSONContentType_whenGet_thenNotAcceptable()
            throws Exception {
        int id = 1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH + ID)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: получение заказа по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getTest_givenJSONAcceptAndNoJSONContentType_whenGet_thenUnsupported()
            throws Exception {
        int id = 1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_XML_VALUE)
        .when()
                .get(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение заказа по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getTest_givenTextPlainAcceptAndNoJSONContentType_whenGet_thenUnsupported()
            throws Exception {
        int id = 1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
        .when()
                .get(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: изменение заказа в базе данных;
    *           заказ имеет валидные параметры;
    *           заказ существует в БД.
    *   Дано:
    *       -   id = 1
    *       -   объект OrderCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   объект OrderViewDto;
    *       -   статус ответа = OK
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndObjectExists_whenPut_thenOk()
            throws Exception {
        final String ADDRESS = "new address";
        OrderCreateDto order = new OrderCreateDto(2,ADDRESS,new Timestamp(1)
                ,true,0,0);
        int id = 1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(order)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .log().body()
                .body("address", is(ADDRESS));
    }

    /*Сценарий: изменение заказа в базе данных;
    *           заказ имеет не валидные параметры.
    *   Дано:
    *       -   id = 1
    *       -   объект OrderCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = UNPROCESSABLE_ENTITY
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndOrderIsNotValid_whenPut_thenUnprocessable()
            throws Exception {
        final String ADDRESS = null;
        OrderCreateDto order = new OrderCreateDto(2,ADDRESS,new Timestamp(1)
                ,true,0,0);
        int id = 1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(order)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    /*Сценарий: изменение заказа в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   объект OrderCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_givenTextPlainAcceptAndJSONContentType_whenPut_thenNotAcceptable()
            throws Exception {
        final String ADDRESS = "new address";
        OrderCreateDto order = new OrderCreateDto(2,ADDRESS,new Timestamp(1)
                ,true,0,0);
        int id = 1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(order)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: изменение заказа в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   объект OrderCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String ADDRESS = "new address";
        OrderCreateDto order = new OrderCreateDto(2,ADDRESS,new Timestamp(1)
                ,true,0,0);
        int id = 1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(order)
        .when()
                .put(PATH + ID)
         .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: изменение заказа в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   объект OrderCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определен
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_givenTextPlainAcceptAndNoJSONContentType_whenPut_thenUnsupported()
            throws Exception {
        final String ADDRESS = "new address";
        OrderCreateDto order = new OrderCreateDto(2,ADDRESS,new Timestamp(1)
                ,true,0,0);
        int id =1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(order)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: удаление заказа по его id;
    *           заказ с таким id есть в БД.
    *   Дано:
    *       -   id = 3
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = NO_CONTENT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void deleteTest_givenJSONAcceptAndJSONContentTypeAndCorrectId_whenDelete_thenNoContent()
            throws Exception {
        int id = 3;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .delete(PATH + ID)
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    /*Сценарий: удаление заказа по его id;
    *           заказа с таким id нет в БД.
    *   Дано:
    *       -   id = 999
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void deleteTest_givenJSONAcceptAndJSONContentTypeAndNotCorrectId_whenDelete_thenNotFound()
            throws Exception {
        int id = 999;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .delete(PATH + ID)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    /*Сценарий: удаление заказа по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void deleteTest_givenTextPlainAcceptAndJSONContentType_whenDelete_thenNotAcceptable()
            throws Exception {
        int id = 1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .delete(PATH + ID)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: удаление заказа по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void deleteTest_givenJSONAcceptAndNoJSONContentType_whenDelete_thenUnsupported()
            throws Exception {
        int id = 1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_XML_VALUE)
        .when()
                .delete(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: удаление заказа по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/order/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void deleteTest_givenTextPlainAcceptAndNoJSONContentType_whenDelete_thenUnsupported()
            throws Exception {
        int id = 1;
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
        .when()
                .delete(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение списка всех заказов;
    *           в базе данных есть заказы.
    *   Дано:
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order"
    *   Результат:
    *       -   List<OrderViewDto>;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getAllTest_givenJSONAcceptAndJSONContentType_whenGet_thenOk()
            throws Exception {
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH)
        .then()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

    /*Сценарий: получение списка всех заказов;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getAllTest_givenTextPlainAcceptAndJSONContentType_whenGet_thenNotAcceptable()
            throws Exception {
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: получение списка всех заказов;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/order"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getAllTest_givenJSONAcceptAndNoJSONContentType_whenGet_thenUnsupported()
            throws Exception {
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_XML_VALUE)
        .when()
                .get(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение списка всех заказов;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/order"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getAllTest_givenTextPlainAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
        .when()
                .get(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение списка всех заказов пользователя с заданным id;
    *           в базе данных есть заказы для пользователя.
    *   Дано:
    *       -   id = 2
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/user/{id}"
    *   Результат:
    *       -   List<OrderViewDto>;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getByUserIdTest_givenJSONAcceptAndJSONContentType_whenGet_thenOk()
            throws Exception {
        int id = 2;
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
        .when()
                .get(PATH + USER + ID)
        .then()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

    /*Сценарий: получение списка всех заказов пользователя с заданным id;
    *           в базе данных нет заказов для пользователя.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/user/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getByUserIdTest_givenJSONAcceptAndJSONContentTypeAndEmptyDb_whenGet_thenNotFound()
            throws Exception {
        int id = 1;
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
        .when()
                .get(PATH + USER + ID)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    /*Сценарий: получение списка всех заказов пользователя с заданным id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 2
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/order/user/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getByUserIdTest_givenTextPlainAcceptAndJSONContentType_whenGet_thenNotAcceptable()
            throws Exception {
        int id = 2;
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
        .when()
                .get(PATH + USER + ID)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: получение списка всех заказов пользователя с заданным id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 2
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/order/user/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getByUserIdTest_givenJSONAcceptAndNoJSONContentType_whenGet_thenUnsupported()
            throws Exception {
        int id = 2;
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_XML_VALUE)
                .pathParam("id", id)
        .when()
                .get(PATH + USER + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение списка всех заказов пользователя с заданным id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 2
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/order/user/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getByUserIdTest_givenTextPlainAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        int id = 2;
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .pathParam("id", id)
        .when()
                .get(PATH + USER + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }
}
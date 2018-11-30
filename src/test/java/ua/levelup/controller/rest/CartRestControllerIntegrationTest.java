package ua.levelup.controller.rest;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ua.levelup.web.dto.create.ProductInCartCreateDto;

import static io.restassured.RestAssured.given;

public class CartRestControllerIntegrationTest {

    private static final String PATH = "onlineShop/rest/cart";

    /*Сценарий: получение текущей корзины товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/cart"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    public void getTest_givenTextPlainAcceptAndJSONContentType_whenGet_thenNotAcceptable()
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

    /*Сценарий: получение текущей корзины товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/cart"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    public void getTest_givenJSONAcceptAndNoJSONContentType_whenGet_thenUnsupported()
            throws Exception {
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_XML_VALUE)
        .when()
                .get(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение текущей корзины товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/cart"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    public void getTest_givenTextPlainAcceptAndNoJSONContentType_whenGet_thenUnsupported()
            throws Exception {
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
        .when()
                .get(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: добавление товара в корзину;
    *           добавляемый товар имеет валидные параметры;
    *   Дано:
    *       -   объект ProductInCartCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/cart"
    *   Результат:
    *       -   объект CartViewDto;
    *       -   статус ответа = OK
    * */
    @Test
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndProductInCartIsValid_whenPut_thenOk()
            throws Exception {
        int productId = 1;
        int count = 2;
        ProductInCartCreateDto product = new ProductInCartCreateDto(productId,count);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .put(PATH)
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    /*Сценарий: добавление товара в корзину;
    *           добавляемый товар имеет не валидные параметры;
    *   Дано:
    *       -   объект ProductInCartCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/cart"
    *   Результат:
    *       -   статус ответа = UNPROCESSABLE_ENTITY
    * */
    @Test
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndProductInCartIsNotValid_whenPut_thenUnprocessable()
            throws Exception {
        int productId = 0;
        int count = 2;
        ProductInCartCreateDto product = new ProductInCartCreateDto(productId,count);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .put(PATH)
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    /*Сценарий: добавление товара в корзину;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ProductInCartCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/cart"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    public void updateTest_givenTextPlainAcceptAndJSONContentType_whenPut_thenNotAcceptable()
            throws Exception {
        int productId = 1;
        int count = 2;
        ProductInCartCreateDto product = new ProductInCartCreateDto(productId,count);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .put(PATH)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: добавление товара в корзину;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ProductInCartCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/cart"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    public void updateTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        int productId = 1;
        int count = 2;
        ProductInCartCreateDto product = new ProductInCartCreateDto(productId,count);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .put(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: добавление товара в корзину;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ProductInCartCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определен
    *       -   url = "/rest/cart"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    public void updateTest_givenTextPlainAcceptAndNoJSONContentType_whenPut_thenUnsupported()
            throws Exception {
        int productId = 1;
        int count = 2;
        ProductInCartCreateDto product = new ProductInCartCreateDto(productId,count);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(product)
        .when()
                .put(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: очистка корзины от товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/cart"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    public void deleteTest_givenTextPlainAcceptAndJSONContentType_whenDelete_thenNotAcceptable()
            throws Exception {
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .delete(PATH)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: очистка корзины от товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/cart"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    public void deleteTest_givenJSONAcceptAndNoJSONContentType_whenDelete_thenUnsupported()
            throws Exception {
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_XML_VALUE)
        .when()
                .delete(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: очистка корзины от товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/cart"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    public void deleteTest_givenTextPlainAcceptAndNoJSONContentType_whenDelete_thenUnsupported()
            throws Exception {
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .when()
                .delete(PATH)
                .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }
}
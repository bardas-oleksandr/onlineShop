package ua.levelup.controller.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.web.dto.SearchParamsDto;
import ua.levelup.web.dto.create.ProductCreateDto;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class ProductRestControllerIntegrationTest {

    private static final String PATH = "onlineShop/rest/product";
    private static final String SEARCH = "/search";
    private static final String ID = "/{id}";

    /*Сценарий: сохранение товара в базу данных;
    *           товар имеет валидные параметры;
    *           имя товара уникально.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product"
    *   Результат:
    *       -   объект ProductViewDto;
    *       -   статус ответа = CREATED
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndCorrectObject_whenPost_thenCreated()
            throws Exception {
        final String NAME = "new product";
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .post(PATH)
        .then()
                .log().body()
                .statusCode(HttpStatus.CREATED.value())
                .assertThat()
                .log().body()
                .body("name", is(NAME));
    }

    /*Сценарий: сохранение товара в базу данных;
    *           имя товара не уникально.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product"
    *   Результат:
    *       -   статус ответа = CONFLICT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndProductNameIsNotUnique_whenPost_thenConfliсt()
            throws Exception {
        final String NAME = "Cygnet Splash Mats";
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    /*Сценарий: сохранение товара в базу данных;
    *           товар имеет не валидные параметры.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product"
    *   Результат:
    *       -   статус ответа = UNPROCESSABLE_ENTITY
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndProductIsNotValid_whenPost_thenUnprocessable()
            throws Exception {
        final String NAME = null;
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    /*Сценарий: сохранение товара в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void createTest_givenTextPlainAcceptAndJSONContentType_whenPost_thenNotAcceptable()
            throws Exception {
        final String NAME = "new product";
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: сохранение товара в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/product"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void createTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new product";
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: сохранение товара в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   url = "/rest/product"
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определен
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void createTest_givenTextPlainAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new product";
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(product)
        .when()
                .post(PATH)
                .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение товара по его id;
    *           товар с таким id есть в БД.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   ProductViewDto c id=1;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
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

    /*Сценарий: получение товара по его id;
    *           товара с таким id нет в БД.
    *   Дано:
    *       -   id = 999
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
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

    /*Сценарий: получение товара по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
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

    /*Сценарий: получение товара по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
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

    /*Сценарий: получение товара по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   url = "/rest/product/{id}"
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
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

    /*Сценарий: изменение товара в базе данных;
    *           товар имеет валидные параметры;
    *           товар существует в БД.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   объект ProductViewDto;
    *       -   статус ответа = OK
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndObjectExists_whenPut_thenOk()
            throws Exception {
        final String NAME = "new name";
        int id = 1;
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .log().body()
                .body("name", is(NAME));
    }

    /*Сценарий: изменение товара в базе данных;
    *           имя товара не уникально.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = CONFLICT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndProductNameIsNotUnique_whenPut_thenConfliсt()
            throws Exception {
        final String NAME = "Fox Bait and Glug Tubs LARGE";
        int id = 1;
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    /*Сценарий: изменение товара в базе данных;
    *           товар имеет не валидные параметры.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = UNPROCESSABLE_ENTITY
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndProductIsNotValid_whenPut_thenUnprocessable()
            throws Exception {
        final String NAME = null;
        int id = 1;
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    /*Сценарий: изменение товара в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_givenTextPlainAcceptAndJSONContentType_whenPut_thenNotAcceptable()
            throws Exception {
        final String NAME = "new name";
        int id = 1;
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: изменение товара в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new name";
        int id = 1;
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(product)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: изменение товара в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ProductCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определен
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_givenTextPlainAcceptAndNoJSONContentType_whenPut_thenUnsupported()
            throws Exception {
        final String NAME = "new name";
        int id =1;
        ProductCreateDto product = new ProductCreateDto(NAME,1.5f,true
                ,"description",1,1);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(product)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: удаление товара по его id;
    *           товар с таким id есть в БД.
    *   Дано:
    *       -   id = 54
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = NO_CONTENT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void deleteTest_givenJSONAcceptAndJSONContentTypeAndCorrectId_whenDelete_thenNoContent()
            throws Exception {
        int id = 54;
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

    /*Сценарий: удаление товара по его id;
    *           товара с таким id нет в БД.
    *   Дано:
    *       -   id = 999
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
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

    /*Сценарий: удаление товара по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
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

    /*Сценарий: удаление товара по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
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

    /*Сценарий: удаление товара по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/product/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
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

    /*Сценарий: получение фильтрованного списка товаров;
    *           в базе данных есть товары, соответствующие параметрам запроса.
    *   Дано:
    *       -   Объект SearchParamsDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/search"
    *   Результат:
    *       -   List<ProductViewDto>;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void searchProductsTest_givenJSONAcceptAndJSONContentType_whenPost_thenOk()
            throws Exception {
        SearchParamsDto searchParamsDto = new SearchParamsDto(1,1
                ,8,true,0.5f,200.0f,0);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(searchParamsDto)
        .when()
                .post(PATH + SEARCH)
        .then()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

    /*Сценарий: получение фильтрованного списка товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/product/search"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void searchProductsTest_givenTextPlainAcceptAndJSONContentType_whenPost_thenNotAcceptable()
            throws Exception {
        SearchParamsDto searchParamsDto = new SearchParamsDto(1,1
                ,8,true,0.5f,200.0f,0);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(searchParamsDto)
        .when()
                .post(PATH + SEARCH)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: получение фильтрованного списка товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/product/search"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void searchProductsTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        SearchParamsDto searchParamsDto = new SearchParamsDto(1,1
                ,8,true,0.5f,200.0f,0);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_XML_VALUE)
                .body(searchParamsDto)
        .when()
                .post(PATH + SEARCH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение фильтрованного списка товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/product/search"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void searchProductsTest_givenTextPlainAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        SearchParamsDto searchParamsDto = new SearchParamsDto(1,1
                ,8,true,0.5f,200.0f,0);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(searchParamsDto)
        .when()
                .post(PATH + SEARCH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }
}
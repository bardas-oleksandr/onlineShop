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
import ua.levelup.web.dto.create.ManufacturerCreateDto;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class ManufacturerRestControllerIntegrationTest {

    private static final String PATH = "onlineShop/rest/manufacturer";
    private static final String ID = "/{id}";

    /*Сценарий: сохранение производителя товаров в базу данных;
    *           производитель имеет валидные параметры;
    *           имя производителя уникально.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer"
    *   Результат:
    *       -   объект ManufacturerViewDto;
    *       -   статус ответа = CREATED
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndCorrectObject_whenPost_thenCreated()
            throws Exception {
        final String NAME = "new manufacturer";
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(manufacturer)
        .when()
                .post(PATH)
        .then()
                .log().body()
                .statusCode(HttpStatus.CREATED.value())
                .assertThat()
                .log().body()
                .body("name", is(NAME));
    }

    /*Сценарий: сохранение производителя товаров в базу данных;
    *           имя производителя не уникально.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer"
    *   Результат:
    *       -   статус ответа = CONFLICT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndManufacturerNameIsNotUnique_whenPost_thenConfliсt()
            throws Exception {
        final String NAME = "Yo-zuri";
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(manufacturer)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    /*Сценарий: сохранение производителя товаров в базу данных;
    *           производитель имеет не валидные параметры.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer"
    *   Результат:
    *       -   статус ответа = UNPROCESSABLE_ENTITY
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndManufacturerIsNotValid_whenPost_thenUnprocessable()
            throws Exception {
        final String NAME = null;
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(manufacturer)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    /*Сценарий: сохранение производителя товаров в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void createTest_givenTextPlainAcceptAndJSONContentType_whenPost_thenNotAcceptable()
            throws Exception {
        final String NAME = "new manufacturer";
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(manufacturer)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: сохранение производителя товаров в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/manufacturer"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void createTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new manufacturer";
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(manufacturer)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: сохранение производителя товаров в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   url = "/rest/manufacturer"
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определен
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void createTest_givenTextPlainAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new manufacturer";
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(manufacturer)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение производителя товаров по его id;
    *           производитель с таким id есть в БД.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   ManufacturerViewDto c id=1;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

    /*Сценарий: получение производителя товаров по его id;
    *           производитель с таким id нет в БД.
    *   Дано:
    *       -   id = 999
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

    /*Сценарий: получение производителя товаров по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

    /*Сценарий: получение производителя товаров по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

    /*Сценарий: получение производителя товаров по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   url = "/rest/manufacturer/{id}"
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

    /*Сценарий: изменение производителя товаров в базе данных;
    *           производитель имеет валидные параметры;
    *           производитель существует в БД.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   объект ManufacturerViewDto;
    *       -   статус ответа = OK
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndObjectExists_whenPut_thenOk()
            throws Exception {
        final String NAME = "new name";
        int id = 1;
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(manufacturer)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .log().body()
                .body("name", is(NAME));
    }

    /*Сценарий: изменение производителя товаров в базе данных;
    *           имя производителя не уникально.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = CONFLICT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndManufacturerNameIsNotUnique_whenPut_thenConfliсt()
            throws Exception {
        final String NAME = "Yo-zuri";
        int id = 1;
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(manufacturer)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    /*Сценарий: изменение производителя товаров в базе данных;
    *           производитель имеет не валидные параметры.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = UNPROCESSABLE_ENTITY
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndManufacturerIsNotValid_whenPut_thenUnprocessable()
            throws Exception {
        final String NAME = null;
        int id = 1;
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(manufacturer)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    /*Сценарий: изменение производителя товаров в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void updateTest_givenTextPlainAcceptAndJSONContentType_whenPut_thenNotAcceptable()
            throws Exception {
        final String NAME = "new name";
        int id = 1;
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(manufacturer)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: изменение производителя товаров в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void updateTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new name";
        int id = 1;
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(manufacturer)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: изменение производителя товаров в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект ManufacturerCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определен
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void updateTest_givenTextPlainAcceptAndNoJSONContentType_whenPut_thenUnsupported()
            throws Exception {
        final String NAME = "new name";
        int id =1;
        ManufacturerCreateDto manufacturer = new ManufacturerCreateDto(NAME);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(manufacturer)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: удаление производителя товаров по его id;
    *           производитель с таким id есть в БД.
    *   Дано:
    *       -   id = 10
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = NO_CONTENT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void deleteTest_givenJSONAcceptAndJSONContentTypeAndCorrectId_whenDelete_thenNoContent()
            throws Exception {
        int id = 10;
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

    /*Сценарий: удаление производителя товаров по его id;
    *           производителя с таким id нет в БД.
    *   Дано:
    *       -   id = 999
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

    /*Сценарий: удаление производителя товаров по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

    /*Сценарий: удаление производителя товаров по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

    /*Сценарий: удаление производителя товаров по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/manufacturer/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

    /*Сценарий: получение списка всех производителей товаров;
    *           в базе данных есть производители.
    *   Дано:
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer"
    *   Результат:
    *       -   List<ManufacturerViewDto>;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

//    /*Сценарий: получение списка производителей товаров;
//    *           в базе данных нет производителей.
//    *   Дано:
//    *       -   Accept = "application/json;charset=UTF-8"
//    *       -   Content-Type = "application/json;charset=UTF-8"
//    *       -   url = "/rest/manufacturer"
//    *   Результат:
//    *       -   статус ответа = NOT_FOUND
//    * */
//    @Test
//    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
//    public void getAllTest_givenJSONAcceptAndJSONContentTypeAndEmptyDb_whenGet_thenNotFound()
//            throws Exception {
//        given()
//                .log().all()
//                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
//        .when()
//                .get(PATH)
//        .then()
//                .statusCode(HttpStatus.NOT_FOUND.value());
//    }

    /*Сценарий: получение списка производителей товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/manufacturer"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

    /*Сценарий: получение списка производителей товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/manufacturer"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
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

    /*Сценарий: получение списка производителя товаров;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/manufacturer"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void getAllTest_givenTextPlainAcceptAndNoJSONContentType_whenGet_thenUnsupported()
            throws Exception {
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
        .when()
                .get(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }
}
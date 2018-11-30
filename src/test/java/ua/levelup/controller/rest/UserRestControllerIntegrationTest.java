package ua.levelup.controller.rest;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.create.UserRegisterCreateDto;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class UserRestControllerIntegrationTest {

    private static final String PATH = "onlineShop/rest/user";
    private static final String ID = "/{id}";

    /*Сценарий: сохранение пользователя в базу данных;
    *           пользователь имеет валидные параметры;
    *           имя пользователя уникально.
    *   Дано:
    *       -   объект UserRegisterCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user"
    *   Результат:
    *       -   объект UserViewDto;
    *       -   статус ответа = CREATED
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndCorrectObject_whenPost_thenCreated()
            throws Exception {
        final String NAME = "new user";
        UserRegisterCreateDto user = new UserRegisterCreateDto(NAME,"1234"
                ,"mail@gmail.com",1);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(user)
        .when()
                .post(PATH)
        .then()
                .log().body()
                .statusCode(HttpStatus.CREATED.value())
                .assertThat()
                .log().body()
                .body("userName", is(NAME));
    }

    /*Сценарий: сохранение пользователя в базу данных;
    *           имя пользователя не уникально.
    *   Дано:
    *       -   объект UserRegisterCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user"
    *   Результат:
    *       -   статус ответа = CONFLICT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndUserNameIsNotUnique_whenPost_thenConfliсt()
            throws Exception {
        final String NAME = "John Doe";
        UserRegisterCreateDto user = new UserRegisterCreateDto(NAME,"1234"
                ,"mail@gmail.com",1);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(user)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    /*Сценарий: сохранение пользователя в базу данных;
    *           пользователь имеет не валидные параметры.
    *   Дано:
    *       -   объект UserRegisterCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user"
    *   Результат:
    *       -   статус ответа = UNPROCESSABLE_ENTITY
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndUserIsNotValid_whenPost_thenUnprocessable()
            throws Exception {
        final String NAME = null;
        UserRegisterCreateDto user = new UserRegisterCreateDto(NAME,"1234"
                ,"mail@gmail.com",1);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(user)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    /*Сценарий: сохранение пользователя в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект UserRegisterCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void createTest_givenTextPlainAcceptAndJSONContentType_whenPost_thenNotAcceptable()
            throws Exception {
        final String NAME = "new user";
        UserRegisterCreateDto user = new UserRegisterCreateDto(NAME,"1234"
                ,"mail@gmail.com",1);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(user)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: сохранение пользователя в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект UserRegisterCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/user"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void createTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new user";
        UserRegisterCreateDto user = new UserRegisterCreateDto(NAME,"1234"
                ,"mail@gmail.com",1);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(user)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: сохранение пользователя в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект UserRegisterCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определен
    *       -   url = "/rest/user"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void createTest_givenTextPlainAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new user";
        UserRegisterCreateDto user = new UserRegisterCreateDto(NAME,"1234"
                ,"mail@gmail.com",1);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(user)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение пользователя по его id;
    *           пользователь с таким id есть в БД.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   UserViewDto c id=1;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: получение пользователя по его id;
    *           пользователя с таким id нет в БД.
    *   Дано:
    *       -   id = 999
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: получение пользователя по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: получение пользователя по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: получение пользователя по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: изменение пользователя в базе данных;
    *           пользователь имеет валидные параметры;
    *           пользователь существует в БД.
    *   Дано:
    *       -   id = 1
    *       -   объект UserCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   объект UserViewDto;
    *       -   статус ответа = OK
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndObjectExists_whenPut_thenOk()
            throws Exception {
        final String NAME = "new name";
        int id = 1;
        UserCreateDto user = new UserCreateDto(NAME,"email@gmail.com",0);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(user)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .log().body()
                .body("userName", is(NAME));
    }

    /*Сценарий: изменение пользователя в базе данных;
    *           имя пользователя не уникально.
    *   Дано:
    *       -   id = 1
    *       -   объект UserCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = CONFLICT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndUserNameIsNotUnique_whenPut_thenConfliсt()
            throws Exception {
        final String NAME = "Dr. Evil";
        int id = 1;
        UserCreateDto user = new UserCreateDto(NAME,"email@gmail.com",2);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(user)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    /*Сценарий: изменение пользователя в базе данных;
    *           пользователь имеет не валидные параметры.
    *   Дано:
    *       -   id = 1
    *       -   объект UserCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = UNPROCESSABLE_ENTITY
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndUserIsNotValid_whenPut_thenUnprocessable()
            throws Exception {
        final String NAME = null;
        int id = 1;
        UserCreateDto user = new UserCreateDto(NAME,"email@gmail.com",0);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(user)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    /*Сценарий: изменение пользователя в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   объект UserCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateTest_givenTextPlainAcceptAndJSONContentType_whenPut_thenNotAcceptable()
            throws Exception {
        final String NAME = "new name";
        int id = 1;
        UserCreateDto user = new UserCreateDto(NAME,"email@gmail.com",0);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(user)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: изменение пользователя в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   объект UserCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new name";
        int id = 1;
        UserCreateDto user = new UserCreateDto(NAME,"email@gmail.com",0);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(user)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: изменение пользователя в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   объект UserCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определен
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateTest_givenTextPlainAcceptAndNoJSONContentType_whenPut_thenUnsupported()
            throws Exception {
        final String NAME = "new name";
        int id =1;
        UserCreateDto user = new UserCreateDto(NAME,"email@gmail.com",0);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(user)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: удаление пользователя по его id;
    *           пользователь с таким id есть в БД.
    *   Дано:
    *       -   id = 4
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = NO_CONTENT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void deleteTest_givenJSONAcceptAndJSONContentTypeAndCorrectId_whenDelete_thenNoContent()
            throws Exception {
        int id = 4;
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

    /*Сценарий: удаление пользователя по его id;
    *           пользователя с таким id нет в БД.
    *   Дано:
    *       -   id = 999
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: удаление пользователя по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: удаление пользователя по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: удаление пользователя по его id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/user/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: получение списка всех пользователей;
    *           в базе данных есть пользователи.
    *   Дано:
    *       -   Объект SearchParamsDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user"
    *   Результат:
    *       -   List<UserViewDto>;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: получение списка всех пользователей;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/user"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: получение списка всех пользователей;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/user"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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

    /*Сценарий: получение списка всех пользователей;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/user"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
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
}
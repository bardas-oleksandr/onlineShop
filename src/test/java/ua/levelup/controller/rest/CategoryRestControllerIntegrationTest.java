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
import ua.levelup.web.dto.create.CategoryCreateDto;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class CategoryRestControllerIntegrationTest {

    private static final String PATH = "onlineShop/rest/category";
    private static final String LEVEL = "/level";
    private static final String PARENT = "/parent";
    private static final String ID = "/{id}";

    /*Сценарий: сохранение категории товаров в базу данных.
    *           Категория имеет валидные параметры.
    *           Имя категории уникально.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category"
    *   Результат:
    *       -   объект CategoryViewDto;
    *       -   статус ответа = CREATED
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndCorrectObject_whenPost_thenCreated()
            throws Exception {
        final String NAME = "new category";
        final int PARENT_CATEGORY_ID = 0;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
        .when()
                .post(PATH)
        .then()
                .log().body()
                .statusCode(HttpStatus.CREATED.value())
                .assertThat()
                .log().body()
                .body("name", is(NAME));
    }

    /*Сценарий: сохранение категории товаров в базу данных.
    *           Имя категории не уникально.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category"
    *   Результат:
    *       -   статус ответа = CONFLICT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndCategoryNameIsNotUnique_whenPost_thenConfliсt()
            throws Exception {
        final String NAME = "Carp";
        final int PARENT_CATEGORY_ID = 0;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    /*Сценарий: сохранение категории товаров в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category"
    *   Результат:
    *       -   статус ответа = UNPROCESSABLE_ENTITY
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void createTest_givenJSONAcceptAndJSONContentTypeAndCategoryIsNotValid_whenPost_thenUnprocessable()
            throws Exception {
        final String NAME = null;
        final int PARENT_CATEGORY_ID = 0;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    /*Сценарий: сохранение категории товаров в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void createTest_givenTextPlainAcceptAndJSONContentType_whenPost_thenNotAcceptable()
            throws Exception {
        final String NAME = "new category";
        final int PARENT_CATEGORY_ID = 0;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: сохранение категории товаров в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/category"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void createTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new category";
        final int PARENT_CATEGORY_ID = 0;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: сохранение категории товаров в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   url = "/rest/category"
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определен
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void createTest_givenTextPlainAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new category";
        final int PARENT_CATEGORY_ID = 0;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(category)
        .when()
                .post(PATH)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение категории товаров по ее id;
    *           категория с таким id есть в БД.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   CategoryViewDto c id=1;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
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

    /*Сценарий: получение категории товаров по ее id;
    *           категории с таким id нет в БД.
    *   Дано:
    *       -   id = 999
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
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

    /*Сценарий: получение категории товаров по ее id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
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

    /*Сценарий: получение категории товаров по ее id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
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

    /*Сценарий: получение категории товаров по ее id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   url = "/rest/category/{id}"
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
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

    /*Сценарий: изменение категории товаров в базе данных;
    *           категория имеет валидные параметры;
    *           категория существует в БД.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   объект CategoryViewDto;
    *       -   статус ответа = OK
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndObjectExists_whenPut_thenOk()
            throws Exception {
        final String NAME = "new name";
        final int PARENT_CATEGORY_ID = 0;
        int id = 1;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .log().body()
                .body("name", is(NAME));
    }

    /*Сценарий: изменение категории товаров в базе данных;
    *           имя категории не уникально.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = CONFLICT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndCategoryNameIsNotUnique_whenPut_thenConfliсt()
            throws Exception {
        final String NAME = "Predator";
        final int PARENT_CATEGORY_ID = 0;
        int id = 1;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    /*Сценарий: изменение категории товаров в базе данных;
    *           категория имеет не валидные параметры.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = UNPROCESSABLE_ENTITY
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void updateTest_givenJSONAcceptAndJSONContentTypeAndCategoryIsNotValid_whenPut_thenUnprocessable()
            throws Exception {
        final String NAME = null;
        final int PARENT_CATEGORY_ID = 0;
        int id = 1;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    /*Сценарий: изменение категории товаров в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void updateTest_givenTextPlainAcceptAndJSONContentType_whenPut_thenNotAcceptable()
            throws Exception {
        final String NAME = "new name";
        final int PARENT_CATEGORY_ID = 0;
        int id = 1;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
         .when()
                .put(PATH + ID)
         .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: изменение категории товаров в базе данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void updateTest_givenJSONAcceptAndNoJSONContentType_whenPost_thenUnsupported()
            throws Exception {
        final String NAME = "new name";
        final int PARENT_CATEGORY_ID = 0;
        int id = 1;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(category)
         .when()
                .put(PATH + ID)
         .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: изменение категории товаров в базу данных;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   объект CategoryCreateDto
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определен
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void updateTest_givenTextPlainAcceptAndNoJSONContentType_whenPut_thenUnsupported()
            throws Exception {
        final String NAME = "new name";
        final int PARENT_CATEGORY_ID = 0;
        int id =1;
        CategoryCreateDto category = new CategoryCreateDto(NAME,PARENT_CATEGORY_ID);
        given()
                .log().all()
                .pathParam("id", id)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .body(category)
        .when()
                .put(PATH + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: удаление категории товаров по ее id;
    *           категория с таким id есть в БД.
    *   Дано:
    *       -   id = 20
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = NO_CONTENT
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void deleteTest_givenJSONAcceptAndJSONContentTypeAndCorrectId_whenDelete_thenNoContent()
            throws Exception {
        int id = 20;
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

    /*Сценарий: удаление категории товаров по ее id;
    *           категории с таким id нет в БД.
    *   Дано:
    *       -   id = 999
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
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

    /*Сценарий: удаление категории товаров по ее id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
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

    /*Сценарий: удаление категории товаров по ее id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
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

    /*Сценарий: удаление категории товаров по ее id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/category/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
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

    /*Сценарий: получение списка категорий товаров заданного уровня;
    *           категории заданного уровня есть в базе данных.
    *   Дано:
    *       -   level = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/level/{id}"
    *   Результат:
    *       -   получен список категорий;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByLevelTest_givenJSONAcceptAndJSONContentTypeAndCorrectLevel_whenGet_thenOk()
            throws Exception {
        int level = 1;
        given()
                .log().all()
                .pathParam("id", level)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH + LEVEL + ID)
        .then()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

    /*Сценарий: получение списка категорий товаров заданного уровня;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   level = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/level/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByLevelTest_givenTextPlainAcceptAndJSONContentType_whenGet_thenNotAcceptable()
            throws Exception {
        int level = 1;
        given()
                .log().all()
                .pathParam("id", level)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH + LEVEL + ID)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: получение списка категорий товаров заданного уровня;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   level = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/category/level/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByLevelTest_givenJSONAcceptAndNoJSONContentType_whenGet_thenUnsupported()
            throws Exception {
        int level = 1;
        given()
                .log().all()
                .pathParam("id", level)
                .header("Accept", MediaType.APPLICATION_XML_VALUE)
        .when()
                .get(PATH + LEVEL + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение списка категорий товаров заданного уровня;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   level = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/category/level/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByLevelTest_givenTextPlainAcceptAndNoJSONContentType_whenGet_thenUnsupported()
            throws Exception {
        int level = 1;
        given()
                .log().all()
                .pathParam("id", level)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
        .when()
                .get(PATH + LEVEL + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение списка подкатегорий товаров для категории с заданным id;
    *           категория имеет подкатегории.
    *   Дано:
    *       -   parent id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/parent/{id}"
    *   Результат:
    *       -   получен список категорий;
    *       -   статус ответа = ОК
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByParentIdTest_givenJSONAcceptAndJSONContentTypeAndCorrectId_whenGet_thenOk()
            throws Exception {
        int parentId = 1;
        given()
                .log().all()
                .pathParam("id", parentId)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH + PARENT + ID)
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    /*Сценарий: получение списка подкатегорий товаров для категории с заданным id;
    *           категория не имеет подкатегорий.
    *   Дано:
    *       -   parent id = 10
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/parent/{id}"
    *   Результат:
    *       -   статус ответа = NOT_FOUND
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByParentIdTest_givenJSONAcceptAndJSONContentTypeAndNotCorrectId_whenGet_thenNotFound()
            throws Exception {
        int parentId = 10;
        given()
                .log().all()
                .pathParam("id", parentId)
                .header("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH + PARENT + ID)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    /*Сценарий: получение списка подкатегорий товаров для категории с заданным id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   parent id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type = "application/json;charset=UTF-8"
    *       -   url = "/rest/category/parent/{id}"
    *   Результат:
    *       -   статус ответа = NOT_ACCEPTABLE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByParentIdTest_givenTextPlainAcceptAndJSONContentType_whenGet_thenNotAcceptable()
            throws Exception {
        int parentId = 1;
        given()
                .log().all()
                .pathParam("id", parentId)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
        .when()
                .get(PATH + PARENT + ID)
        .then()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /*Сценарий: получение списка подкатегорий товаров для категории с заданным id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   parent id = 1
    *       -   Accept = "application/json;charset=UTF-8"
    *       -   Content-Type - не определен
    *       -   url = "/rest/category/parent/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByParentIdTest_givenJSONAcceptAndNoJSONContentType_whenGet_thenUnsupported()
            throws Exception {
        int parentId = 1;
        given()
                .log().all()
                .pathParam("id", parentId)
                .header("Accept", MediaType.APPLICATION_XML_VALUE)
        .when()
                .get(PATH + PARENT + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /*Сценарий: получение списка подкатегорий товаров для категории с заданным id;
    *           параметры http-запроса не соответсвуют ожидаемым.
    *   Дано:
    *       -   parent id = 1
    *       -   Accept = "text/plain"
    *       -   Content-Type - не определено
    *       -   url = "/rest/category/parent/{id}"
    *   Результат:
    *       -   статус ответа = UNSUPPORTED_MEDIA_TYPE
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByParentIdTest_givenTextPlainAcceptAndNoJSONContentType_whenGet_thenUnsupported()
            throws Exception {
        int level = 1;
        given()
                .log().all()
                .pathParam("id", level)
                .header("Accept", MediaType.TEXT_PLAIN_VALUE)
        .when()
                .get(PATH + PARENT + ID)
        .then()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }
}
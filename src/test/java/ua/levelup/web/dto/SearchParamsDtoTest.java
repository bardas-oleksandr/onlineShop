package ua.levelup.web.dto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.validator.SearchParamsValidatorService;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Класс SearchParamsDtoTest содержит интеграционные тесты
 * для проверки корректности валидации объектов SearchParamsCreateDto
 * <p>
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class SearchParamsDtoTest {

    @Autowired
    private SearchParamsValidatorService searchParamsValidatorService;

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           все поля корректны.
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_SearchParamsValidated() {
        //GIVEN
        SearchParamsDto dto = new SearchParamsDto(1, 2,
                3, true, 4.0f, 5.0f, 1);
        //WHEN
        Set<ConstraintViolation<SearchParamsDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле int categoryId < 0
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenCategoryIdIsNegative_thenNotValidated() {
        //GIVEN
        SearchParamsDto dto = new SearchParamsDto(-1, 1,
                2, true, 3.0f, 4.0f, 1);
        //WHEN
        Set<ConstraintViolation<SearchParamsDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("negative_search_category_id", violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле int subcategoryId < 0
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenSubcategoryIdIsNegative_thenNotValidated() {
        //GIVEN
        SearchParamsDto dto = new SearchParamsDto(1, -1,
                2, true, 3.0f, 4.0f, 1);
        //WHEN
        Set<ConstraintViolation<SearchParamsDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("negative_search_category_id", violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле int manufacturerId < 0
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenManufacturerIdIsNegative_thenNotValidated() {
        //GIVEN
        SearchParamsDto dto = new SearchParamsDto(1, 2,
                -1, true, 3.0f, 4.0f, 1);
        //WHEN
        Set<ConstraintViolation<SearchParamsDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("negative_search_manufacturer_id", violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле float minPrice < 0.0
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenMinPriceIsNegative_thenNotValidated() {
        //GIVEN
        SearchParamsDto dto = new SearchParamsDto(0, 1,
                2, true, -0.1f, 4.0f, 1);
        //WHEN
        Set<ConstraintViolation<SearchParamsDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_price", violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле float minPrice больше чем поле float maxPrice
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenMinPriceBiggerThenMaxPrice_thenNotValidated() {
        //GIVEN
        SearchParamsDto dto = new SearchParamsDto(0, 1,
                2, true, 5.0f, 4.0f, 1);
        //WHEN
        Set<ConstraintViolation<SearchParamsDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("min_price_bigger_then_max_price", violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле OrderMethod < 0
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenOrderMethodIsNegative_thenNotValidated() {
        //GIVEN
        SearchParamsDto dto = new SearchParamsDto(0, 1,
                2, true, 3.0f, 4.0f, -1);
        //WHEN
        Set<ConstraintViolation<SearchParamsDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_order_method", violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле OrderMethod > 2
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenOrderMethodBiggerThenTwo_thenNotValidated() {
        //GIVEN
        SearchParamsDto dto = new SearchParamsDto(0, 1,
                2, true, 3.0f, 4.0f, 3);
        //WHEN
        Set<ConstraintViolation<SearchParamsDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_order_method", violations.stream()
                .findFirst().get().getMessage());
    }
}
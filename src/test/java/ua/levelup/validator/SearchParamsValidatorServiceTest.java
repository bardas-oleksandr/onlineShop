package ua.levelup.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.config.TestContextConfig;
import ua.levelup.dao.support.OrderMethod;
import ua.levelup.model.Product;
import ua.levelup.web.dto.create.SearchParamsCreateDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Класс SearchParamsValidatorServiceTest содержит интеграционные тесты
 * для проверки корректности валидации объектов SearchParamsCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class SearchParamsValidatorServiceTest {

    @Autowired
    private SearchParamsValidatorService searchParamsValidatorService;

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           все поля корректны.
    * Дано:
    *   - SearchParamsCreateDto dto
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_SearchParamsValidated() {
        //GIVEN
        SearchParamsCreateDto dto = new SearchParamsCreateDto(new Product(),1,
                2,3.0f,4.0f, OrderMethod.CHEAP_FIRST);
        //WHEN
        Set<ConstraintViolation<SearchParamsCreateDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле Product product == null
    * Дано:
    *   - SearchParamsCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenProductEgualsNull_thenNotValidated() {
        //GIVEN
        SearchParamsCreateDto dto = new SearchParamsCreateDto(null,1,
                2,3.0f,4.0f, OrderMethod.CHEAP_FIRST);
        //WHEN
        Set<ConstraintViolation<SearchParamsCreateDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_search_product_field",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле int categoryId < 0
    * Дано:
    *   - SearchParamsCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenCategoryIdIsNegative_thenNotValidated() {
        //GIVEN
        SearchParamsCreateDto dto = new SearchParamsCreateDto(new Product(),-1,
                2,3.0f,4.0f, OrderMethod.CHEAP_FIRST);
        //WHEN
        Set<ConstraintViolation<SearchParamsCreateDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("negative_search_category_id",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле int subcategoryId < 0
    * Дано:
    *   - SearchParamsCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenSubcategoryIdIsNegative_thenNotValidated() {
        //GIVEN
        SearchParamsCreateDto dto = new SearchParamsCreateDto(new Product(),1,
                -1,3.0f,4.0f, OrderMethod.CHEAP_FIRST);
        //WHEN
        Set<ConstraintViolation<SearchParamsCreateDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("negative_search_category_id",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле float minPrice < 0.0
    * Дано:
    *   - SearchParamsCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenMinPriceIsNegative_thenNotValidated() {
        //GIVEN
        SearchParamsCreateDto dto = new SearchParamsCreateDto(new Product(),1,
                2,-0.1f,4.0f, OrderMethod.CHEAP_FIRST);
        //WHEN
        Set<ConstraintViolation<SearchParamsCreateDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_price",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле float minPrice больше чем поле float maxPrice
    * Дано:
    *   - SearchParamsCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenMinPriceBiggerThenMaxPrice_thenNotValidated() {
        //GIVEN
        SearchParamsCreateDto dto = new SearchParamsCreateDto(new Product(),1,
                2,5.0f,4.0f, OrderMethod.CHEAP_FIRST);
        //WHEN
        Set<ConstraintViolation<SearchParamsCreateDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("max_price_bigger_then_min_price",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта SearchParamsCreateDto;
    *           поле OrderMethod == null
    * Дано:
    *   - SearchParamsCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenOrderMethodEqualsNull_thenNotValidated() {
        //GIVEN
        SearchParamsCreateDto dto = new SearchParamsCreateDto(new Product(),1,
                2,3.0f,4.0f, null);
        //WHEN
        Set<ConstraintViolation<SearchParamsCreateDto>> violations = searchParamsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_order_method",violations.stream()
                .findFirst().get().getMessage());
    }
}
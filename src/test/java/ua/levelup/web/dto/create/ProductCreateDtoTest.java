package ua.levelup.web.dto.create;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.validator.ProductValidatorService;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Класс ProductCreateDtoTest содержит интеграционные тесты
 * для проверки корректности валидации объектов ProductCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ProductCreateDtoTest {

    @Autowired
    private ProductValidatorService productValidatorService;

    /*Сценарий: валидация объекта ProductCreateDto;
    *           все поля корректны.
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_ProductValidated() {
        //GIVEN
        ProductCreateDto dto = new ProductCreateDto("product",1.0f,true,
                "description",2,3);
        //WHEN
        Set<ConstraintViolation<ProductCreateDto>> violations = productValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта ProductCreateDto;
    *           поле String name == null.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameEqualsNull_thenNotValidated() {
        //GIVEN
        ProductCreateDto dto = new ProductCreateDto(null,1.0f,true,
                "description",2,3);
        //WHEN
        Set<ConstraintViolation<ProductCreateDto>> violations = productValidatorService.
                validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_product_name",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта ProductCreateDto;
    *           поле String name == "".
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameIsEmpty_thenNotValidated() {
        //GIVEN
        ProductCreateDto dto = new ProductCreateDto("",1.0f,true,
                "description",2,3);
        //WHEN
        Set<ConstraintViolation<ProductCreateDto>> violations = productValidatorService.
                validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_product_name",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта ProductCreateDto;
    *           поле String name имеет длину более 50 символов
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameIsTooLong_thenNotValidated() {
        //GIVEN
        ProductCreateDto dto = new ProductCreateDto("0123456789012345678901234567" +
                "89012345678901234567890",1.0f,true,
                "description",2,3);
        //WHEN
        Set<ConstraintViolation<ProductCreateDto>> violations = productValidatorService.
                validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_product_name_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта ProductCreateDto;
    *           поле float price < 0.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPriceIsNegative_thenNotValidated() {
        //GIVEN
        ProductCreateDto dto = new ProductCreateDto("product",-0.1f,true,
                "description",2,3);
        //WHEN
        Set<ConstraintViolation<ProductCreateDto>> violations = productValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_price",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта ProductCreateDto;
    *           поле String description имеет длину более 100 символов
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenDescriptionIsTooLong_thenNotValidated() {
        //GIVEN
        ProductCreateDto dto = new ProductCreateDto("product",1.0f,true,
                "01234567890123456789012345678901234567890123456789012345678901234" +
                        "567890123456789012345678901234567890",2,3);
        //WHEN
        Set<ConstraintViolation<ProductCreateDto>> violations = productValidatorService.
                validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_product_description_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта ProductCreateDto;
    *           поле int categoryId < 1
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenCategoryIdSmallerThenOne_thenNotValidated() {
        //GIVEN
        ProductCreateDto dto = new ProductCreateDto("product",1.0f,true,
                "description",0,3);
        //WHEN
        Set<ConstraintViolation<ProductCreateDto>> violations = productValidatorService.
                validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_category_id",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта ProductCreateDto;
    *           поле int manufacturerId < 1
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenManufacturerIdSmallerThenOne_thenNotValidated() {
        //GIVEN
        ProductCreateDto dto = new ProductCreateDto("product",1.0f,true,
                "description",2,0);
        //WHEN
        Set<ConstraintViolation<ProductCreateDto>> violations = productValidatorService.
                validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_manufacturer_id",violations.stream()
                .findFirst().get().getMessage());
    }
}
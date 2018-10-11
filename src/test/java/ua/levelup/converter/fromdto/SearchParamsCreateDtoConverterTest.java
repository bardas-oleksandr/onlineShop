package ua.levelup.converter.fromdto;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.model.SearchParams;
import ua.levelup.web.dto.create.SearchParamsCreateDto;

/*Класс SearchParamsCreateDtoConverterTest содержит тесты для проверки
* конвертации объектов класса SearchParamsCreateDto в объекты класса SearchParams
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class SearchParamsCreateDtoConverterTest {

    @Autowired
    private SearchParamsCreateDtoConverter searchParamsCreateDtoConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса SearchParamsCreateDto в объект класса SearchParams;
    *           ID подкатегории товаров больше нуля.
    * Результат: преобразование выполнено успешно.
    *            полю int categoryId объекта SearchParams
    *            передано значение subcategoryId объекта SearchParamsCreateDto
    * */
    @Test
    public void convertTest_whenSubcategoryIdNotEqualsZero_thenOk() throws Exception {
        //GIVEN
        SearchParamsCreateDto dto = new SearchParamsCreateDto(1,2,
                3,true,4.0f,5.0f, 0);
        SearchParams expected = new SearchParams(dto.getSubcategoryId(),dto.getManufacturerId(),
                dto.isAvailableOnly(),dto.getMinPrice(),dto.getMaxPrice(),
                SearchParams.OrderMethod.get(dto.getOrderMethodIndex()));
        //WHEN
        SearchParams searchParams = searchParamsCreateDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(searchParams);
        Assert.assertEquals(expected, searchParams);
    }

    /*Сценарий: преобразование объекта класса SearchParamsCreateDto в объект класса SearchParams;
    *           ID подкатегории товаров равно нулю.
    *           ID категории товаров больше нуля.
    * Результат: преобразование выполнено успешно.
    *            полю int categoryId объекта SearchParams
    *            передано значение categoryId объекта SearchParamsCreateDto
    * */
    @Test
    public void convertTest_whenSubcategoryIdEqualsZero_thenOk() throws Exception {
        //GIVEN
        SearchParamsCreateDto dto = new SearchParamsCreateDto(1,0,
                3,true,4.0f,5.0f, 0);
        SearchParams expected = new SearchParams(dto.getCategoryId(),dto.getManufacturerId(),
                dto.isAvailableOnly(),dto.getMinPrice(),dto.getMaxPrice(),
                SearchParams.OrderMethod.get(dto.getOrderMethodIndex()));
        //WHEN
        SearchParams searchParams = searchParamsCreateDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(searchParams);
        Assert.assertEquals(expected, searchParams);
    }

    /*Сценарий: преобразование объекта класса SearchParamsCreateDto в объект класса SearchParams;
    *           объект SearchParamsCreateDto searchParamsCreateDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenSearchParamsCreateDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("searchParamsCreateDto is marked @NonNull but is null");
        //THEN
        searchParamsCreateDtoConverter.convert(null);
    }
}
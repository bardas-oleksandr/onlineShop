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
import ua.levelup.converter.fromdto.SearchParamsDtoConverter;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.model.SearchParams;
import ua.levelup.web.dto.SearchParamsDto;

/*Класс SearchParamsCreateDtoConverterTest содержит тесты для проверки
* конвертации объектов класса SearchParamsCreateDto в объекты класса SearchParams
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class SearchParamsDtoConverterTest {

    @Autowired
    private SearchParamsDtoConverter searchParamsDtoConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса SearchParamsDto в объект класса SearchParams;
    *           ID подкатегории товаров больше нуля.
    * Результат: преобразование выполнено успешно.
    *            полю int categoryId объекта SearchParams
    *            передано значение subcategoryId объекта SearchParamsDto
    * */
    @Test
    public void convertTest_whenSubcategoryIdNotEqualsZero_thenOk() throws Exception {
        //GIVEN
        SearchParamsDto dto = new SearchParamsDto(1,2,
                3,true,4.0f,5.0f, 0);
        SearchParams expected = new SearchParams(dto.getSubcategoryId(),dto.getManufacturerId(),
                dto.isAvailableOnly(),dto.getMinPrice(),dto.getMaxPrice(),
                SearchParams.OrderMethod.get(dto.getOrderMethodIndex()));
        //WHEN
        SearchParams searchParams = searchParamsDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(searchParams);
        Assert.assertEquals(expected, searchParams);
    }

    /*Сценарий: преобразование объекта класса SearchParamsDto в объект класса SearchParams;
    *           ID подкатегории товаров равно нулю.
    *           ID категории товаров больше нуля.
    * Результат: преобразование выполнено успешно.
    *            полю int categoryId объекта SearchParams
    *            передано значение categoryId объекта SearchParamsDto
    * */
    @Test
    public void convertTest_whenSubcategoryIdEqualsZero_thenOk() throws Exception {
        //GIVEN
        SearchParamsDto dto = new SearchParamsDto(1,0,
                3,true,4.0f,5.0f, 0);
        SearchParams expected = new SearchParams(dto.getCategoryId(),dto.getManufacturerId(),
                dto.isAvailableOnly(),dto.getMinPrice(),dto.getMaxPrice(),
                SearchParams.OrderMethod.get(dto.getOrderMethodIndex()));
        //WHEN
        SearchParams searchParams = searchParamsDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(searchParams);
        Assert.assertEquals(expected, searchParams);
    }

    /*Сценарий: преобразование объекта класса SearchParamsDto в объект класса SearchParams;
    *           объект SearchParamsDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenSearchParamsDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("searchParamsDto is marked @NonNull but is null");
        //THEN
        searchParamsDtoConverter.convert(null);
    }
}
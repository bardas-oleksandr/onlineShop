package ua.levelup.converter.todto;

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
import ua.levelup.model.Manufacturer;
import ua.levelup.web.dto.view.ManufacturerViewDto;

/*Класс ManufacturerConverterTest содержит тесты для проверки
* конвертации объектов класса Manufacturer в объекты класса ManufacturerViewDto
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ManufacturerConverterTest {

    @Autowired
    private ManufacturerConverter manufacturerConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса Manufacturer в объект класса ManufacturerViewDto;
    *           объект Manufacturer manufacturer не равен null.
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenManufacturerNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        Manufacturer manufacturer = new Manufacturer("manufacturer");
        manufacturer.setId(1);
        ManufacturerViewDto expected = new ManufacturerViewDto();
        expected.setName(manufacturer.getName());
        expected.setId(manufacturer.getId());
        //WHEN
        ManufacturerViewDto manufacturerDto = manufacturerConverter.convert(manufacturer);
        //THEN
        Assert.assertNotNull(manufacturerDto);
        Assert.assertEquals(expected, manufacturerDto);
    }

    /*Сценарий: преобразование объекта класса Manufacturer в объект класса ManufacturerViewDto;
    *           объект Manufacturer manufacturer не равен null.
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenManufacturerNameEqualsNull_thenOk() throws Exception {
        //GIVEN
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1);
        ManufacturerViewDto expected = new ManufacturerViewDto();
        expected.setId(manufacturer.getId());
        //WHEN
        ManufacturerViewDto manufacturerDto = manufacturerConverter.convert(manufacturer);
        //THEN
        Assert.assertNotNull(manufacturerDto);
        Assert.assertEquals(expected, manufacturerDto);
    }

    /*Сценарий: преобразование объекта класса Manufacturer в объект класса ManufacturerDto;
    *           объект Manufacturer manufacturer равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenManufacturerEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("manufacturer is marked @NonNull but is null");
        //THEN
        manufacturerConverter.convert(null);
    }
}
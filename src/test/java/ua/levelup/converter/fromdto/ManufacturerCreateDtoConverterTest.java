package ua.levelup.converter.fromdto;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.config.TestContextConfig;
import ua.levelup.model.Manufacturer;
import ua.levelup.web.dto.create.ManufacturerCreateDto;

/*Класс ManufacturerCreateDtoConverterTest содержит тесты для проверки
* конвертации объектов класса ManufacturerCreateDto в объекты класса Manufacturer
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class ManufacturerCreateDtoConverterTest {

    @Autowired
    private ManufacturerCreateDtoConverter manufacturerCreateDtoConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса ManufacturerCreateDto в объект класса Manufacturer;
    *           объект ManufacturerCreateDto manufacturerCreateDto не равен null.
    * Дано:
    *   - ManufacturerCreateDto manufacturerCreateDto
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenManufacturerCreateDtoNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        ManufacturerCreateDto dto = new ManufacturerCreateDto("manufacturer");
        Manufacturer expected = new Manufacturer(dto.getName());
        //WHEN
        Manufacturer manufacturer = manufacturerCreateDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(manufacturer);
        Assert.assertEquals(expected, manufacturer);
    }

    /*Сценарий: преобразование объекта класса ManufacturerCreateDto в объект класса Manufacturer;
    *           объект ManufacturerCreateDto manufacturerCreateDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenManufacturerCreateDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("manufacturerCreateDto is marked @NonNull but is null");
        //THEN
        manufacturerCreateDtoConverter.convert(null);
    }
}
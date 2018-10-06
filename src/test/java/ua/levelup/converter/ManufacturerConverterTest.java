package ua.levelup.converter;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.levelup.converter.toViewDto.ManufacturerConverter;
import ua.levelup.model.Manufacturer;
import ua.levelup.web.dto.view.ManufacturerViewDto;

/*Класс ManufacturerConverterTest содержит тесты для проверки
* конвертации объектов класса Manufacturer в объекты класса ManufacturerDto
*
* Автор: Бардась А.А.
* */
public class ManufacturerConverterTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса Manufacturer в объект класса ManufacturerDto.
    *           Аргумент метода не равен null
    * Дано:
    *   - Manufacturer manufacturer
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asManufacturerDtoTest() throws Exception {
        //GIVEN
        Manufacturer manufacturer = new Manufacturer("manufaturer");
        manufacturer.setId(1);
        ManufacturerViewDto expected = new ManufacturerViewDto();
        expected.setName("manufaturer");
        expected.setId(1);
        //WHEN
        ManufacturerViewDto manufacturerDto = ManufacturerConverter.asManufacturerDto(manufacturer);
        //THEN
        Assert.assertNotNull(manufacturerDto);
        Assert.assertEquals(expected, manufacturerDto);
    }

    /*Сценарий: преобразование объекта класса Manufacturer в объект класса ManufacturerDto.
    *           Аргумент метода равен null
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asManufacturerDtoTest_whenManufacturerNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("manufacturer is marked @NonNull but is null");
        //THEN
        ManufacturerConverter.asManufacturerDto(null);
    }
}
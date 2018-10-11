package ua.levelup.dao.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.dao.ManufacturerDao;
import ua.levelup.model.Manufacturer;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import ua.levelup.testconfig.TestContextConfig;

import java.util.Properties;

/*Класс ManufacturerDaoImplTest содержит интеграционные тесты для проверки
* корректности работы слоя доступа к данным, относящимся к сущности
* "Производитель товаров"
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ManufacturerDaoImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private ManufacturerDao manufacturerDao;

    @Autowired
    private Properties messagesProperties;

    private Manufacturer manufacturer = new Manufacturer("manufacturer");



}
package ua.levelup.converter.todto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.model.Order;
import ua.levelup.model.OrderPosition;
import ua.levelup.model.User;
import ua.levelup.web.dto.view.OrderPositionViewDto;
import ua.levelup.web.dto.view.OrderViewDto;
import ua.levelup.web.dto.view.UserViewDto;

import java.sql.Timestamp;
import java.util.Collections;

/*Класс OrderConverterTest содержит тесты для проверки
* конвертации объектов класса Order в объекты класса OrderViewDto
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class OrderConverterTest {

    @Mock
    UserConverter userConverter;

    @Mock
    OrderPositionConverter orderPositionConverter;

    @Autowired
    @InjectMocks
    private OrderConverter orderConverter;

    private Order order;
    private OrderViewDto expected;
    private User user;
    private OrderPosition orderPosition;
    private OrderPositionViewDto orderPositionViewDto;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        user = new User("name", "password",
                "email@gmail.com", User.UserState.ACTIVE);
        user.setId(1);
        order = new Order(user, "address", new Timestamp(1), false
                , Order.OrderState.REGISTERED, Order.PaymentConditions.CARD);
        order.setId(1);
        orderPosition = new OrderPosition(order.getId(), 2, 3, 4.0f);
        orderPosition.setProductName("product");

        UserViewDto userViewDto = new UserViewDto();
        userViewDto.setId(user.getId());
        userViewDto.setEmail(user.getEmail());
        userViewDto.setUserName(user.getUserName());
        userViewDto.setState(user.getUserState().ordinal());
        Mockito.when(userConverter.convert(user)).thenReturn(userViewDto);

        orderPositionViewDto = new OrderPositionViewDto();
        orderPositionViewDto.setOrderId(orderPosition.getOrderId());
        orderPositionViewDto.setProductId(orderPosition.getOrderId());
        orderPositionViewDto.setProductName(orderPosition.getProductName());
        orderPositionViewDto.setQuantity(orderPosition.getQuantity());
        orderPositionViewDto.setUnitPrice(orderPosition.getUnitPrice());
        Mockito.when(orderPositionConverter.convert(orderPosition)).thenReturn(orderPositionViewDto);

        expected = new OrderViewDto();
        expected.setUserDto(userViewDto);
        expected.setId(order.getId());
        expected.setAddress(order.getAddress());
        expected.setPayed(order.isPayed());
        expected.setDate(order.getDate());
        expected.setOrderState(order.getOrderState().ordinal());
        expected.setConditions(order.getPaymentConditions().ordinal());
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса Order в объект класса OrderViewDto;
    *           объект Order order не равен null.
    *           список OrderPositionList объекта Order order не пустой
    * Результат: преобразование выполнено успешно
    * */
    @Test
    public void convertTest_whenOrderPositionListIsNotEmpty_thenOk() throws Exception {
        //GIVEN
        order.addOrderPosition(orderPosition);
        expected.setOrderPositionViewDtoList(Collections.singletonList(orderPositionViewDto));
        //WHEN
        OrderViewDto orderViewDto = orderConverter.convert(order);
        //THEN
        Assert.assertNotNull(orderViewDto);
        Assert.assertEquals(expected, orderViewDto);
        Mockito.verify(userConverter, Mockito.times(1))
                .convert(user);
        Mockito.verify(orderPositionConverter, Mockito.times(1))
                .convert(orderPosition);
        Mockito.verifyNoMoreInteractions(userConverter);
        Mockito.verifyNoMoreInteractions(orderPositionConverter);
    }

    /*Сценарий: преобразование объекта класса Order в объект класса OrderViewDto;
    *           объект Order order не равен null.
    *           список OrderPositionList объекта Order order пустой, не равен null
    * Результат: исключение NullPointerException
    * */
    @Test
    public void convertTest_whenOrderPositionListIsEmpty_thenException() throws Exception {
        //WHEN
        OrderViewDto orderViewDto = orderConverter.convert(order);
        //THEN
        Assert.assertNotNull(orderViewDto);
        Assert.assertEquals(expected, orderViewDto);
        Mockito.verify(userConverter, Mockito.times(1))
                .convert(user);
        Mockito.verifyNoMoreInteractions(userConverter);
        Mockito.verifyNoMoreInteractions(orderPositionConverter);
    }

    /*Сценарий: преобразование объекта класса Order в объект класса OrderViewDto;
    *           объект Order order равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenOrderEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("order is marked @NonNull but is null");
        //THEN
        orderConverter.convert(null);
    }
}
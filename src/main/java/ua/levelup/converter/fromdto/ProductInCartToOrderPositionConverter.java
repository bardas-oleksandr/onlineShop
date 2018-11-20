package ua.levelup.converter.fromdto;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.view.ProductInCartViewDto;

/**
 *
 */
//Если пометить класс каждого конвертера как @Component, тогда Spring
//сам соберет все конвертеры в Set<Converter<?,?>> и вручную собирать уже не нужно
@Component("productInCartToOrderPositionConverter")
public class ProductInCartToOrderPositionConverter
        implements Converter<ProductInCartViewDto, OrderPosition> {

    @Override
    public OrderPosition convert(@NonNull ProductInCartViewDto productInCartViewDto) {
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setProductId(productInCartViewDto.getProductViewDto().getId());
        orderPosition.setProductName(productInCartViewDto.getProductViewDto().getName());
        orderPosition.setQuantity(productInCartViewDto.getCount());
        orderPosition.setUnitPrice(productInCartViewDto.getProductViewDto().getPrice());
        return orderPosition;
    }
}

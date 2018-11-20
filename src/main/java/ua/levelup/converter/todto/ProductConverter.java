package ua.levelup.converter.todto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.Product;
import ua.levelup.web.dto.view.ProductViewDto;

/**
 *
 */
//Если пометить класс каждого конвертера как @Component, тогда Spring
//сам соберет все конвертеры в Set<Converter<?,?>> и вручную собирать уже не нужно
@Component("productConverter")
public class ProductConverter implements Converter<Product, ProductViewDto> {

    @Autowired
    CategoryConverter categoryConverter;

    @Autowired
    ManufacturerConverter manufacturerConverter;

    @Override
    public ProductViewDto convert(@NonNull Product product) {
        ProductViewDto productViewDto = new ProductViewDto();
        productViewDto.setId(product.getId());
        productViewDto.setName(product.getName());
        productViewDto.setPrice(product.getPrice());
        productViewDto.setAvailable(product.isAvailable());
        productViewDto.setDescription(product.getDescription());
        productViewDto.setCategoryViewDto(categoryConverter.convert(product.getCategory()));
        productViewDto.setManufacturerViewDto(manufacturerConverter.convert(product.getManufacturer()));
        return productViewDto;
    }
}

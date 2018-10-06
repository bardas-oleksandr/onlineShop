package ua.levelup.converter.toViewDto;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.Product;
import ua.levelup.web.dto.view.ProductViewDto;

@Component("productConverter")
@Getter
@Setter
public class ProductConverter implements Converter<Product, ProductViewDto> {

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private ManufacturerConverter manufacturerConverter;

    @NotNull
    @Override
    public ProductViewDto convert(@NonNull Product product) {
        ProductViewDto productViewDto = new ProductViewDto();
        productViewDto.setId(product.getId());
        productViewDto.setName(product.getName());
        productViewDto.setPrice(product.getPrice());
        productViewDto.setAvailable(product.isAvailable());
        productViewDto.setDescription(product.getDescription());
        productViewDto.setCategoryDto(categoryConverter.convert(product.getCategory()));
        productViewDto.setManufacturerDto(manufacturerConverter.convert(product.getManufacturer()));
        return productViewDto;
    }
}

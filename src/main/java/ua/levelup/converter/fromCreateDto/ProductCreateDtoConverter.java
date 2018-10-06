package ua.levelup.converter.fromCreateDto;

import com.sun.istack.internal.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import ua.levelup.model.Category;
import ua.levelup.model.Manufacturer;
import ua.levelup.model.Product;
import ua.levelup.web.dto.create.ProductCreateDto;

public class ProductCreateDtoConverter implements Converter<ProductCreateDto, Product> {

    @NotNull
    @Override
    public Product convert(@NonNull ProductCreateDto productCreateDto) {
        Product product = new Product();
        product.setName(productCreateDto.getName());
        product.setPrice(productCreateDto.getPrice());
        product.setAvailable(productCreateDto.isAvailable());
        product.setDescription(productCreateDto.getDescription());
        Category category = new Category();
        category.setId(productCreateDto.getCategoryId());
        product.setCategory(category);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(productCreateDto.getManufacturerId());
        product.setManufacturer(manufacturer);
        return product;
    }
}

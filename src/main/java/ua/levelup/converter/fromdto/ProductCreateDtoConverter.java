package ua.levelup.converter.fromdto;

import org.springframework.core.convert.converter.Converter;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.levelup.model.Category;
import ua.levelup.model.Manufacturer;
import ua.levelup.model.Product;
import ua.levelup.web.dto.create.ProductCreateDto;

/**
 *
 */
//Если пометить класс каждого конвертера как @Component, тогда Spring
//сам соберет все конвертеры в Set<Converter<?,?>> и вручную собирать уже не нужно
@Component("productCreateDtoConverter")
public class ProductCreateDtoConverter implements Converter<ProductCreateDto, Product> {

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
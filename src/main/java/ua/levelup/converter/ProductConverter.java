package ua.levelup.converter;

import ua.levelup.model.Product;
import ua.levelup.web.dto.ProductDto;

public enum ProductConverter {
    ;

    public static ProductDto asProductViewDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setAvailable(product.isAvailable());
        productDto.setDescription(product.getDescription());
        productDto.setCategoryDto(CategoryConverter.asCategoryDto(product.getCategory()));
        productDto.setManufacturerDto(ManufacturerConverter.asManufacturerDto(product.getManufacturer()));
        return productDto;
    }
}

package ua.levelup.converter;

import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.Product;
import ua.levelup.web.dto.ProductCreateDto;
import ua.levelup.web.dto.ProductViewDto;

public enum ProductConverter {
    ;

    public static Product asProduct(ProductCreateDto createdProduct){
        if(createdProduct.getName() == null){
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("AS_PRODUCT_CONVERTATION_ERROR"));
        }
        Product product = new Product();
        product.setName(createdProduct.getName());
        product.setPrice(createdProduct.getPrice());
        product.setAvailable(createdProduct.isAvailable());
        product.setDescription(createdProduct.getDescription());
        product.setCategoryId(createdProduct.getCategoryId());
        product.setManufacturerId(createdProduct.getManufacturerId());
        return product;
    }

    public static ProductViewDto asProductViewDto(Product product){
        if(product.getName() == null){
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("AS_PRODUCT_VIEW_DTO_CONVERTATION_ERROR"));
        }
        ProductViewDto viewDto = new ProductViewDto();
        viewDto.setId(product.getId());
        viewDto.setName(product.getName());
        viewDto.setPrice(product.getPrice());
        viewDto.setAvailable(product.isAvailable());
        viewDto.setDescription(product.getDescription());
        viewDto.setCategoryId(product.getCategoryId());
        viewDto.setCategoryName(product.getCategoryName());
        viewDto.setManufacturerId(product.getManufacturerId());
        viewDto.setManufacturerName(product.getManufacturerName());
        return viewDto;
    }
}

package ua.levelup.service;

import ua.levelup.web.dto.SearchParamsDto;
import ua.levelup.web.dto.create.ProductCreateDto;
import ua.levelup.web.dto.view.ProductViewDto;

import javax.validation.Valid;
import java.util.List;

public interface ProductService {
    List<ProductViewDto> searchProducts(@Valid SearchParamsDto searchParams);
    void deleteProduct(int productId);
    ProductViewDto updateProduct(@Valid ProductCreateDto productCreateDto, int productId);
    ProductViewDto createProduct(@Valid ProductCreateDto productCreateDto);
}

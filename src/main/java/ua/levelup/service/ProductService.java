package ua.levelup.service;

import ua.levelup.web.dto.SearchParamsDto;
import ua.levelup.web.dto.create.ProductCreateDto;
import ua.levelup.web.dto.view.ProductViewDto;

import java.util.List;

public interface ProductService {
    ProductViewDto createProduct(ProductCreateDto productCreateDto);
    ProductViewDto updateProduct(ProductCreateDto productCreateDto, int productId);
    void deleteProduct(int productId);
    List<ProductViewDto> searchProducts(SearchParamsDto searchParams);
}
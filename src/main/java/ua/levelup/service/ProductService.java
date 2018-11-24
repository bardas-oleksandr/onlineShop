package ua.levelup.service;

import ua.levelup.web.dto.SearchParamsDto;
import ua.levelup.web.dto.create.ProductCreateDto;
import ua.levelup.web.dto.view.ProductViewDto;

import java.util.List;

public interface ProductService {
    ProductViewDto create(ProductCreateDto productCreateDto);

    ProductViewDto update(ProductCreateDto productCreateDto, int productId);

    void delete(int productId);

    ProductViewDto get(int productId);

    List<ProductViewDto> searchProducts(SearchParamsDto searchParams);
}
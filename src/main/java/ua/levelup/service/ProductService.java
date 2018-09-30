package ua.levelup.service;

import ua.levelup.web.servlet.support.SearchParams;
import ua.levelup.web.dto.ProductCreateDto;
import ua.levelup.web.dto.ProductViewDto;

import java.util.List;

public interface ProductService {
    List<ProductViewDto> searchProducts(SearchParams searchParams);
    void deleteProduct(int productId);
    void updateProduct(ProductCreateDto productCreateDto, int productId);
    void createNewProduct(ProductCreateDto productCreateDto);
}

package ua.levelup.service;

import ua.levelup.web.servlet.support.SearchParams;
import ua.levelup.web.dto.create.ProductCreateDto;
import ua.levelup.web.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> searchProducts(SearchParams searchParams);
    void deleteProduct(int productId);
    void updateProduct(ProductCreateDto productCreateDto, int productId);
    void createNewProduct(ProductCreateDto productCreateDto);
}

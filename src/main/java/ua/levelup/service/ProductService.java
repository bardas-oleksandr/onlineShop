package ua.levelup.service;

import ua.levelup.model.Product;
import ua.levelup.web.dto.create.SearchParamsCreateDto;
import ua.levelup.web.dto.view.ProductViewDto;

import java.util.List;

public interface ProductService {
    List<ProductViewDto> searchProducts(SearchParamsCreateDto searchParams);
    void deleteProduct(int productId);
    void updateProduct(Product product, int productId);
    void createNewProduct(Product product);
}

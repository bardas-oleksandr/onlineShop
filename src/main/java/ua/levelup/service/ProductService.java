package ua.levelup.service;

import ua.levelup.model.Product;
import ua.levelup.web.servlet.support.SearchParams;
import ua.levelup.web.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> searchProducts(SearchParams searchParams);
    void deleteProduct(int productId);
    void updateProduct(Product product, int productId);
    void createNewProduct(Product product);
}

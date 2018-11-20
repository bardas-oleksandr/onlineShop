package ua.levelup.service;

import ua.levelup.model.Product;
import ua.levelup.web.dto.SearchParamsDto;
import ua.levelup.web.dto.view.ProductViewDto;

import javax.validation.Valid;
import java.util.List;

public interface ProductService {
    List<ProductViewDto> searchProducts(@Valid SearchParamsDto searchParams);
    void deleteProduct(int productId);
    void updateProduct(Product product, int productId);
    void createNewProduct(Product product);
}

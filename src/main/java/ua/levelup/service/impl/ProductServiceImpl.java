package ua.levelup.service.impl;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.levelup.dao.ProductDao;
import ua.levelup.model.Product;
import ua.levelup.model.SearchParams;
import ua.levelup.service.ProductService;
import ua.levelup.web.dto.SearchParamsDto;
import ua.levelup.web.dto.create.ProductCreateDto;
import ua.levelup.web.dto.view.ProductViewDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Setter
@Service("productService")
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ProductDao productDao;

    @Autowired
    ConversionService conversionService;

    @Override
    public List<ProductViewDto> searchProducts(@Valid SearchParamsDto createDto) {
        SearchParams searchParams = conversionService.convert(createDto, SearchParams.class);
        List<Product> products = productDao.getFilteredProducts(searchParams);
        List<ProductViewDto> viewDtos = new ArrayList<>();
        products.stream().forEach((item) -> viewDtos
                .add(conversionService.convert(item, ProductViewDto.class)));
        return viewDtos;
    }

    @Override
    public void deleteProduct(int productId) {
        productDao.delete(productId);
    }

    @Override
    public ProductViewDto updateProduct(@Valid ProductCreateDto productCreateDto, int productId) {
        Product product = conversionService.convert(productCreateDto, Product.class);
        product.setId(productId);
        productDao.update(product);
        return conversionService.convert(product, ProductViewDto.class);
    }

    @Override
    public ProductViewDto createProduct(@Valid ProductCreateDto productCreateDto) {
        Product product = conversionService.convert(productCreateDto, Product.class);
        productDao.add(product);
        return conversionService.convert(product, ProductViewDto.class);
    }
}
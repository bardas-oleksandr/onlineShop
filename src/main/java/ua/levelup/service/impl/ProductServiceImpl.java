package ua.levelup.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.levelup.converter.toViewDto.ProductConverter;
import ua.levelup.dao.ProductDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.Product;
import ua.levelup.service.ProductService;
import ua.levelup.web.servlet.support.SearchParams;
import ua.levelup.validator.ProductValidator;
import ua.levelup.web.dto.view.ProductViewDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
@Getter
@Setter
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductViewDto> searchProducts(SearchParams searchParams) {
        List<ua.levelup.model.Product> productList = productDao.getFilteredProducts(searchParams.getProduct(),
                searchParams.getMinPrice(), searchParams.getMaxPrice(), searchParams.getOrderMethod());
        List<ProductViewDto> productViewDtoList = new ArrayList<>();
        for (ua.levelup.model.Product item : productList) {
            productViewDtoList.add(ProductConverter.asProductViewDto(item));
        }
        return productViewDtoList;
    }

    @Override
    public void deleteProduct(int productId) {
        int count = productDao.delete(productId);
        if (count == 0) {
            logger.error(MessageHolder.getMessageProperties().getProperty("PRODUCT_DOES_NOT_EXIST"));
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("PRODUCT_DOES_NOT_EXIST"));
        } else if(count > 1) {
            logger.error(MessageHolder.getMessageProperties().getProperty("UNEXPECTED_RESULT"));
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("UNEXPECTED_RESULT")); //Actually, we will never have this exception, because product_id is unique
        }
    }

    @Override
    public void updateProduct(Product product, int productId) {
        ProductValidator.validateNewProduct(product);
        product.setId(productId);
        int count = productDao.update(product);
        if (count == 0) {
            logger.error(MessageHolder.getMessageProperties().getProperty("PRODUCT_DOES_NOT_EXIST"));
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("PRODUCT_DOES_NOT_EXIST"));
        } else if(count > 1)  {
            logger.error(MessageHolder.getMessageProperties().getProperty("UNEXPECTED_RESULT"));
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("UNEXPECTED_RESULT")); //Actually, we will never have this exception, because product_id is unique
        }
    }

    @Override
    public void createNewProduct(Product product) {
        ProductValidator.validateNewProduct(product);
        productDao.add(product);
    }
}

package ua.levelup.service.impl;

import ua.levelup.converter.ProductConverter;
import ua.levelup.dao.ProductDao;
import ua.levelup.dao.support.DaoHolder;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.service.ProductService;
import ua.levelup.web.servlet.support.SearchParams;
import ua.levelup.validator.ProductValidator;
import ua.levelup.web.dto.create.ProductCreateDto;
import ua.levelup.web.dto.ProductDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LogManager.getLogger();

    private ProductDao productDao = (ProductDao) DaoHolder
            .getDaoObject(DaoHolder.PRODUCT_DAO);

    @Override
    public List<ProductDto> searchProducts(SearchParams searchParams) {
        List<ua.levelup.model.Product> productList = productDao.getFilteredProducts(searchParams.getProduct(),
                searchParams.getMinPrice(), searchParams.getMaxPrice(), searchParams.getOrderMethod());
        List<ProductDto> productViewDtoList = new ArrayList<>();
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
    public void updateProduct(ProductCreateDto productCreateDto, int productId) {
        ProductValidator.validateNewProduct(productCreateDto);
        ua.levelup.model.Product product = ProductConverter.asProduct(productCreateDto);
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
    public void createNewProduct(ProductCreateDto productCreateDto) {
        ProductValidator.validateNewProduct(productCreateDto);
        ua.levelup.model.Product product = ProductConverter.asProduct(productCreateDto);
        productDao.add(product);
    }
}

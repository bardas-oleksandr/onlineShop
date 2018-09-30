package ua.levelup.web.servlet.support;

import ua.levelup.dao.support.OrderMethod;
import ua.levelup.model.Product;

public class SearchParams {
    private Product product;
    private int categoryId;
    private int subcategoryId;
    private float minPrice;
    private float maxPrice;
    private OrderMethod orderMethod;

    public SearchParams() { }

    public SearchParams(Product product, int categoryId, int subcategoryId, float minPrice, float maxPrice, OrderMethod orderMethod) {
        this.product = product;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.orderMethod = orderMethod;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public OrderMethod getOrderMethod() {
        return orderMethod;
    }

    public void setOrderMethod(OrderMethod orderMethod) {
        this.orderMethod = orderMethod;
    }
}

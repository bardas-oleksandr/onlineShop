package ua.levelup.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Cart implements Serializable {

    private static final long serialVersionUID = -1701271203258179128L;

    private List<ProductInCart> productInCartList;

    public Cart(){
        this.productInCartList = new ArrayList<>();
    }

    public void putProduct(Product product, int productCount) {
        Optional<ProductInCart> productInCartOptional = productInCartList.stream().filter((item) -> item
                .getProduct().getId() == product.getId()).findFirst();
        if (productInCartOptional.isPresent()){
            ProductInCart productInCart = productInCartOptional.get();
            productInCart.setCount(productInCart.getCount() + productCount);
        }else{
            productInCartList.add(new ProductInCart(product, productCount));
        }
    }
}
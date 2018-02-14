package main;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Interface defining all relevant methods to be implemented for ShoppingCart implementation
 */
public interface IShoppingCart {

    void createTours();

    Integer totalPrice(ShoppingCartImpl shoppingCart);

    Integer applyDiscount(ShoppingCartImpl shoppingCart, AtomicInteger countOH, AtomicInteger countBC, AtomicInteger countSK);
}

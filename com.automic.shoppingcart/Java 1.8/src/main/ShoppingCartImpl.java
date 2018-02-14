package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementation of ShoppingCart with total price and discounted price calculation methods
 */
public class ShoppingCartImpl implements IShoppingCart {

    // integers to store price of each tour
    int tourOHPrice;
    int tourBCPrice;
    int tourSKPrice;

    // HashMap to store the available tours
    HashMap<String, Integer> tours = new HashMap<>();

    // ArrayList to store the tour packages available on discounts
    ArrayList<String> tourPackages = new ArrayList<>();

    // Objecgt of ShoppingCart implementation class
    private ShoppingCartImpl shoppingCart;

    // getter and setter for OH(Opera House) tour price
    public int getTourOHPrice() {
        return tourOHPrice;
    }

    public void setTourOHPrice(int tourOHPrice) {
        this.tourOHPrice = tourOHPrice;
    }

    public int getTourBCPrice() {
        return tourBCPrice;
    }

    // getter and setter for BC(Bridge Climb) tour price
    public void setTourBCPrice(int tourBCPrice) {
        this.tourBCPrice = tourBCPrice;
    }

    // getter and setter for SK(Sydney Sky Tower) tour price
    public int getTourSKPrice() {
        return tourSKPrice;
    }

    public void setTourSKPrice(int tourSKPrice) {
        this.tourSKPrice = tourSKPrice;
    }

    /**
     * method to create generic tours
     */
    @Override
    public void createTours() {

        this.setTourOHPrice(300);
        this.setTourBCPrice(110);
        this.setTourSKPrice(30);

        tours.put("OH", this.getTourOHPrice());
        tours.put("BC", this.getTourBCPrice());
        tours.put("SK", this.getTourSKPrice());
    }

    /**
     * method to calculate total price based on tour packages added in the
     * shopping cart
     * @param shoppingCart
     * @return total price
     */
    @Override
    public Integer totalPrice(ShoppingCartImpl shoppingCart) {

        this.shoppingCart = shoppingCart;

        AtomicReference<Integer> totalPrice = new AtomicReference<>(0);

        AtomicInteger countOH = new AtomicInteger();
        AtomicInteger countBC = new AtomicInteger();
        AtomicInteger countSK = new AtomicInteger();

        shoppingCart.tourPackages.forEach((String key) -> {

            if (key.contains("OH")) {
                countOH.getAndIncrement();
            }
            if(key.contains("BC")) {
                countBC.getAndIncrement();
            }
            if(key.contains("SK")) {
                countSK.getAndIncrement();
            }

            int totalOHPrice = Math.multiplyExact(countOH.intValue(), shoppingCart.tours.get("OH"));
            int totalBCPrice = Math.multiplyExact(countBC.intValue(), shoppingCart.tours.get("BC"));
            int totalSKPrice = Math.multiplyExact(countSK.intValue(), shoppingCart.tours.get("SK"));

            int fullPrice = totalOHPrice + totalBCPrice + totalSKPrice;

            int discountedPrice = fullPrice - applyDiscount(shoppingCart, countOH, countBC, countSK);

            totalPrice.set(discountedPrice);
        });

        applyDiscount(this.shoppingCart, countOH, countBC, countSK);

        return Integer.valueOf(String.valueOf(totalPrice));
    }

    /**
     * method to calculate discount based on defined rules aligned to the tour packages added in the cart
     * @param shoppingCart
     * @param countOH
     * @param countBC
     * @param countSK
     * @return
     */
    @Override
    public Integer applyDiscount(ShoppingCartImpl shoppingCart, AtomicInteger countOH, AtomicInteger countBC, AtomicInteger countSK) {

        int discountOH;

        if(countOH.intValue() % 3 == 0) {

            discountOH = (countOH.intValue()/3) * 300;

            return discountOH;
        }

        if(countOH.intValue() >=1 && shoppingCart.tourPackages.contains("SK")) {

            discountOH = 30;
            return discountOH;
        }

        if(countBC.intValue() >= 4) {

            shoppingCart.setTourBCPrice(20);
            return countBC.intValue() * getTourBCPrice();
        }
        return 0;
    }

    /**
     * Entry point to the program
     * @param args
     */
    public static void main(String[] args) {

        ShoppingCartImpl shoppingCart = new ShoppingCartImpl();

        shoppingCart.createTours();

        //TODO: use these set of inputs for testing. You may change inputs for testing other scenarios

        //Example input 1
        /*shoppingCart.tourPackages.add("OH");
        shoppingCart.tourPackages.add("OH");
        shoppingCart.tourPackages.add("OH");
        shoppingCart.tourPackages.add("BC");*/

        //Example input 2
        /*shoppingCart.tourPackages.add("OH");
        shoppingCart.tourPackages.add("SK");*/

        //Example input 3
        /*shoppingCart.tourPackages.add("BC");
        shoppingCart.tourPackages.add("BC");
        shoppingCart.tourPackages.add("BC");
        shoppingCart.tourPackages.add("BC");
        shoppingCart.tourPackages.add("BC");
        shoppingCart.tourPackages.add("OH");*/

        Integer totalPrice = shoppingCart.totalPrice(shoppingCart);

        System.out.println(totalPrice);
    }
}

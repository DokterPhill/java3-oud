/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roel
 */
public class Waiter implements Runnable {

    Restaurant restaurant;

    public Waiter(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        
        String[] ordered = restaurant.getLastOrdered();
        int orderCount = restaurant.getOrderCount();
        orderCount++;
        Order order = new Order(orderCount);

            for (String ordered1 : ordered) {
                String[] lineParts = ordered1.split("\\s*,\\s*", 2);
                int mealNR = 0;
                int servings = 0;
                try {
                    mealNR = Integer.parseInt(lineParts[0].trim());
                    servings = Integer.parseInt(lineParts[1].trim());
                } catch (NumberFormatException nfe) {
                    try {
                        throw new RestaurantException(nfe);
                    } catch (RestaurantException ex) {
                        Logger.getLogger(Waiter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (!restaurant.getRecipes().containsKey(mealNR)) {
                    System.out.println("Order nr. " + orderCount
                            + ": a non existing meal (nr.=" + mealNR + ") ordered!");
                } else {
                    order.addMeal(mealNR, servings);
                    System.out.println("Order nr. " + orderCount
                            + ", ordered: menu nr. " + mealNR + " ,"
                            + servings + " servings.");
                }
            }
            restaurant.getOrderQueue().put(order);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            restaurant.setOrderCount(orderCount);
    }

}

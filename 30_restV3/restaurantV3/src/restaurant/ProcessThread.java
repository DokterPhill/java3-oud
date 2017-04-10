/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jazz
 */
public class ProcessThread implements Callable {

    Restaurant restaurant;
    CompletableFuture<ArrayList<Meal>> future = new CompletableFuture<>();
    
    public ProcessThread(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Order WaiterTask() {
        String[] ordered = restaurant.getOrders();
        int orderCount = restaurant.incrementOrderCount();
        Order order = new Order(orderCount);
        for (String orders : ordered) {
            System.out.println("orders = " + orders + " orderNr = "+orderCount);
            if (orders.equals("Restaurant Closed")) {
                restaurant.decrementOrderCount();
                return new Order(0);
            } else {
                if (orders.equals("empty1")) {
                    restaurant.decrementOrderCount();
                } else { 
                
                String[] lineParts = orders.split("\\s*,\\s*", 2);
                int mealNR = 0;
                int servings = 0;
                try {
                    mealNR = Integer.parseInt(lineParts[0].trim());
                    servings = Integer.parseInt(lineParts[1].trim());
                } catch (NumberFormatException nfe) {
                    try {
                        throw new RestaurantException(nfe);
                    } catch (RestaurantException ex) {
                        Logger.getLogger(ProcessThread.class.getName()).log(Level.SEVERE, null, ex);
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
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return order;
    }

    private ArrayList<Meal> CookTask(Order order) {
        ArrayList<Meal> meals = new ArrayList<>();
        int orderNR = order.getNumber();
        for (OrderLine ol : order) {
            int mealNR = ol.getMealNR();
            int persons = ol.getPersons();
            for (int p = 0; p < persons; p++) {
                meals.add(prepareMeal(orderNR, mealNR));
            }
        } return meals;
    }

    private Meal prepareMeal(int orderNR, int mealNR) {
        Recipe recipe = restaurant.getRecipes().get(mealNR);
        String mealName = recipe.getName();
        int procTime = recipe.getPreparationTime();
        try {
            Thread.sleep(procTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Meal(orderNR, mealNR, mealName);
    }
    
    @Override
    public ArrayList<Meal> call() throws Exception {
        Order order = this.WaiterTask();
        if (order.getNumber() == 0) {
            restaurant.stopExecutor();
            return null;
        } else {
            ArrayList<Meal> meals = this.CookTask(order);
             return meals;
        }
    }
}

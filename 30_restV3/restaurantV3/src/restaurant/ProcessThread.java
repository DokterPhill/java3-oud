/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jazz
 */
public class ProcessThread implements Runnable {

    Restaurant restaurant;

    public ProcessThread(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Order WaiterTask() {
        String ordered = restaurant.getOrders();
        int orderCount = restaurant.incrementOrderCount();
        Order order = new Order(orderCount);

            if (ordered.contentEquals("Restaurant Closed")){
                restaurant.setRun();
            } else {
            String[] lineParts = ordered.split("\\s*,\\s*", 2);
            int mealNR = 0;
            int servings = 0;
            try {
                mealNR = Integer.parseInt(lineParts[0].trim());
                servings = Integer.parseInt(lineParts[1].trim());
            } catch (NumberFormatException nfe) {
                try {
                    throw new RestaurantException(nfe);
                } catch (RestaurantException ex) {
                    //Logger.getLogger(Waiter.class.getName()).log(Level.SEVERE, null, ex);
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
        
        //restaurant.getOrderQueue().put(order);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        restaurant.setOrderCount(orderCount);
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
        }
        return meals;
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

    private void ServerTask(Meal meal) throws InterruptedException {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(meal.toString());
    }

    @Override
    public void run() {
        Order order = this.WaiterTask();
        Restaurant.printSeparator();
        ArrayList<Meal> meals = this.CookTask(order);
        for (Meal meal : meals) {
            try {
                this.ServerTask(meal);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProcessThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

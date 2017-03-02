/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

/**
 *
 * @author roel
 */
public class Cook implements Runnable {
    
    Restaurant restaurant;

    public Cook(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    
//    
//         /**
//     * Process the orders in FIFO order.
//     */
//    public void procesOrders() {
//        while (!restaurant.getOrderQueue().empty()) {
//            Order order = restaurant.getOrderQueue().get();
//            int orderNR = order.getNumber();
//            for ( OrderLine ol : order){ //while (order.hasOrderLines()) {
//                //OrderLine ol = order.getOrderLine();
//                int mealNR = ol.getMealNR();
//                int persons = ol.getPersons();
//                for (int p = 0; p < persons; p++) {
//                    restaurant.getMealsReadyQueue().put(prepareMeal(orderNR, mealNR));
//                }
//            }
//        }
//    }
    
    
     /**
     * Prepares a meal according to recipe (preparation time).
     * @param orderNR
     * @param mealNR
     * @return the prepared meal.
     */
    private Meal prepareMeal(int orderNR, int mealNR) {
        Recipe recipe = restaurant.getRecipes().get(mealNR);
        String mealName = recipe.getName();
        int procTime = recipe.getPreparationTime();
        try {
            Thread.sleep(procTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Meal(orderNR, mealNR, mealName);
    }

    @Override
    public void run() {
            while (!restaurant.getOrderQueue().empty()) {
            Order order = restaurant.getOrderQueue().get();
            int orderNR = order.getNumber();
            for ( OrderLine ol : order){ //while (order.hasOrderLines()) {
                //OrderLine ol = order.getOrderLine();
                int mealNR = ol.getMealNR();
                int persons = ol.getPersons();
                for (int p = 0; p < persons; p++) {
                    restaurant.getMealsReadyQueue().put(prepareMeal(orderNR, mealNR));
                }
            }
        } 
    }
   
}

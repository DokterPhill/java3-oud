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
  
     /**
     * Prepares a meal according to recipe (preparation time).
     * @param orderNR
     * @param mealNR
     * @return the prepared meal.
     */
    private void prepareMeal(int orderNR, int mealNR) {
        Recipe recipe = restaurant.getRecipes().get(mealNR);
        String mealName = recipe.getName();
        int procTime = recipe.getPreparationTime();
        try {
            Thread.sleep(procTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         restaurant.getMealsReadyQueue().put(new Meal(orderNR, mealNR, mealName));
         
    }

    @Override
    public void run() {
        
            while (!restaurant.getOrderQueue().empty()) {
            Order order = restaurant.getOrderQueue().get();
            int orderNR = order.getNumber();
            for ( OrderLine ol : order){
                
                int mealNR = ol.getMealNR();
                int persons = ol.getPersons();
                for (int p = 0; p < persons; p++) {
                    prepareMeal(orderNR, mealNR);
                    
                }
            }
        } 
    }
   
}

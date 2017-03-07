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
public class Server implements Runnable {
    
    Restaurant restaurant;

    public Server(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Meal meal = restaurant.getMealsReadyQueue().get();
        System.out.println( meal.toString());
    }
    
}

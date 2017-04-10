/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roel
 */
public class ExecuteRestaurant implements Runnable {

    Restaurant restaurant;
    Boolean run = true;
    private final ExecutorService exec = Executors.newFixedThreadPool(3);

    public ExecuteRestaurant(Restaurant restaurant) throws InterruptedException {
        this.restaurant = restaurant;
    }

    public void stopExecute() throws InterruptedException {
        waitSecond();
        this.run = false;
    }

    private void waitSecond() throws InterruptedException {
        Thread.sleep(6000);
    }

    private void ServerTask(ArrayList<Meal> meals) throws InterruptedException {
        for (Meal meal : meals) {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(meal.toString());
        }
    }

    @Override
    public void run() {

        while (run) {
            if (restaurant.isOrdersEmpty()) {
            } else {
                String[] orders = restaurant.getOrders();
                Boolean bool = false;
                for (String ordered : orders) {
                    if (ordered.contains("empty1")) {
                        bool = true;
                    }
                    if (!bool) {
                        CompletableFuture future = (CompletableFuture) exec.submit(new ProcessThread(restaurant));
                        try {
                            ServerTask((ArrayList<Meal>) future.get());
                        } catch (InterruptedException | ExecutionException ex) {
                            Logger.getLogger(ExecuteRestaurant.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }
        }
    }
}

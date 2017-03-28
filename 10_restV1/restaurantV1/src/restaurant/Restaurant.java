package restaurant;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Naive restaurant for concurrency lab.
 *
 * @author ode
 * @author hom
 */
public class Restaurant {

    /**
     * Name of the joint.
     */
    private String name;
    /**
     * Last order number to keep track of the orders.
     */
    private int orderCount;
    /**
     * The cookbook a.k.a. preparation rules.
     */
    private Map<Integer, Recipe> recipes;
    /**
     * What can be ordered here
     */
    private ArrayList<String> menu;
    /**
     * Queue of orders.
     */
    private Queue<Order> orderQueue;
    /**
     * Queue of meals.
     */
    private Queue<Meal> mealsReadyQueue;
    /**
     * Format for our price list and bills
     */
    private final DecimalFormat df = new DecimalFormat("##.00");
    private String[] lastOrdered;


    /**
     * Helper method for output formatting.
     */
    public static void printSeparator() {
        System.out
                .println("===================================="
                        + "==================================");
    }

    /**
     * Construct a named restaurant.
     *
     * @param name
     */
    public Restaurant(String name) {
        this.name = name;
        orderCount = 0;
        orderQueue = new Queue<Order>();
        mealsReadyQueue = new Queue<Meal>();
        recipes = new HashMap<Integer, Recipe>();
        menu = new ArrayList<String>();
        importMeals();

    }

    /**
     * Import the meals. Putting meals in a separate text file avoid hard coding
     * of meal properties into this source code. It would acquire a bad smell
     * over time ;-)).
     */
    final void importMeals() {
        Collection<String> mealInfo = Input.getMeals("meals.txt");
        for (String s : mealInfo) {
            String[] ss = s.split("#");
            int mealNR = Integer.parseInt(ss[0]);
            String mname = ss[1];
            float price = Float.parseFloat(ss[2]);
            int prepTime = Integer.parseInt(ss[3]);
            recipes.put(mealNR, new Recipe(mname, prepTime));
            menu.add(mealNR + ":\t" + mname + "\tprice:\t" + df.format(price));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public Map<Integer, Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Map<Integer, Recipe> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<String> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<String> menu) {
        this.menu = menu;
    }

    public Queue<Order> getOrderQueue() {
        return orderQueue;
    }

    public void setOrderQueue(Queue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }

    public Queue<Meal> getMealsReadyQueue() {
        return mealsReadyQueue;
    }

    public void setMealsReadyQueue(Queue<Meal> mealsReadyQueue) {
        this.mealsReadyQueue = mealsReadyQueue;
    }

    public String[] getLastOrdered() {
        return lastOrdered;
    }

    public void setLastOrdered(String[] lastOrdered) {
        this.lastOrdered = lastOrdered;
    }
    
    

    /**
     * Are there orders in work?
     *
     * @return
     */
    public boolean hasOrders() {
        return !orderQueue.empty();
    }

    /**
     * Removes one order from the order list.
     *
     * @return info for the next order
     *
     */
    public String getNextOrder() {
        return orderQueue.get().toString();
    }

    /**
     * Is there anything to serve?
     *
     * @return true if there are meals that can be obtained via the getMeals
     * method
     */
    public boolean hasMeals() {
        return !mealsReadyQueue.empty();
    }

    /**
     * Print the menu of the restaurant. Each meal has a number and this number
     * should be used to order a meal.
     */
    public void printMenu() {
        System.out.println("Menu of restaurant " + name);
        printSeparator();
        Set<Integer> mealNRs = recipes.keySet();
        for (String line : menu) {
            System.out.println(line);
        }
    }

    /**
     * String representation of the restaurant.
     *
     * @return a string.
     */
    @Override
    public String toString() {
        return "Restaurant " + name;
    }

    void procesOrders() throws InterruptedException {
        
        Thread cook = new Thread(new Cook(this));
        cook.start();
        cook.join();
    }

    void submitOrder(String... ordered) throws RestaurantException, InterruptedException {
        this.setLastOrdered(ordered);
        Thread waiter = new Thread(new Waiter(this));
        waiter.start();
        waiter.join();
    }

    void getNextMeal() throws InterruptedException {
        Thread server = new Thread(new Server(this));
        server.start();
        server.join();
    }
}

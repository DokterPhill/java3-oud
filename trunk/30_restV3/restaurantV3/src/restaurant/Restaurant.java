package restaurant;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
//import java.util.Queue;
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
     * Format for our price list and bills
     */
    private final DecimalFormat df = new DecimalFormat("##.00");
    private Queue<String> orders1;
    Thread execThread;

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
     * @throws java.lang.InterruptedException
     */
    public Restaurant(String name) throws InterruptedException {
        this.execThread = new Thread(new ExecuteRestaurant(this));
        this.name = name;
        orderCount = 1;
        recipes = new HashMap<Integer, Recipe>();
        menu = new ArrayList<String>();
        orders1 = new Queue();
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

    public int incrementOrderCount() {
        int count;
        synchronized (this) {
            count = this.orderCount;
            this.orderCount++;
        }
        return count;
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

    public Boolean isOrdersEmpty() {
        return orders1.empty();

    }

    public void stopExecutor() {

    }

    public String getOrders() {
        return this.orders1.get();

    }

    public void addOrders(String order) {
        System.out.println(order);
        orders1.put(order);

    }

    public void startExecutor() {
        execThread.start();
    }

    public void submitOrder(String... ordered) throws RestaurantException, InterruptedException {
        for (String string1 : ordered) {
            if (ordered.equals("Restaurant Closed")) {
            } else {
                addOrders(string1);
                System.out.println(string1);
            }
        }
    }

}

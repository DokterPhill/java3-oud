package restaurant;

/**
 * Restaurant driver class.
 * @author ode
 * @author hom
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Restaurant.printSeparator();
        Restaurant china = new Restaurant("Mei Ling");
        china.printMenu();
        Restaurant.printSeparator();
        System.out.println("Restaurant has opened!!");
        Restaurant.printSeparator();
        try {
            china.submitOrder("11, 7", "13, 4"); 
        } catch (RestaurantException e) {
            System.out.println(e.getMessage());
        }
        try {
            china.submitOrder("44, 4 ", "47, 8", " 13, 9 ",
                    "11, 5"); //change to server
        } catch (RestaurantException e) {
            System.out.println(e.getMessage());
        }
        china.procesOrders();
       
        try {
            china.submitOrder("45, 1 ", " 47, 2 ", " 10, 2");
        } catch (RestaurantException e) {
            System.out.println(e.getMessage());
        }
        try {
            china.submitOrder("10, 2 ", " 33, 2 ", " 19, 2"); 
        } catch (RestaurantException e) {
            System.out.println(e.getMessage());
        }
        china.procesOrders(); //change to cook
        System.out.println("");
        System.out.println("Getting all the meals");
        Restaurant.printSeparator();
        while (china.hasMeals()) {
        china.getNextMeal();
        }
        Restaurant.printSeparator();
    }

}

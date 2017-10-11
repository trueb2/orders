import java.util.Collections;
import java.util.Vector;

public class Store {
    private Vector<Worker> busyWorkers;
    private Vector<Worker> freeWorkers;
    private Vector<Order> orders;

    /**
     * Represents a store with a list of workers and orders that are received in a day.
     * Workers will process orders asynchronously
     * @param workers
     * @param orders
     */
    public Store(Worker[] workers, Order[] orders) {
        // Instantiate structures
        busyWorkers = new Vector<>();
        freeWorkers = new Vector<>();
        this.orders = new Vector<>();

        // Populate the vectors with the data, none of the workers are busy yet
        Collections.addAll(freeWorkers, workers);
        Collections.addAll(this.orders, orders);
    }

    /**
     * Opens a store for the day
     */
    public void open() {
        for(Worker w : freeWorkers)
            System.out.printf("%d :: %s\n", w.getId(), w.getName());

        for(Order o : orders) {
            o.printData();
        }
    }


    public static void main(String[] args) {
        System.out.println("The store is open!");


        Worker[] workers = {
            new Worker("Jaime"),
            new Worker("Tyrion")
        };

        Order[] orders = {
            new Order<>(1., 2., 3., 4.),
            new Order<>(1, 2, 3),
            new Order<>(true, false),
            new Order<>('a', 'b', 'c')
        };

        Store store = new Store(workers, orders);
        store.open();
    }
}

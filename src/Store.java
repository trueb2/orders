import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class Store {
    private Set<Worker> busyWorkers;
    private Set<Worker> freeWorkers;
    private Vector<Order> knownOrders;
    private Vector<Order> incompleteOrders;
    private Vector<Order> completedOrders;

    private final Semaphore semaphore;
    private volatile boolean closed;

    /**
     * Represents a store with a list of workers and knownOrders that are received in a day.
     * Workers will process knownOrders asynchronously
     * @param workers, the workers at the store who will be processing knownOrders
     * @param orders, the knownOrders that will come in over the course of the day
     */
    public Store(Worker[] workers, Order[] orders) {
        // Instantiate structures
        busyWorkers = Collections.synchronizedSet(new HashSet<Worker>());
        freeWorkers = Collections.synchronizedSet(new HashSet<Worker>());
        knownOrders = new Vector<>();
        incompleteOrders = new Vector<>();
        completedOrders = new Vector<>();

        // Doesn't matter if one worker does all of the work, fair set to false
        semaphore = new Semaphore(workers.length, false);

        // Populate the vectors with the data, none of the workers are busy yet
        Collections.addAll(freeWorkers, workers);
        Collections.addAll(knownOrders, orders);
    }

    /**
     * Opens a store for the day and
     * visits the knownOrders to trigger their timers
     */
    public void open() {
        // Store is open
        closed = false;

        // Orders come in at different times of the day
        for(Order o : knownOrders)
            o.awaitOrder(this);

        try {
            while(!closed) {
                semaphore.acquire();

            }

        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes all orders effectively closing the store for the day
     */
    public void close() {
        for(Order o : knownOrders)
            o.cancel();
    }


    /**
     * An order is received at the store
     * @param order that needs to be completed
     */
    public void acceptOrder(Order order) {
        incompleteOrders.addElement(order);
        Thread thread = new Thread(() -> {
            try {
                while(!closed) {
                    semaphore.acquire();
                    Worker w = freeWorkers.iterator().next();
                    freeWorkers.remove(w);
                    busyWorkers.add(w);
                    w.complete(order);
                    semaphore.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
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

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                store.close();
                store.closed = true;
                System.out.println("The store is closed!");
            }
        }, orders.length * 1000);
    }
}


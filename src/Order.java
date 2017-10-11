import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Order<T> extends Unique {
    private ArrayList<T> data;
    private int randomWait;
    private Timer timer;

    /**
     * Create an Order accepting an arbitrary number of T inputs
     * @param data
     */
    public Order(T ... data) {
        // Store the data for later use
        this.data = new ArrayList<>(data.length);
        Collections.addAll(this.data, data);

        // Pick a random wait time for the order to be issued that is between 0 and 1000 ms
        timer = new Timer(true);
        randomWait = ThreadLocalRandom.current().nextInt(0, 1001);
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void printData() {
        for(T datum : data)
            System.out.printf("%d :: %s\n", getId(), datum);
    }

    /**
     * This order is sent to the provided store
     * after a random wait in ms between 0 and 1000
     * @param store to receive the order
     */
    public void awaitOrder(Store store) {
        Order thisOrder = this;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Putting in order " + getId());
                store.acceptOrder(thisOrder);
            }
        }, randomWait);
    }

    /**
     * Cancels the scheduled order
     * Has no effect if order is already complete
     */
    public void cancel() {
        timer.cancel();
    }
}

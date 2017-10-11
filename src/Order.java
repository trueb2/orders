import java.util.ArrayList;
import java.util.Collections;

public class Order<T> extends Unique {
    private ArrayList<T> data;

    /**
     * Create an Order accepting an arbitrary number of T inputs
     * @param data
     */
    public Order(T ... data) {
        // Store the data for later use
        this.data = new ArrayList<>(data.length);
        Collections.addAll(this.data, data);
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void printData() {
        for(T datum : data)
            System.out.printf("%d :: %s\n", getId(), datum);
    }
}

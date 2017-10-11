public class Worker extends Unique {
    private String name;

    public Worker(String name) {
        super();

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void complete(Order order) {
        System.out.printf("Worker %d :: ", getId());
        order.printData();
    }
}

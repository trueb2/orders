public class Unique {
    private static Integer idCounter = 0;
    private int id;

    public Unique() {
        synchronized (idCounter) {
            id = idCounter++;
        }
    }

    public int getId() {
        return id;
    }
}

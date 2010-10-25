package turtle;

/**
 *
 * @author Sean
 */
public enum Finger {
    THUMB("thumb"), INDEX("index"), MIDDLE("middle"), RING("ring"), PINKIE("pinkie");

    private final String name;
    
    private Finger(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

/**
 *
 * @author Sean
 */
public enum Finger {
    NONE("none", 0), THUMB("thumb", 1), INDEX("index", 2), MIDDLE("middle", 3), RING("ring", 4), PINKIE("pinky", 5);

    private final String name;
    private final int value;

    private Finger(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}

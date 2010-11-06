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
    THUMB("thumb"), INDEX("index"), MIDDLE("middle"), RING("ring"), PINKIE("pinky");

    private final String name;

    private Finger(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

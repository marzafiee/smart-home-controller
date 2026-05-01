package smarthome_classes.devices;

/**
 * smart door device with lock and unlock behavior.
 * in this project, we're assuming on means unlocked and off means locked.
 */
public class Door extends AbstractDevice {
    private boolean locked;

    public Door(String name) {
        super(name);
        this.locked = true; // start locked for safety
        this.on = false;
    }

    @Override
    public void turnOn() {
        this.on = true;
        this.locked = false;
        notifyObservers("UNLOCKED");
    }

    @Override
    public void turnOff() {
        this.on = false;
        this.locked = true;
        notifyObservers("LOCKED");
    }

    /** explicit lock helper that calls turnoff(). */
    public void lock() {
        turnOff();
    }

    /** explicit unlock helper that calls turnon(). */
    public void unlock() {
        turnOn();
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public String getStatus() {
        String state = locked ? "LOCKED" : "UNLOCKED";
        return "Door [" + name + "] - " + state;
    }
}

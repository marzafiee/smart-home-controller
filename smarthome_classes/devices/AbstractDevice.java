package smarthome_classes.devices;

import smarthome_classes.observers.DeviceObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * base class for shared device behavior.
 * this stores common fields and observer list logic.
 */
public abstract class AbstractDevice implements SmartDevice {
    protected final String name;
    protected boolean on = false;

    // observer list for this device
    private final List<DeviceObserver> observers = new ArrayList<>();

    protected AbstractDevice(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isOn() {
        return on;
    }

    // observer methods

    @Override
    public void addObserver(DeviceObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(DeviceObserver observer) {
        observers.remove(observer);
    }

    /**
     * notifies each observer using the action message.
     *
     * @param action action text like "turned on"
     */
    @Override
    public void notifyObservers(String action) {
        for (DeviceObserver observer : observers) {
            observer.update(this, action);
        }
    }
}

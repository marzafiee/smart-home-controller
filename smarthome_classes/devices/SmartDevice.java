package smarthome_classes.devices;

import smarthome_classes.observers.DeviceObserver;

/**
 * core interface for all smart devices.
 */
public interface SmartDevice {

    /** turns the device on and should notify observers. */
    void turnOn();

    /** turns the device off and should notify observers. */
    void turnOff();

    /** returns readable status text. */
    String getStatus();

    /** returns device display name. */
    String getName();

    /** adds an observer for state updates. */
    void addObserver(DeviceObserver observer);

    /** removes an observer. */
    void removeObserver(DeviceObserver observer);

    /** sends action update to all observers. */
    void notifyObservers(String action);

    /** returns true if device is currently on. */
    boolean isOn();
}

package smarthome_classes.decorators;

import smarthome_classes.devices.SmartDevice;
import smarthome_classes.observers.DeviceObserver;

/**
 * base decorator class for devices.
 * it forwards calls to wrapped device unless child class changes behavior.
 */
public abstract class DeviceDecorator implements SmartDevice {

    /** wrapped device instance. */
    protected final SmartDevice wrappedDevice;

    protected DeviceDecorator(SmartDevice device) {
        this.wrappedDevice = device;
    }

    // forwarding methods to wrapped device
    @Override
    public void turnOn() {
        wrappedDevice.turnOn();
    }

    @Override
    public void turnOff() {
        wrappedDevice.turnOff();
    }

    @Override
    public String getStatus() {
        return wrappedDevice.getStatus();
    }

    @Override
    public String getName() {
        return wrappedDevice.getName();
    }

    @Override
    public boolean isOn() {
        return wrappedDevice.isOn();
    }

    @Override
    public void addObserver(DeviceObserver observer) {
        wrappedDevice.addObserver(observer);
    }

    @Override
    public void removeObserver(DeviceObserver observer) {
        wrappedDevice.removeObserver(observer);
    }

    @Override
    public void notifyObservers(String action) {
        wrappedDevice.notifyObservers(action);
    }
}

package smarthome_classes.decorators;

import smarthome_classes.devices.SmartDevice;
import java.time.LocalDateTime;

/**
 * decorator that tracks energy usage for a wrapped device.
 */
public class EnergyMonitorDecorator extends DeviceDecorator {
    private final double wattage; // device power in watts
    private double totalKWhConsumed = 0.0;
    private LocalDateTime lastTurnOnTime = null;
    private int onOffCycles = 0;

    public EnergyMonitorDecorator(SmartDevice device, double wattage) {
        super(device);
        this.wattage = wattage;
    }

    @Override
    public void turnOn() {
        if (!wrappedDevice.isOn()) {
            lastTurnOnTime = LocalDateTime.now();
            onOffCycles++;
        }
        wrappedDevice.turnOn();
        wrappedDevice.notifyObservers("energy monitor: now tracking (" + wattage + "W)");
    }

    @Override
    public void turnOff() {
        if (wrappedDevice.isOn() && lastTurnOnTime != null) {
            // calculate this usage session before turn off
            long secondsOn = java.time.Duration
                    .between(lastTurnOnTime, LocalDateTime.now())
                    .getSeconds();
            double hoursOn = secondsOn / 3600.0;
            double kWhUsed = (wattage * hoursOn) / 1000.0;
            totalKWhConsumed += kWhUsed;
            lastTurnOnTime = null;
        }
        wrappedDevice.turnOff();
    }

    /** returns total tracked energy in kwh. */
    public double getTotalKWh() {
        return totalKWhConsumed;
    }

    /** returns how many on/off cycles were tracked. */
    public int getOnOffCycles() {
        return onOffCycles;
    }

    /** resets tracked energy values. */
    public void resetEnergyCounter() {
        totalKWhConsumed = 0.0;
        onOffCycles = 0;
        wrappedDevice.notifyObservers("energy counter reset");
    }

    @Override
    public String getStatus() {
        return wrappedDevice.getStatus()
                + String.format(" [Energy: %.4f kWh, cycles: %d]",
                        totalKWhConsumed, onOffCycles);
    }
}

package smarthome_classes.observers;

import smarthome_classes.devices.SmartDevice;

/**
 * observer that prints live updates to console.
 */
public class ConsoleDisplay implements DeviceObserver {
    @Override
    public void update(SmartDevice device, String action) {
        System.out.printf("%s%s %s%n", "  ⟶  " + device.getName(), action);
    }
}

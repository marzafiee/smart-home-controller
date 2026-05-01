package smarthome_classes.observers;

import smarthome_classes.devices.SmartDevice;

/**
 * observer that prints live updates to console.
 */
public class ConsoleDisplay implements DeviceObserver {
    @Override
    public void update(SmartDevice device, String action) {
        // Avoid printf format parsing on action text, especially when action
        // contains percent signs like "brightness set to 60%".
        System.out.println("  ->  " + device.getName() + " " + action);
    }
}

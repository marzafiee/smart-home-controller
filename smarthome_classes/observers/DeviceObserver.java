package smarthome_classes.observers;

import smarthome_classes.devices.SmartDevice;

/**
 * observer interface for device updates.
 */
public interface DeviceObserver {

    /**
     * called when a device reports a new action.
     *
     * @param device device object that changed
     * @param action action message sent by the device
     */
    void update(SmartDevice device, String action);
}

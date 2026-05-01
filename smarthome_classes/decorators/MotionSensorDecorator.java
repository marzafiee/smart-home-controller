package smarthome_classes.decorators;

import smarthome_classes.devices.SmartDevice;

/**
 * decorator that adds motion sensor behavior to a device.
 */
public class MotionSensorDecorator extends DeviceDecorator {
    private boolean motionDetected = false;
    private int sensitivity; // from 1 to 10, starts at 5
    private final int autOffSeconds; // seconds before auto-off message window

    public MotionSensorDecorator(SmartDevice device) {
        this(device, 30);
    }

    public MotionSensorDecorator(SmartDevice device, int autoOffSeconds) {
        super(device);
        this.autOffSeconds = autoOffSeconds;
        this.sensitivity = 5;
    }

    /** triggers motion event, turns on device if needed, then notifies. */
    public void triggerMotion() {
        motionDetected = true;
        // if device is off, motion should switch it on
        if (!wrappedDevice.isOn()) {
            wrappedDevice.turnOn();
        }
        // send motion-specific observer message
        wrappedDevice.notifyObservers("MOTION DETECTED!! Going to auto-on for " + autOffSeconds + "s");
    }

    /** clears motion state and notifies observers. */
    public void clearMotion() {
        motionDetected = false;
        wrappedDevice.notifyObservers("motion cleared");
    }

    public boolean isMotionDetected() {
        return motionDetected;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = Math.max(1, Math.min(10, sensitivity));
        wrappedDevice.notifyObservers("motion sensor sensitivity set to " + this.sensitivity);
    }

    public int getSensitivity() {
        return sensitivity;
    }

    /** adds motion sensor details to the base status text. */
    @Override
    public String getStatus() {
        return wrappedDevice.getStatus()
                + " [Motion Sensor: " + (motionDetected ? "TRIGGERED" : "clear")
                + ", sensitivity=" + sensitivity + "]";
    }
}

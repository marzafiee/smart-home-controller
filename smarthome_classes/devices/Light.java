package smarthome_classes.devices;

/**
 * smart light device with brightness support.
 */
public class Light extends AbstractDevice {

    private int brightness; // range is 0 to 100

    public Light(String name) {
        super(name);
        this.brightness = 100; // default brightness
    }

    @Override
    public void turnOn() {
        this.on = true;
        notifyObservers("turned ON");
    }

    @Override
    public void turnOff() {
        this.on = false;
        notifyObservers("turned OFF");
    }

    /**
     * sets brightness and updates on/off state based on value.
     */
    public void setBrightness(int brightness) {
        this.brightness = Math.max(0, Math.min(100, brightness));
        this.on = this.brightness > 0;
        notifyObservers("brightness set to " + this.brightness + "%");
    }

    public int getBrightness() {
        return brightness;
    }

    @Override
    public String getStatus() {
        String state = on ? "ON (brightness: " + brightness + "%)" : "OFF";
        return "Light [" + name + "] - " + state;
    }
}

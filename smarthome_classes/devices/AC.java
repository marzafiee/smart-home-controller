package smarthome_classes.devices;

/**
 * smart ac device with mode and target temperature.
 */
public class AC extends AbstractDevice {
    public enum Mode {
        COOL, HEAT, FAN_ONLY
    }

    private double targetTemp;
    private Mode mode;

    public AC(String name) {
        super(name);
        this.targetTemp = 24.0;
        this.mode = Mode.COOL;
    }

    @Override
    public void turnOn() {
        this.on = true;
        notifyObservers("turned ON [" + mode + " @ " + targetTemp + "°C]");
    }

    @Override
    public void turnOff() {
        this.on = false;
        notifyObservers("turned OFF");
    }

    public void setTargetTemp(double temp) {
        this.targetTemp = temp;
        notifyObservers("target temperature set to " + temp + "°C");
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        notifyObservers("mode changed to " + mode);
    }

    public double getTargetTemp() {
        return targetTemp;
    }

    public Mode getMode() {
        return mode;
    }

    @Override
    public String getStatus() {
        String state = on
                ? "ON [" + mode + " @ " + targetTemp + "°C]"
                : "OFF";
        return "AC [" + name + "] - " + state;
    }
}

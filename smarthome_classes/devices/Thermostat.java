package smarthome_classes.devices;

/**
 * smart thermostat device that tracks target temperature.
 */
public class Thermostat extends AbstractDevice {
    private double temperature; // current set temperature (celsius)
    private static final double STANDBY_TEMP = 16.0;

    public Thermostat(String name) {
        super(name);
        this.temperature = 20.0; // default start value
    }

    @Override
    public void turnOn() {
        this.on = true;
        notifyObservers("turned ON at " + temperature + "°C");
    }

    @Override
    public void turnOff() {
        this.on = false;
        this.temperature = STANDBY_TEMP;
        notifyObservers("turned OFF (standby " + STANDBY_TEMP + "°C)");
    }

    /**
     * sets the target temperature and marks thermostat as on.
     */
    public void setTemperature(double temp) {
        this.temperature = temp;
        this.on = true;
        notifyObservers("temperature set to " + temp + "°C");
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String getStatus() {
        String state = on ? "ON (" + temperature + "°C)" : "OFF (standby)";
        return "Thermostat [" + name + "] - " + state;
    }
}

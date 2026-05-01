package smarthome_classes.factory;

import smarthome_classes.devices.*;

/**
 * factory class that creates devices by type string.
 * this keeps creation logic in one place.
 */
public class DeviceFactory {
    // private constructor because this is just a static utility class
    private DeviceFactory() {
    }

    /**
     * creates and returns a smartdevice for the requested type.
     *
     * @param type device type string: "light", "thermostat", "door", "ac"
     * @param name display name for this device
     * @return new smartdevice object
     * @throws IllegalArgumentException if type is not recognized
     */
    public static SmartDevice createDevice(String type, String name) {
        switch (type.toUpperCase().trim()) {
            case "LIGHT":
                return new Light(name);
            case "THERMOSTAT":
                return new Thermostat(name);
            case "DOOR":
                return new Door(name);
            case "AC":
                return new AC(name);
            default:
                throw new IllegalArgumentException(
                        "Unknown device type: '" + type + "'. " +
                                "Supported types: LIGHT, THERMOSTAT, DOOR, AC");
        }
    }

    /**
     * helper method to create a light by name.
     */
    public static Light createLight(String name) {
        return new Light(name);
    }

    /**
     * helper method to create a thermostat by name.
     */
    public static Thermostat createThermostat(String name) {
        return new Thermostat(name);
    }

    /**
     * helper method to create a door by name.
     */
    public static Door createDoor(String name) {
        return new Door(name);
    }

    /**
     * helper method to create an ac by name.
     */
    public static AC createAC(String name) {
        return new AC(name);
    }
}

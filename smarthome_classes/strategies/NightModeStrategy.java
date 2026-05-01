package smarthome_classes.strategies;

import smarthome_classes.devices.Door;
import smarthome_classes.devices.Light;
import smarthome_classes.devices.SmartDevice;
import smarthome_classes.devices.Thermostat;
import smarthome_classes.rooms.Room;
import java.util.List;

/**
 * night mode strategy.
 * this mode turns things into night setup.
 */
public class NightModeStrategy implements AutomationStrategy {
    private static final double NIGHT_TEMP = 18.0; // not too hot or cold

    @Override
    public void execute(List<Room> rooms) {
        System.out.println("\nNIGHT MODE ACTIVATED :(");
        System.out.println("\nTime to sleep and dreamm!──────");

        for (Room room : rooms) {
            for (SmartDevice device : room.getDevices()) {
                if (device instanceof Light) {
                    device.turnOff();
                } else if (device instanceof Door) {
                    ((Door) device).lock();
                } else if (device instanceof Thermostat) {
                    ((Thermostat) device).setTemperature(NIGHT_TEMP);
                } else {
                    // for any other device type, just turn it off
                    device.turnOff();
                }
            }
        }
        System.out.println("─────────────────────────────────────");
    }

    @Override
    public String getModeName() {
        return "Night Mode";
    }
}

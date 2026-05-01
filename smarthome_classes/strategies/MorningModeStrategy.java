package smarthome_classes.strategies;

import smarthome_classes.devices.Door;
import smarthome_classes.devices.Light;
import smarthome_classes.devices.SmartDevice;
import smarthome_classes.devices.Thermostat;
import smarthome_classes.rooms.Room;
import java.util.List;

/**
 * morning mode strategy.
 * this mode prepares the home for morning use.
 */
public class MorningModeStrategy implements AutomationStrategy {
    private static final double MORNING_TEMP = 21.0; // can be changed here if needed

    @Override
    public void execute(List<Room> rooms) {
        System.out.println("\nMORNING MODE ACTIVATED :))");
        System.out.println("\nRise and Shineeeee");

        for (Room room : rooms) {
            for (SmartDevice device : room.getDevices()) {
                if (device instanceof Light) {
                    device.turnOn();
                } else if (device instanceof Thermostat) {
                    ((Thermostat) device).setTemperature(MORNING_TEMP);
                } else if (device instanceof Door) {
                    // i only unlock doors that look like the front door
                    if (device.getName().toLowerCase().contains("front")) {
                        ((Door) device).unlock();
                    }
                    // other doors stay locked in this mode
                }
                // ac is left as-is in morning mode. as it should
            }
        }
        System.out.println("─────────────────────────────────────");
    }

    @Override
    public String getModeName() {
        return "Morning Mode";
    }
}

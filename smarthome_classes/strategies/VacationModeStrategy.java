package smarthome_classes.strategies;

import smarthome_classes.devices.Door;
import smarthome_classes.devices.SmartDevice;
import smarthome_classes.devices.Thermostat;
import smarthome_classes.rooms.Room;
import java.util.List;

/**
 * vacation mode strategy.
 * this mode leaves the house in a safer low-power state. we call it eco mode
 */
public class VacationModeStrategy implements AutomationStrategy {
    private static final double ECO_TEMP = 15.0;

    @Override
    public void execute(List<Room> rooms) {
        System.out.println("\nVACATION MODE ACTIVATED!!!");
        System.out.println("\nTime to chill innit :) ────────────────");

        for (Room room : rooms) {
            for (SmartDevice device : room.getDevices()) {
                if (device instanceof Door) {
                    ((Door) device).lock();
                } else if (device instanceof Thermostat) {
                    ((Thermostat) device).setTemperature(ECO_TEMP);
                } else {
                    device.turnOff();
                }
            }
        }
        System.out.println("─────────────────────────────────────");
    }

    @Override
    public String getModeName() {
        return "Vacation Mode";
    }
}

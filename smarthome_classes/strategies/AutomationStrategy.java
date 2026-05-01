package smarthome_classes.strategies;

import smarthome_classes.rooms.Room;
import java.util.List;

/**
 * strategy interface for automation modes.
 * each mode class implements how it should run.
 */
public interface AutomationStrategy {
    /**
     * runs this mode using the room list.
     *
     * @param rooms list of rooms in the smart home
     */
    void execute(List<Room> rooms);

    /** returns the mode name for prints or logs. */
    String getModeName();
}

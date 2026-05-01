package smarthome_classes.rooms;

import smarthome_classes.devices.SmartDevice;
import smarthome_classes.observers.DeviceObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this class groups devices by room name.
 * it helps us run actions on all devices in a room together.
 */
public class Room {
    private final String name;
    private final List<SmartDevice> devices = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // device handling
    /** adds one device to this room. */
    public void addDevice(SmartDevice device) {
        devices.add(device);
    }

    /** removes one device from this room. */
    public void removeDevice(SmartDevice device) {
        devices.remove(device);
    }

    /** returns room devices as read-only list. */
    public List<SmartDevice> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    // room-wide controls
    /** turns on every device in this room. */
    public void turnAllOn() {
        System.out.println("[Room: " + name + "] Turning all devices ON...");
        devices.forEach(SmartDevice::turnOn);
    }

    /** turns off every device in this room. */
    public void turnAllOff() {
        System.out.println("[Room: " + name + "] Turning all devices OFF...");
        devices.forEach(SmartDevice::turnOff);
    }

    // observer helper
    /**
     * adds an observer to each device currently in this room.
     */
    public void addObserverToAll(DeviceObserver observer) {
        devices.forEach(d -> d.addObserver(observer));
    }

    // status printing
    /** prints a simple status report for devices in this room. */
    public void printRoomStatus() {
        System.out.println("\nRoom: " + name + " ──────────────");
        if (devices.isEmpty()) {
            System.out.println("(no devices in this room)");
        } else {
            devices.forEach(d -> System.out.println("│  " + d.getStatus()));
        }
    }

    @Override
    public String toString() {
        return "Room{name='" + name + "', devices=" + devices.size() + "}";
    }
}

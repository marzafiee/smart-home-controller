package smarthome_classes.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// importing necessary classes for the controller
import smarthome_classes.devices.SmartDevice;
import smarthome_classes.factory.DeviceFactory;
import smarthome_classes.observers.ActivityLogger;
import smarthome_classes.observers.ConsoleDisplay;
import smarthome_classes.observers.DeviceObserver;
import smarthome_classes.rooms.Room;
import smarthome_classes.strategies.AutomationStrategy;

/**
 * this class is the main controller for the smart home.
 *
 * i use singleton here so only one controller object exists.
 * this class keeps rooms, observers, and current mode.
 */
public class SmartHomeController {
    // singleton setup
    /** this holds the single shared instance. */
    private static volatile SmartHomeController instance;

    /** private constructor so no one creates extra controllers. */
    private SmartHomeController() {
        System.out.println("SmartHomeController initialised (Singleton).");
    }

    /**
     * returns the single controller instance.
     * double-check locking is used for thread safety.
     */
    public static SmartHomeController getInstance() {
        if (instance == null) {
            synchronized (SmartHomeController.class) {
                if (instance == null) {
                    instance = new SmartHomeController();
                }
            }
        }
        return instance;
    }

    // stored state
    private final List<Room> rooms = new ArrayList<>();
    private final List<DeviceObserver> globalObservers = new ArrayList<>();

    // default observers used by the app
    private final ActivityLogger activityLogger = new ActivityLogger();
    private final ConsoleDisplay consoleDisplay = new ConsoleDisplay();

    // current mode strategy (null means no mode yet)
    private AutomationStrategy currentStrategy = null;

    // initial setup
    /**
     * adds default observers globally.
     * call this once after getinstance().
     */
    public void initDefaultObservers() {
        addGlobalObserver(activityLogger);
        addGlobalObserver(consoleDisplay);
    }

    // room handling
    /** adds a room and links current global observers to its devices. */
    public void addRoom(Room room) {
        rooms.add(room);
        // make sure new room also follows current observer setup
        for (DeviceObserver obs : globalObservers) {
            room.addObserverToAll(obs);
        }
    }

    /** finds a room by name without case sensitivity. */
    public Optional<Room> findRoom(String name) {
        return rooms.stream()
                .filter(r -> r.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    /** returns rooms as read-only list. */
    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    // device handling
    /**
     * creates a device through the factory, adds it to a room,
     * and attaches current global observers.
     *
     * @param type    device type: "light", "thermostat", "door", "ac"
     * @param name    display name for the device
     * @param room    room where the device belongs
     * @return        created smartdevice
     */
    public SmartDevice addDevice(String type, String name, Room room) {
        SmartDevice device = DeviceFactory.createDevice(type, name);
        room.addDevice(device);
        // attach every global observer to the new device
        for (DeviceObserver obs : globalObservers) {
            device.addObserver(obs);
        }
        return device;
    }

    /** adds an already-created device (like decorated one) to a room. */
    public void addDecoratedDevice(SmartDevice device, Room room) {
        room.addDevice(device);
        for (DeviceObserver obs : globalObservers) {
            device.addObserver(obs);
        }
    }

    // observer handling
    /**
     * adds one observer globally so it receives updates from all devices.
     */
    public void addGlobalObserver(DeviceObserver observer) {
        if (!globalObservers.contains(observer)) {
            globalObservers.add(observer);
            // also attach it to devices that already exist
            for (Room room : rooms) {
                room.addObserverToAll(observer);
            }
        }
    }

    // strategy and automation modes
    /**
     * sets and runs the selected automation strategy immediately.
     *
     * @param strategy  automation mode object to activate
     */
    public void activateMode(AutomationStrategy strategy) {
        this.currentStrategy = strategy;
        System.out.println("\n[Controller] Activating: " + strategy.getModeName());
        strategy.execute(rooms);
    }

    /** returns current strategy, or null if none selected yet. */
    public AutomationStrategy getCurrentStrategy() {
        return currentStrategy;
    }

    // display helper
    /** prints status for every room and device. */
    public void printAllStatus() {
        System.out.println("SMART HOME FULL STATUS OF ROOMS AND DEVICES:");
        if (rooms.isEmpty()) {
            System.out.println("No rooms configured.");
        } else {
            rooms.forEach(Room::printRoomStatus);
        }
        System.out.println();
    }

    /** gives access to activity log data. */
    public ActivityLogger getActivityLogger() {
        return activityLogger;
    }
}

import smarthome_classes.controller.SmartHomeController;
import smarthome_classes.rooms.Room;
import smarthome_classes.devices.*;
import smarthome_classes.factory.DeviceFactory;
import smarthome_classes.decorators.EnergyMonitorDecorator;
import smarthome_classes.decorators.MotionSensorDecorator;
import smarthome_classes.strategies.MorningModeStrategy;
import smarthome_classes.strategies.NightModeStrategy;
import smarthome_classes.strategies.VacationModeStrategy;

/**
 * main class for running the smart home demo.
 *
 * i kept this flow to show how each pattern is used:
 * 1) singleton
 * 2) factory
 * 3) observer
 * 4) decorator
 * 5) strategy
 */
public class SmartHomeApplication {

    public static void main(String[] args) {

        separator("SMART HOME CONTROLLER");

        // singleton: both variables should point to the same controller object
        separator("1. SINGLETON pattern class: SmartHomeController");

        SmartHomeController controller = SmartHomeController.getInstance();
        controller.initDefaultObservers();

        SmartHomeController controller2 = SmartHomeController.getInstance();
        System.out.println("Same instance? " + (controller == controller2)); // should print true

        // making some rooms first so we can assign devices into them
        Room livingRoom = new Room("Living Room");
        Room bedroom = new Room("Bedroom");
        Room kitchen = new Room("Kitchen");
        Room entryway = new Room("Entryway");

        controller.addRoom(livingRoom);
        controller.addRoom(bedroom);
        controller.addRoom(kitchen);
        controller.addRoom(entryway);

        // factory: controller asks the factory for device objects by type
        separator("2. FACTORY: DeviceFactory.createDevice(...)");

        // i am creating all regular devices through one method here to keep it
        // consistent
        SmartDevice livingLight = controller.addDevice("LIGHT", "Living Room Light", livingRoom);
        SmartDevice bedroomLight = controller.addDevice("LIGHT", "Bedroom Light", bedroom);
        SmartDevice kitchenLight = controller.addDevice("LIGHT", "Kitchen Light", kitchen);
        SmartDevice thermostat = controller.addDevice("THERMOSTAT", "Main Thermostat", livingRoom);
        SmartDevice frontDoor = controller.addDevice("DOOR", "Front Door", entryway);
        SmartDevice backDoor = controller.addDevice("DOOR", "Back Door", entryway);
        SmartDevice bedroomAC = controller.addDevice("AC", "Bedroom AC", bedroom);

        // this try/catch checks what happens if someone gives a wrong device type
        try {
            controller.addDevice("TOASTER", "Broken Device", livingRoom);
        } catch (IllegalArgumentException e) {
            System.out.println("Factory correctly rejected unknown type: " + e.getMessage());
        }

        // observer: logger/display should get updates when these actions run
        separator("3. OBSERVER for turning devices on/off, watch the log");

        System.out.println("\nIndividual device control");
        livingLight.turnOn();
        kitchenLight.turnOn();
        ((Light) bedroomLight).setBrightness(60);
        ((Thermostat) thermostat).setTemperature(22.0);
        ((Door) frontDoor).unlock();
        bedroomAC.turnOn();

        // decorator: adding extra behavior around a device at runtime
        separator("4. DECORATOR — adding motion detection + energy monitoring at runtime");

        // this wraps a light so it can react to motion
        MotionSensorDecorator motionKitchenLight = new MotionSensorDecorator(
                DeviceFactory.createLight("Kitchen Motion Light"), 60);
        controller.addDecoratedDevice(motionKitchenLight, kitchen);

        // this wraps an ac so we can track its power usage
        EnergyMonitorDecorator monitoredAC = new EnergyMonitorDecorator(
                DeviceFactory.createAC("Bedroom AC (monitored)"), 1500.0);
        controller.addDecoratedDevice(monitoredAC, bedroom);

        // trying the motion behavior
        System.out.println("\n--- Motion sensor triggered ---");
        motionKitchenLight.setSensitivity(8);
        motionKitchenLight.triggerMotion(); // should auto turn on and notify observers
        System.out.println(motionKitchenLight.getStatus()); // should include motion sensor details

        System.out.println("\n--- Energy monitor ---");
        monitoredAC.turnOn();
        monitoredAC.turnOff();
        System.out.println(monitoredAC.getStatus());

        // stacking decorators means one wrapper can wrap another wrapper
        EnergyMonitorDecorator doubleDecorated = new EnergyMonitorDecorator(
                new MotionSensorDecorator(DeviceFactory.createLight("Garden Light")), 60.0);
        controller.addDecoratedDevice(doubleDecorated, entryway);
        System.out.println("\n--- Stacked decorator (motion + energy monitor) ---");
        doubleDecorated.turnOn();
        System.out.println(doubleDecorated.getStatus());

        // room-level action: this should switch off everything in kitchen
        separator("Room-Level Control — turn off entire kitchen");
        kitchen.turnAllOff();

        // checking a full status snapshot after the room command
        controller.printAllStatus();

        // strategy: changing mode objects to apply different automation rules
        separator("5. STRATEGY — Automation Modes");

        System.out.println("Current state before Night Mode:");
        controller.printAllStatus();

        // first mode: night
        controller.activateMode(new NightModeStrategy());
        controller.printAllStatus();

        // second mode: morning
        controller.activateMode(new MorningModeStrategy());
        controller.printAllStatus();

        // third mode: vacation
        controller.activateMode(new VacationModeStrategy());
        controller.printAllStatus();

        // final check: print all observer log entries
        separator("ACTIVITY LOG (Observer for all recorded events)");
        controller.getActivityLogger().printLog();
        System.out.println("Total events logged: "
                + controller.getActivityLogger().count());

        separator("DEMO COMPLETE :)");
    }

    /** helper method that prints each section title. */
    private static void separator(String title) {
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.printf("║  %-52s║%n", title);
        System.out.println("═══════════════════════════════════════════════════");
    }
}

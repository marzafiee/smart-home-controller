package smarthome_classes.observers;

import smarthome_classes.devices.SmartDevice;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * observer that stores timestamped activity entries.
 */
public class ActivityLogger implements DeviceObserver {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final List<String> log = new ArrayList<>();

    @Override
    public void update(SmartDevice device, String action) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String entry = String.format("[%s] %s → %s", timestamp, device.getName(), action);
        log.add(entry);
    }

    /** returns log list as read-only view. */
    public List<String> getLog() {
        return Collections.unmodifiableList(log);
    }

    /** prints all activity log entries. */
    public void printLog() {
        System.out.println("\nACTIVITY LOG:");
        if (log.isEmpty()) {
            System.out.println("(no activity yet)");
        } else {
            log.forEach(entry -> System.out.println("  " + entry));
        }
    }

    /** returns total number of recorded events. */
    public int count() {
        return log.size();
    }

    /** clears all log entries. */
    public void clear() {
        log.clear();
    }
}

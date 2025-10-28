package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a collection of log entries documenting interactions with a contact.
 *
 * This class is immutable. Once constructed, the list of log entries cannot be modified directly.
 * To add a log entry, use {@code addLogEntry} which returns a new InteractionLog instance.
 * The internal list is never {@code null}.
 */
public class InteractionLog {
    private final List<LogEntry> logs;

    /**
     * Creates an empty InteractionLog with no log entries.
     */
    public InteractionLog() {
        this.logs = new ArrayList<>();
    }

    /**
     * Creates an InteractionLog with the given list of log entries.
     * The list is defensively copied to ensure immutability.
     *
     * @param logs the list of log entries. If null, creates an empty log.
     */
    public InteractionLog(List<LogEntry> logs) {
        if (logs == null) {
            this.logs = new ArrayList<>();
        } else {
            this.logs = new ArrayList<>(logs);
        }
    }

    /**
     * Returns an unmodifiable view of the log entries.
     * The list is sorted with newest entries first (most recent at index 0).
     *
     * @return an unmodifiable list of log entries
     */
    public List<LogEntry> getLogs() {
        return Collections.unmodifiableList(logs);
    }

    /**
     * Returns a new InteractionLog with the given log entry added.
     * The new entry is added at the beginning of the list (most recent first).
     * This method does not modify the current InteractionLog instance.
     *
     * @param entry the log entry to add. It must not be null
     * @return a new InteractionLog instance with the entry added
     */
    public InteractionLog addLogEntry(LogEntry entry) {
        assert entry != null : "LogEntry should not be null";

        List<LogEntry> updatedLogs = new ArrayList<>();
        updatedLogs.add(entry); // Add new entry at the beginning
        updatedLogs.addAll(logs); // Add existing entries after
        return new InteractionLog(updatedLogs);
    }

    /**
     * Returns whether this InteractionLog has any log entries.
     *
     * @return true if there are no log entries, false otherwise
     */
    public boolean isEmpty() {
        return logs.isEmpty();
    }

    /**
     * Returns the number of log entries.
     *
     * @return the size of the log entries list
     */
    public int size() {
        return logs.size();
    }

    /**
     * Returns a string representation of this InteractionLog.
     * Each log entry is on a separate line.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "No interaction logs";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < logs.size(); i++) {
            sb.append(logs.get(i).toString());
            if (i < logs.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Checks whether this InteractionLog is equal to another object.
     * Two InteractionLogs are equal if they contain the same log entries in the same order.
     *
     * @param other the object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InteractionLog)) {
            return false;
        }

        InteractionLog otherLog = (InteractionLog) other;
        return logs.equals(otherLog.logs);
    }

    /**
     * Computes the hash code for this InteractionLog.
     *
     * @return the hash code derived from the list of log entries
     */
    @Override
    public int hashCode() {
        return Objects.hash(logs);
    }
}

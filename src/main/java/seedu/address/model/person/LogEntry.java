package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a single log entry documenting an interaction with a contact.
 *
 * This class is immutable: once constructed, the message, type, and timestamp cannot change.
 * The {@code message} field is never {@code null}. The {@code type} field may be an empty string
 * if no interaction type was specified. The {@code timestamp} records when the log entry was created.
 */
public class LogEntry {
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    private final String message;
    private final String type;
    private final LocalDateTime timestamp;

    /**
     * Creates a LogEntry with the given message and type, using the current time as timestamp.
     *
     * @param message the log message; must not be null
     * @param type the type of interaction (e.g., "call", "meeting", "email"); can be empty string
     */
    public LogEntry(String message, String type) {
        this(message, type, LocalDateTime.now());
    }

    /**
     * Creates a LogEntry with the given message, type, and timestamp.
     * This constructor is primarily used for deserialization from storage.
     *
     * @param message the log message; must not be null
     * @param type the type of interaction; can be empty string
     * @param timestamp the time when this log entry was created; must not be null
     */
    public LogEntry(String message, String type, LocalDateTime timestamp) {
        assert message != null : "LogEntry message should not be null";
        assert type != null : "LogEntry type should not be null";
        assert timestamp != null : "LogEntry timestamp should not be null";

        this.message = message;
        this.type = type;
        this.timestamp = timestamp;
    }

    /**
     * Returns the log message.
     *
     * @return the message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the interaction type.
     *
     * @return the type string (may be empty)
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the timestamp when this log entry was created.
     *
     * @return the LocalDateTime timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns whether this log entry has a specified type.
     *
     * @return true if type is not empty, false otherwise
     */
    public boolean hasType() {
        return !type.isEmpty();
    }

    /**
     * Returns a formatted string representation suitable for display.
     * Format: "[dd MMM yyyy HH:mm] (type) message" or "[dd MMM yyyy HH:mm] message" if no type.
     *
     * @return the formatted display string
     */
    @Override
    public String toString() {
        String formattedTime = timestamp.format(DISPLAY_FORMATTER);
        if (hasType()) {
            return String.format("[%s] (%s) %s", formattedTime, type, message);
        } else {
            return String.format("[%s] %s", formattedTime, message);
        }
    }

    /**
     * Checks whether this LogEntry is equal to another object.
     * Two LogEntries are equal if they have the same message, type, and timestamp.
     *
     * @param other the object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LogEntry)) {
            return false;
        }

        LogEntry otherEntry = (LogEntry) other;
        return message.equals(otherEntry.message)
                && type.equals(otherEntry.type)
                && timestamp.equals(otherEntry.timestamp);
    }

    /**
     * Computes the hash code for this LogEntry.
     *
     * @return the hash code derived from message, type, and timestamp
     */
    @Override
    public int hashCode() {
        return Objects.hash(message, type, timestamp);
    }
}

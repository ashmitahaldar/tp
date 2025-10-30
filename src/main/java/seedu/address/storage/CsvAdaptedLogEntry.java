package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.LogEntry;

/**
 * Jackson-friendly version of {@link LogEntry}.
 */
public class CsvAdaptedLogEntry {
    private final String message;
    private final String type;
    private final String timestamp;

    /**
     * Constructs a {@code CsvAdaptedLogEntry} with the given log entry details.
     */
    public CsvAdaptedLogEntry(String message,
                              String type,
                              String timestamp) {
        this.message = message;
        this.type = type;
        this.timestamp = timestamp;
    }

    /**
     * Converts a given {@code LogEntry} into this class for Jackson use.
     */
    public CsvAdaptedLogEntry(LogEntry source) {
        message = source.getMessage();
        type = source.getType();
        timestamp = source.getTimestamp().toString();
    }

    /**
     * Converts this adapted log entry object into the model's {@code LogEntry} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted log entry.
     */
    public LogEntry toModelType() throws IllegalValueException {
        if (message == null) {
            throw new IllegalValueException("Log entry message cannot be null");
        }

        if (type == null) {
            throw new IllegalValueException("Log entry type cannot be null");
        }

        if (timestamp == null) {
            throw new IllegalValueException("Log entry timestamp cannot be null");
        }

        LocalDateTime parsedTimestamp;
        try {
            parsedTimestamp = LocalDateTime.parse(timestamp);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid timestamp format: " + timestamp);
        }

        return new LogEntry(message, type, parsedTimestamp);
    }
}

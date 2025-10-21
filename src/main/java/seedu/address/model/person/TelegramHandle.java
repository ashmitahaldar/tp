package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's telegram handle in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHandle(String)}
 */
public class TelegramHandle {


    public static final String MESSAGE_CONSTRAINTS = "A telegram handle should be between 5 and 32 characters and"
            + "start with a letter, are you sure you entered it correctly?";

    public static final String VALIDATION_REGEX = "^@?[a-zA-Z][a-zA-Z0-9_]{4,31}$";
    public final String value;
    public final boolean isValid;

    /**
     * Constructs a {@code TelegramHandle}.
     *
     * @param handle A valid Telegram handle.
     */
    public TelegramHandle(String handle) {
        requireNonNull(handle);
        if (handle.isEmpty()) {
            value = handle;
            isValid = false;
            return;
        }
        checkArgument(isValidHandle(handle), MESSAGE_CONSTRAINTS);
        value = handle;
        isValid = true;
    }

    /**
     * Returns true if a given string is a valid Telegram handle.
     */
    public static boolean isValidHandle(String test) {
        return test.isEmpty() || test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TelegramHandle)) {
            return false;
        }

        TelegramHandle otherHandle = (TelegramHandle) other;
        return value.equals(otherHandle.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

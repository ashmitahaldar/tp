package seedu.address.model.person;

/**
 * Represents a freeform note attached to a person in the address book.
 *
 * <p>This class is immutable: once constructed, the note text stored in {@code value} does not change.
 * An empty string denotes the absence of a note. The {@code value} field is never {@code null}.
 */
public class Note {
    public final String value;

    /**
     * Creates an empty Note.
     */
    public Note() {
        value = "";
    }

    /**
     * Creates a Note with the given remark.
     *
     * @param note the text of the note; if empty, the note will be stored as an empty string
     */
    public Note(String note) {
        assert note != null : "Note constructor should not be called with null";
        if (note == null) {
            value = "";
        } else {
            value = note;
        }
    }

    /**
     * Returns the string representation of this Note.
     *
     * @return the note text
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks whether this Note is equal to another object.
     * Two Notes are equal if they are the same object or if the other object is a Note with the same value.
     *
     * @param other the object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short-circuit if same object
                || (other instanceof Note) // instanceof handles nulls
                && value.equals(((Note) other).value); // state check
    }

    /**
     * Computes the hash code for this Note.
     *
     * @return the hash code derived from the note value
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

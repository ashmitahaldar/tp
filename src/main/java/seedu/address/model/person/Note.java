package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

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
        if (note.isEmpty()) {
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
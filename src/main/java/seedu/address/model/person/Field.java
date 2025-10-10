package seedu.address.model.person;

/**
 * Represents a Field in the address book.
 * Guarantees: immutable; and is in a specified state
 */
public class Field {
    /**
     * Represents the different type of Fields, including the invalid state.
     */
    public enum FieldValue {
        NAME,
        PHONE,
        EMAIL,
        ADDRESS,
        INVALID
    }

    public static final String MESSAGE_CONSTRAINTS = "Field should be one of (name, phone, email, address)";

    public final FieldValue value;

    /**
     * Constructs the field from a specified string.
     * @param field The input field string
     */
    public Field(String field) {
        if (field.equals("name")) {
            value = FieldValue.NAME;
        } else if (field.equals("phone")) {
            value = FieldValue.PHONE;
        } else if (field.equals("email")) {
            value = FieldValue.EMAIL;
        } else if (field.equals("address")) {
            value = FieldValue.ADDRESS;
        } else {
            value = FieldValue.INVALID;
        }
    }

    public boolean isInvalid() {
        return value == FieldValue.INVALID;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Field)) {
            return false;
        }

        return value == ((Field) other).value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

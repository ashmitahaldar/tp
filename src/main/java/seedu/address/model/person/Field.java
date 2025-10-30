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
        TELEGRAM,
        ADDRESS,
        INVALID
    }

    public static final String MESSAGE_CONSTRAINTS = "Field should be one of (name, phone, email, telegram, address)";

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
        } else if (field.equals("telegram")) {
            value = FieldValue.TELEGRAM;
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
    public String toString() {
        switch (value) {
        case NAME:
            return "name";
        case PHONE:
            return "phone";
        case EMAIL:
            return "email";
        case TELEGRAM:
            return "telegram";
        case ADDRESS:
            return "address";
        default:
            return "[invalid field]";
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

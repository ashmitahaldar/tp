package seedu.address.model.person;

/**
 * Represents a Field in the address book.
 * Guarantees: immutable; and is in a specfied state
 */
public class Order {
    public enum OrderValue {
        ASC,
        DESC,
        INVALID
    }

    public static final String MESSAGE_CONSTRAINTS = "Field should be one of (ASC, DESC)";

    public final OrderValue value;

    public Order(String order) {
        if (order.equals("asc")) {
            value = OrderValue.ASC;
        } else if (order.equals("desc")) {
            value = OrderValue.DESC;
        } else {
            value = OrderValue.INVALID;
        }
    }

    public boolean isInvalid() {
        return value == OrderValue.INVALID;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Order)) {
            return false;
        }

        return value == ((Order) other).value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

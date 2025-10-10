package seedu.address.model.person;

/**
 * Represents an Order in the address book.
 * Guarantees: immutable; and is in a specified state
 */
public class Order {
    /**
     * Represents the different type of Orders, including the invalid state.
     */
    public enum OrderValue {
        ASC,
        DESC,
        INVALID
    }

    public static final String MESSAGE_CONSTRAINTS = "Field should be one of (ASC, DESC)";

    public final OrderValue value;

    /**
     * Constructs the Order from a specified string.
     * @param order The input order string
     */
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
    public String toString() {
        switch (value) {
        case ASC:
            return "ascending";
        case DESC:
            return "descending";
        default:
            return "[invalid field]";
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

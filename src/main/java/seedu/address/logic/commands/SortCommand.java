package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Field;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user in a specified sorted order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons by the specified field "
            + "in the specified order.\n"
            + "Parameters: "
            + "[" + PREFIX_FIELD + "FIELD] "
            + "[" + PREFIX_ORDER + "ORDER]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FIELD + "name "
            + PREFIX_ORDER + "asc";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    private final Field field;
    private final Order order;

    /**
     * Constructs a SortCommand from a specified field and order.
     * @param field Field to use for sorting
     * @param order Order to use for sorting
     */
    public SortCommand(Field field, Order order) {
        this.field = requireNonNull(field);
        this.order = requireNonNull(order);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // don't filter person list
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // build and apply comparator
        Comparator<Person> comparator = buildComparator();
        model.sortFilteredPersonList(comparator);

        return new CommandResult(MESSAGE_SUCCESS + " by " + field.toString() + " in " + order.toString() + " order");
    }

    /**
     * Constructs the appropriate Comparator given a specific field and order.
     * @return The constructed Comparator.
     */
    private Comparator<Person> buildComparator() {
        Comparator<Person> base;

        // sort by specific field
        switch(field.value) {
        case NAME:
            base = Comparator.comparing(p -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER);
            break;
        case PHONE:
            base = Comparator.comparing(p -> p.getPhone().value, String.CASE_INSENSITIVE_ORDER);
            break;
        case EMAIL:
            base = Comparator.comparing(p -> p.getEmail().value, String.CASE_INSENSITIVE_ORDER);
            break;
        case ADDRESS:
            base = Comparator.comparing(p -> p.getAddress().value, String.CASE_INSENSITIVE_ORDER);
            break;
        default:
            // should not ever reach here
            base = Comparator.comparing(p -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        }

        // reverse order if necessary
        if (order.value == Order.OrderValue.DESC) {
            base = base.reversed();
        }

        return base;
    }
}

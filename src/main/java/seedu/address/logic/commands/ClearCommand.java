package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all entries from the address book.\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD
            + "Key in \"clear confirm\" to confirm deleting data.";

    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_CONFIRM_CLEAR = "Enter 'clear confirm' to confirm deleting all contacts";


    public final boolean isConfirmed;


    public ClearCommand(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (isConfirmed) {
            model.saveAddressBookState();
            model.setAddressBook(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_CONFIRM_CLEAR);

    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Redo last action by state of addressbook before undo
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo successful!";
    public static final String MESSAGE_NO_HISTORY = "Nothing to Redo";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (!model.canRedoAddressBook()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }
        model.redoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

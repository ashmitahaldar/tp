package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Undo last action by loading previous state of addressbook
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_NO_HISTORY = "Nothing to Undo";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (!model.canUndoAddressBook()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }
        model.undoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

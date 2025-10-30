package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.InteractionLog;
import seedu.address.model.person.LogEntry;
import seedu.address.model.person.Person;

/**
 * Adds a log entry to document an interaction with an existing contact in the address book.
 */
public class LogCommand extends Command {
    public static final String COMMAND_WORD = "log";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Logs an interaction with the contact identified "
            + "by the index number used in the last person listing. "
            + "The log entry will be added to the interaction history.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "m/MESSAGE [t/TYPE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "m/Called about catering quote, they'll email by Friday t/call";
    public static final String MESSAGE_SUCCESS = "Added log entry to Person: %1$s";

    private final Index index;
    private final String message;
    private final String type;

    /**
     * Constructs a LogCommand to add a log entry to a person's interaction history.
     *
     * @param index the index of the person in the filtered person list
     * @param message the log message describing the interaction
     * @param type the type of interaction (e.g., "call", "meeting", "email"). It can be an empty string.
     */
    public LogCommand(Index index, String message, String type) {
        requireAllNonNull(index, message, type);

        this.index = index;
        this.message = message;
        this.type = type;
    }

    /**
     * Executes the command and adds a log entry to the person's interaction history.
     *
     * @param model the model which the command should operate on
     * @return the result of command execution containing a success message
     * @throws CommandException if the provided index is invalid
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null : "model must not be null";

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Create new log entry
        LogEntry newEntry = new LogEntry(message, type);

        // Add log entry to existing logs
        InteractionLog updatedLogs = personToEdit.getLogs().addLogEntry(newEntry);

        // Create new person with updated logs
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getTelegramHandle(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getNote(),
                updatedLogs,
                personToEdit.isPinned()
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Checks whether this LogCommand is equal to another object.
     * Two LogCommands are equal if they have the same index, message, and type.
     *
     * @param other the object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LogCommand)) {
            return false;
        }

        LogCommand otherCommand = (LogCommand) other;
        return index.equals(otherCommand.index)
                && message.equals(otherCommand.message)
                && type.equals(otherCommand.type);
    }
}

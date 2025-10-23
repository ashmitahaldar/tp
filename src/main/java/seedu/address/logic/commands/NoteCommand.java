package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Changes the note of an existing person in the address book.
 */
public class NoteCommand extends Command {
    public static final String COMMAND_WORD = "note";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the note of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing note will be overwritten by the input."
            + "The note will be visible on the person info panel. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "note/ [NOTE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "note/ Likes to swim.";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Note: %2$s";
    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added note to Person: %1$s";
    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Removed note from Person: %1$s";

    private final Index index;
    private final Note note;
    private final boolean isInitiating;

    /**
     * Constructs a NoteCommand with an index and a Note object.
     *
     * @param index the index of the person to edit
     * @param note the Note to set for the person
     */
    public NoteCommand(Index index, Note note) {
        requireAllNonNull(index, note);

        this.index = index;
        this.note = note;
        this.isInitiating = false;
    }

    /**
     * Constructs an initiating NoteCommand used when the user types just the command word.
     * This variant does not operate on a specific index and is used to trigger interactive behavior.
     */
    public NoteCommand() {
        this.index = null;
        this.note = new Note("");
        this.isInitiating = true;

        assert this.index == null && this.note.value.isEmpty() && this.isInitiating
                : "initiating NoteCommand must have null index and empty note";

    }

    /**
     * Constructs a NoteCommand with an index and a raw note string.
     *
     * @param index the index of the person to edit
     * @param note the note text to set for the person
     */
    public NoteCommand(Index index, String note) {
        requireAllNonNull(index, note);

        this.index = index;
        this.note = new Note(note);
        this.isInitiating = false;
    }

    /**
     * Executes the command and updates the model with the edited person.
     *
     * @param model the model which the command should operate on
     * @return the result of command execution containing a success message
     * @throws CommandException if the provided index is invalid
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null : "model must not be null";
        if (isInitiating) {
            // Initiating mode: no immediate model changes. Return a neutral result that the UI can handle.
            return new CommandResult("");
        }
        List<Person> lastShownList = model.getFilteredPersonList();

        assert lastShownList != null : "filtered person list should not be null";

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getTelegramHandle(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), note);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the note was added or removed for the edited person.
     *
     * @param personToEdit the person after editing
     * @return formatted success message
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !note.value.isEmpty() ? MESSAGE_ADD_NOTE_SUCCESS : MESSAGE_DELETE_NOTE_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }

    /**
     * Checks whether this NoteCommand is equal to another object.
     * Two NoteCommands are equal if they have the same index and note.
     *
     * @param other the object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        NoteCommand e = (NoteCommand) other;
        if (this.isInitiating || e.isInitiating) {
            return this.isInitiating == e.isInitiating;
        }
        return index.equals(e.index)
                && note.equals(e.note);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.nio.file.Path;
import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Adds a person to the address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a specified .json file. "
            + "Parameters: "
            + PREFIX_FILE + "FILE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "data/addressbook.json";

    public static final String MESSAGE_SUCCESS = "Save data imported: %1$s";
    public static final String MESSAGE_INVALID_FILE = "This file cannot be found, "
            + "check if the relative path is correct";

    private Path filepath;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public ImportCommand(Path filepath) {
        requireNonNull(filepath);
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setAddressBookFilePath(filepath);

        AddressBookStorage newAddressStorage = new JsonAddressBookStorage(filepath);
        try {
            ReadOnlyAddressBook newAddressBook = newAddressStorage.readAddressBook().get();
            model.setAddressBook(newAddressBook);
        } catch (DataLoadingException | NoSuchElementException e) {
            throw new CommandException(ImportCommand.MESSAGE_INVALID_FILE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, filepath.toString().replace('\\', '/')));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherAddCommand = (ImportCommand) other;
        return filepath.equals(otherAddCommand.filepath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filepath", filepath.toString().replace('\\', '/'))
                .toString();
    }
}

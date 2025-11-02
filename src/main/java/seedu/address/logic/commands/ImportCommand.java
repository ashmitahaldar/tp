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
import seedu.address.storage.CsvAddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Imports a save file to the address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a specified .json or .csv file. "
            + "\nParameters: "
            + PREFIX_FILE + "FILEPATH \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "data/linkedup.json";

    public static final String MESSAGE_SUCCESS = "Save data imported: %1$s";
    public static final String MESSAGE_PARSE_ERROR = "Error occurred when parsing file."
            + "Check if the file is not corrupted.";
    public static final String MESSAGE_INVALID_FILE = "This file cannot be found, "
            + "check if the relative path is correct.";
    public static final String MESSAGE_INVALID_FILETYPE = "Invalid filetype specified, "
            + "filetype must be .json or .csv";

    private Path filepath;

    /**
     * Creates an ImportCommand to import the specified save file
     */
    public ImportCommand(Path filepath) {
        requireNonNull(filepath);
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // throw exception if file is not a csv or json file
        if (!this.filepath.toString().endsWith(".csv") && !this.filepath.toString().endsWith(".json")) {
            throw new CommandException(MESSAGE_INVALID_FILETYPE);
        }

        if (this.filepath.toString().endsWith(".csv")) {
            model.saveAddressBookState();
            return importCsv(model);
        }

        model.saveAddressBookState();
        return importJson(model);
    }

    /**
     * Imports a .json file
     *
     * @param model {@code Model} to which the file should be imported to
     * @return feedback message of the operation result for display
     * @throws CommandException if an invalid file was provided
     */
    public CommandResult importJson(Model model) throws CommandException {
        AddressBookStorage newAddressStorage = new JsonAddressBookStorage(filepath);
        try {
            ReadOnlyAddressBook newAddressBook = newAddressStorage.readAddressBook().get();
            model.setAddressBook(newAddressBook);
            model.setAddressBookFilePath(filepath);
        } catch (DataLoadingException | NoSuchElementException e) {
            throw new CommandException(ImportCommand.MESSAGE_INVALID_FILE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, filepath.toString().replace('\\',
                '/'), false, false, true));
    }

    /**
     * Imports a .csv file
     *
     * @param model {@code Model} to which the file should be imported to
     * @return feedback message of the operation result for display
     * @throws CommandException if an invalid file was provided
     */
    public CommandResult importCsv(Model model) throws CommandException {
        AddressBookStorage newAddressStorage = new CsvAddressBookStorage(filepath);
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

        ImportCommand otherImportCommand = (ImportCommand) other;
        return filepath.equals(otherImportCommand.filepath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filepath", filepath.toString().replace('\\', '/'))
                .toString();
    }
}

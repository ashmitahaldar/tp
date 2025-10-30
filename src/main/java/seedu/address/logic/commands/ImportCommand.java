package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBuilder;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Imports a save file to the address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a specified .json or .csv file. "
            + "\nParameters: "
            + PREFIX_FILE + "FILE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "data/addressbook.json";

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

    /**
     * Imports a .csv file
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the import for display
     * @throws CommandException if an invalid filepath was specified
     */
    private CommandResult importCsv(Model model) throws CommandException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath.toFile()));
            AddressBook addressBook = new AddressBook();
            boolean isParsing = true;
            while (isParsing) {
                String csvLine = reader.readLine();
                if (csvLine == null) {
                    isParsing = false;
                } else {
                    Person newPerson = createNewPerson(csvLine);
                    addressBook.addPerson(newPerson);
                }
            }
            model.setAddressBook(addressBook);
            model.saveAddressBookState();
        } catch (FileNotFoundException e) {
            throw new CommandException(ImportCommand.MESSAGE_INVALID_FILE);
        } catch (ParseException | IOException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, filepath.toString().replace('\\', '/')));
    }

    /**
     * Creates a person based on the input comma-separated string.
     *
     * @param inputString A comma-separated string of text.
     * @return the person specified in the string.
     */
    private Person createNewPerson(String inputString) throws ParseException {
        requireNonNull(inputString);

        PersonBuilder builder = new PersonBuilder();

        String[] fieldArray = inputString.split(",");
        for (String s : fieldArray) {
            addFieldToPerson(builder, s);
        }

        return builder.buildPerson();
    }

    private void addFieldToPerson(PersonBuilder builder, String s) throws ParseException {
        switch (s.substring(0, s.indexOf(":"))) {
        case "name":
            builder.setName(s.substring(s.indexOf(":") + 1));
            break;
        case "phone":
            builder.setPhone(s.substring(s.indexOf(":") + 1));
            break;
        case "address":
            builder.setAddress(s.substring(s.indexOf(":") + 1));
            break;
        case "email":
            builder.setEmail(s.substring(s.indexOf(":") + 1));
            break;
        case "telegram":
            if (s.length() >= s.indexOf(":") + 2) {
                builder.setTelegramHandle(s.substring(s.indexOf(":") + 2));
            }
            break;
        case "tag":
            builder.setTag(s.substring(s.indexOf(":") + 2, s.length() - 1));
            break;
        case "note":
            builder.setNote(s.substring(s.indexOf(":") + 1));
            break;
        case "isPinned":
            builder.setPinned(s.substring(s.indexOf(":") + 1));
            break;
        case "log":
            String logTime = s.substring(s.indexOf('[' + 1), s.indexOf(']'));
            String logType = s.substring(s.indexOf('(' + 1), s.indexOf(')'));
            String logData = s.substring(s.indexOf(')') + 1);
            builder.setLog(logTime, logType, logData);
            break;
        default:
            throw new ParseException("Unknown tag encountered");
        }
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

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Exports the save file as a .csv file
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the address book to a .csv file.\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Save data exported to %1$s";

    public static final String INVALID_EXPORT_FORMAT = "Target file should be .csv or .json!";

    private PrintWriter printWriter;
    private Path exportName;
    private Set<Tag> tags;

    /**
     * Initializes the export command object.
     *
     * @param exportName Relative path of the export file from the working directory.
     * @param tags Set of tags that all exported contacts should have.
     */
    public ExportCommand(Path exportName, Set<Tag> tags) {
        requireNonNull(exportName);
        assert(exportName.toString().endsWith(".json") || exportName.toString().endsWith(".csv"));

        this.exportName = exportName;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        initializeExport();

        ObservableList<Person> personList = getPersonList(model);

        Stream<Person> filteredPersonStream = personList.stream().filter(this::hasTags);

        if (exportName.toString().endsWith(".csv")) {
            this.saveAsCsv(filteredPersonStream);
        } else {
            this.saveAsJson(filteredPersonStream);
        }

        printWriter.close();

        return new CommandResult(String.format(MESSAGE_SUCCESS, exportName));
    }

    /**
     * Initializes export variables.
     * @throws CommandException if an error occurred while initializing the export file
     */
    private void initializeExport() throws CommandException {
        try {
            File outputFile = exportName.toFile();
            // if target file has a parent directory
            if (!(outputFile.getParentFile() == null)) {
                // create all missing directories on path specified
                outputFile.getParentFile().mkdirs();
            }
            outputFile.createNewFile();
            printWriter = new PrintWriter(outputFile);
        } catch (IOException e) {
            throw new CommandException("An error occurred while generating the output file :" + e.getMessage());
        }
    }

    /**
     * Retrieves the saved list of people in the address book.
     * @param model the model used for the instance
     * @return list of people in the address book of the model
     * @throws CommandException if the save file used by the model is not found
     */
    private ObservableList<Person> getPersonList(Model model) throws CommandException {
        Path saveFilePath = model.getAddressBookFilePath();

        AddressBookStorage newAddressStorage = new JsonAddressBookStorage(saveFilePath);
        try {
            return newAddressStorage.readAddressBook().get().getPersonList();
        } catch (DataLoadingException | NoSuchElementException e) {
            throw new CommandException("Existing save file not found, please try again.");
        }
    }

    /**
     * Writes a stream of people into the .csv file.
     * @param people stream of people to be saved in the .csv file
     */
    private void saveAsCsv(Stream<Person> people) {
        people.forEach((Person person) -> {
            StringBuilder fieldsString = new StringBuilder();
            person.getFields().forEach((String s) -> {
                fieldsString.append(s);
                fieldsString.append(',');
            });
            fieldsString.deleteCharAt(fieldsString.length() - 1); // remove trailing comma
            printWriter.println(fieldsString);
        });
    }

    /**
     * Writes a stream of people into the .json file.
     * @param people stream of people to be saved in the .json file.
     * @throws CommandException if a write error occurs.
     */
    private void saveAsJson(Stream<Person> people) throws CommandException {
        AddressBook addressBook = new AddressBook();
        people.forEach(addressBook::addPerson);

        try {
            new JsonAddressBookStorage(exportName).saveAddressBook(addressBook);
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Checks if a person has all the tags in the tag list
     * @param person Person to be checked
     * @return True if person has all tags
     */
    private boolean hasTags(Person person) {
        return tags.stream().allMatch(person::hasTag);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof ExportCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}

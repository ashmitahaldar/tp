package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Adds a person to the address book.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the addressbook to a .csv file"
            + "\n"
            + "Example: " + COMMAND_WORD;

    public static final String OUTPUT_PATH = "data/addressbook.csv";

    public static final String MESSAGE_SUCCESS = "Save data exported to " + OUTPUT_PATH;

    private PrintWriter printWriter;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        initializeExport();

        ObservableList<Person> personList = getPersonList(model);

        saveAsCsv(personList);

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    private void initializeExport() throws CommandException {
        try {
            File outputFile = new File(OUTPUT_PATH);
            outputFile.createNewFile();
            printWriter = new PrintWriter(outputFile);
        } catch (IOException e) {
            throw new CommandException("An error occurred while generating the output file :" + e.getMessage());
        }
    }

    private ObservableList<Person> getPersonList(Model model) throws CommandException {
        Path saveFilePath = model.getAddressBookFilePath();

        AddressBookStorage newAddressStorage = new JsonAddressBookStorage(saveFilePath);
        try {
            return newAddressStorage.readAddressBook().get().getPersonList();
        } catch (DataLoadingException | NoSuchElementException e) {
            throw new CommandException("Existing save file not found, please try again.");
        }
    }

    private void saveAsCsv(ObservableList<Person> personList) {
        personList.iterator().forEachRemaining((Person person) -> {
            StringBuilder fieldsString = new StringBuilder();
            person.getFields().forEach(fieldsString::append);
            printWriter.println(fieldsString);
        });
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

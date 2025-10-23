package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILEPATH_CSV;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILEPATH_JSON;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;

public class ImportCommandTest {
    private static Path validJsonPath;
    private static Path validCsvPath;
    private static Path invalidPath;

    @BeforeAll
    public static void setup() {
        try {
            validJsonPath = ParserUtil.parsePath(VALID_FILEPATH_JSON);
            validCsvPath = ParserUtil.parsePath(VALID_FILEPATH_CSV);
            invalidPath = ParserUtil.parsePath(INVALID_FILEPATH);
        } catch (ParseException e) {
            System.out.println("Test file missing.\n");
        }
    }

    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ImportCommand(null));
    }

    @Test
    public void execute_pathAcceptedByModel_importJsonSuccessful() throws Exception {
        ModelStub modelStub = new ModelStubAcceptingPath();

        CommandResult commandResult = new ImportCommand(validJsonPath).execute(modelStub);

        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, VALID_FILEPATH_JSON),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_pathAcceptedByModel_importCsvSuccessful() throws Exception {
        ModelStub modelStub = new ModelStubAcceptingPath();

        CommandResult commandResult = new ImportCommand(validCsvPath).execute(modelStub);

        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, VALID_FILEPATH_CSV),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_pathAcceptedByModel_importFailure() {
        ModelStub modelStub = new ModelStubAcceptingPath();

        assertThrows(CommandException.class, () -> new ImportCommand(invalidPath)
                .execute(modelStub).getFeedbackToUser());
    }

    @Test
    public void toString_json_returnFilePath() {
        ImportCommand importCommand = new ImportCommand(validJsonPath);
        String expected = ImportCommand.class.getCanonicalName() + "{filepath=" + VALID_FILEPATH_JSON + "}";
        assertEquals(expected, importCommand.toString());
    }

    @Test
    public void toString_csv_returnFilePath() {
        ImportCommand importCommand = new ImportCommand(validCsvPath);
        String expected = ImportCommand.class.getCanonicalName() + "{filepath=" + VALID_FILEPATH_CSV + "}";
        assertEquals(expected, importCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredPersonList(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void saveAddressBookState() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        public ObservableList<Person> getSortedPersonList() {
            throw new AssertionError("This method should not be called.");
        };
    }

    /**
     * A Model stub that always returns a valid filepath.
     */
    private class ModelStubAcceptingPath extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            return;
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            return;
        }

        @Override
        public void saveAddressBookState() {
            return;
        }
    }

}

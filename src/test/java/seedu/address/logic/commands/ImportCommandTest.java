package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FILE_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILEPATH_JOHN;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;

public class ImportCommandTest {
    private static Path validPath;
    private static Path invalidPath;
    private static Path invalidFile;

    @BeforeAll
    public static void setup() {
        try {
            validPath = ParserUtil.parsePath(VALID_FILEPATH_JOHN);
            invalidPath = ParserUtil.parsePath(INVALID_FILEPATH);
            invalidFile = ParserUtil.parsePath(INVALID_FILE_TYPE);
        } catch (ParseException e) {
            System.out.println("Test file missing.\n");
        }
    }

    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ImportCommand(null));
    }

    @Test
    public void execute_pathAcceptedByModel_importSuccessful() throws Exception {
        ModelStub modelStub = new ModelStubAcceptingPath();

        CommandResult commandResult = new ImportCommand(validPath).execute(modelStub);

        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, VALID_FILEPATH_JOHN),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_pathAcceptedByModel_importFailure() {
        ModelStub modelStub = new ModelStubAcceptingPath();

        assertThrows(CommandException.class, () -> new ImportCommand(invalidPath)
                .execute(modelStub).getFeedbackToUser());
    }

    @Test
    public void execute_pathAcceptedByModel_invalidFileFailure() {
        ModelStub modelStub = new ModelStubAcceptingPath();

        assertThrows(CommandException.class, () -> new ImportCommand(invalidFile)
                .execute(modelStub).getFeedbackToUser());
    }

    @Test
    public void toStringMethod() {
        ImportCommand importCommand = new ImportCommand(validPath);
        String expected = ImportCommand.class.getCanonicalName() + "{filepath=" + VALID_FILEPATH_JOHN + "}";
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
        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            return;
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            return;
        }
    }

}

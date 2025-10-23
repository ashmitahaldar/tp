package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILEPATH_JSON;
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

public class ExportCommandTest {
    private static Path validPath;
    private static Path invalidPath;

    @BeforeAll
    public static void setup() {
        try {
            validPath = ParserUtil.parsePath(VALID_FILEPATH_JSON);
            invalidPath = ParserUtil.parsePath(INVALID_FILEPATH);
        } catch (ParseException e) {
            System.out.println("Test file missing.\n");
        }
    }

    @Test
    public void execute_pathAcceptedByModel_exportSuccessful() throws Exception {
        ModelStub modelStub = new ModelStubValidPath();

        CommandResult commandResult = new ExportCommand().execute(modelStub);

        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_pathAcceptedByModel_exportFailure() {
        ModelStub modelStub = new ModelStubInvalidPath();

        assertThrows(CommandException.class, () -> new ExportCommand()
                .execute(modelStub).getFeedbackToUser());
    }

    @Test
    public void toStringMethod() {
        ExportCommand exportCommand = new ExportCommand();
        String expected = ExportCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, exportCommand.toString());
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

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        public ObservableList<Person> getSortedPersonList() {
            throw new AssertionError("This method should not be called.");
        };
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubValidPath extends ModelStub {
        @Override
        public Path getAddressBookFilePath() {
            return validPath;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubInvalidPath extends ModelStub {
        @Override
        public Path getAddressBookFilePath() {
            return invalidPath;
        }
    }

}

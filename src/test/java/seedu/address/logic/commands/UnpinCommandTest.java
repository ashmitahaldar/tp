package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnpinCommand}.
 */
public class UnpinCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // First pin the person
        Person pinnedPerson = new PersonBuilder(personToUnpin).withPinned(true).build();
        model.setPerson(personToUnpin, pinnedPerson);

        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_PERSON);

        Person unpinnedPerson = new PersonBuilder(pinnedPerson).withPinned(false).build();

        String expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS,
                Messages.format(unpinnedPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(pinnedPerson, unpinnedPerson);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnpinCommand unpinCommand = new UnpinCommand(outOfBoundIndex);

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_notPinnedPerson_throwsCommandException() {
        Person personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(unpinCommand, model, UnpinCommand.MESSAGE_PERSON_NOT_PINNED);
    }

    @Test
    public void equals() {
        UnpinCommand unpinFirstCommand = new UnpinCommand(INDEX_FIRST_PERSON);
        UnpinCommand unpinSecondCommand = new UnpinCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unpinFirstCommand.equals(unpinFirstCommand));

        // same values -> returns true
        UnpinCommand unpinFirstCommandCopy = new UnpinCommand(INDEX_FIRST_PERSON);
        assertTrue(unpinFirstCommand.equals(unpinFirstCommandCopy));

        // different types -> returns false
        assertFalse(unpinFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unpinFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnpinCommand unpinCommand = new UnpinCommand(targetIndex);
        String expected = UnpinCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unpinCommand.toString());
    }
}

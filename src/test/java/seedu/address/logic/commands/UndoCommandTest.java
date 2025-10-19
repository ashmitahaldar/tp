package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;

public class UndoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_undoAdd() throws Exception {
        int initialSize = model.getFilteredPersonList().size();
        new AddCommand(new PersonBuilder().withName("Alice").build()).execute(model);
        CommandResult result = new UndoCommand().execute(model);
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(initialSize, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_undoEdit() throws Exception {
        new EditCommand(Index.fromZeroBased(0), new EditCommand.EditPersonDescriptor())
                .execute(model);
        CommandResult result = new UndoCommand().execute(model);
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_undoPin() throws Exception {
        new PinCommand(Index.fromZeroBased(0)).execute(model);
        assertEquals(true, model.getFilteredPersonList().get(0).isPinned());

        new UndoCommand().execute(model);
        assertEquals(false, model.getFilteredPersonList().get(0).isPinned());
    }

    @Test
    public void execute_doubleUndo() throws Exception {
        int initialSize = model.getFilteredPersonList().size();
        new AddCommand(new PersonBuilder().withName("Alice").build()).execute(model);
        new AddCommand(new PersonBuilder().withName("Bob").build()).execute(model);

        new UndoCommand().execute(model);
        assertEquals(initialSize + 1, model.getFilteredPersonList().size());
        new UndoCommand().execute(model);
        assertEquals(initialSize, model.getFilteredPersonList().size());
    }
}

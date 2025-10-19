package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class RedoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addUndoRedo() throws Exception {
        int initialSize = model.getFilteredPersonList().size();
        new AddCommand(new PersonBuilder().withName("Alice").build()).execute(model);

        new UndoCommand().execute(model);
        assertEquals(initialSize, model.getFilteredPersonList().size());

        CommandResult result = new RedoCommand().execute(model);
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(initialSize + 1, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_editPinUndoUndoRedoRedo() throws Exception {
        EditCommand.EditPersonDescriptor descriptor = new EditCommand.EditPersonDescriptor();
        descriptor.setName(new PersonBuilder().withName("Edited").build().getName());

        new EditCommand(Index.fromZeroBased(0), descriptor).execute(model);
        new PinCommand(Index.fromZeroBased(0)).execute(model);
        assertEquals(true, model.getFilteredPersonList().get(0).isPinned());

        new UndoCommand().execute(model);
        assertEquals(false, model.getFilteredPersonList().get(0).isPinned());

        new UndoCommand().execute(model);

        new RedoCommand().execute(model);

        CommandResult result = new RedoCommand().execute(model);
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(true, model.getFilteredPersonList().get(0).isPinned());
    }
}

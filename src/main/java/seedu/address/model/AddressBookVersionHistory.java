package seedu.address.model;

import java.util.Stack;

/**
 * Manages version history of AddressBook states for undo/redo functionality.
 * Maintains two stacks: one for past states (undo) and one for future states (redo).
 */
public class AddressBookVersionHistory {

    private static final int MAX_HISTORY_SIZE = 50;
    private final Stack<ReadOnlyAddressBook> undoStack;
    private final Stack<ReadOnlyAddressBook> redoStack;

    /**
     * Creates an empty version history.
     */
    public AddressBookVersionHistory() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Saves the current state before making changes.
     *
     * @param currentState The current state to save.
     */
    public void saveState(ReadOnlyAddressBook currentState) {
        undoStack.push(new AddressBook(currentState));
        redoStack.clear();
        if (undoStack.size() > MAX_HISTORY_SIZE) {
            undoStack.remove(0);
        }
    }

    /**
     * Checks if undo operation is available.
     *
     * @return true if there are states to undo to.
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * Checks if redo operation is available.
     *
     * @return true if there are states to undo to.
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    /**
     * Performs undo operation by retrieving the previous state.
     *
     * @param currentState The current state to save for redo.
     * @return The previous state from undo stack.
     */
    public ReadOnlyAddressBook undo(ReadOnlyAddressBook currentState) {
        redoStack.push(new AddressBook(currentState));
        return undoStack.pop();
    }

    /**
     * Performs redo operation by retrieving state in redo stack.
     *
     * @param currentState The current state to save for redo.
     * @return The previous state from redo stack.
     */
    public ReadOnlyAddressBook redo(ReadOnlyAddressBook currentState) {
        undoStack.push(new AddressBook(currentState));
        return redoStack.pop();
    }

}

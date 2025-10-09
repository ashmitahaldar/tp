package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final boolean isInlineEdit;
    private final String editData;

    /**
     * Constructs a {@code CommandResult} with the specified fields with inline edit disabled.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.isInlineEdit = false;
        this.editData = "";
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the fields with inline edit and selected person as input,
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         boolean isInlineEdit, String editData) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.isInlineEdit = isInlineEdit;
        this.editData = editData;
    }

    private static String formatPersonForEdit(Person person, int index) {
        StringBuilder tagsString = new StringBuilder();
        for (Tag tag : person.getTags()) {
            tagsString.append(" t/").append(tag.tagName);
        }

        return String.format("edit " + index + " n/%s p/%s e/%s a/%s%s",
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                tagsString.toString());
    }

    /** The method for creating inline edit without person object. */
    public static CommandResult forInlineEdit(Person person, int index) {
        String editData = formatPersonForEdit(person, index);
        return new CommandResult("", false, false, true, editData);
    }

    public boolean isInlineEdit() {
        return isInlineEdit;
    }

    public String getEditData() {
        return editData;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}

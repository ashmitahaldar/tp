package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

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
    private final boolean isExit;

    /** The application should display statistics. */
    private final boolean isShowingStats;


    private final boolean isInlineEdit;
    private final String editData;

    private final Optional<Person> personToSelect;

    /**
     * Constructs a {@code CommandResult} with the specified fields with inline edit disabled, stats disabled.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.isExit = exit;
        this.isInlineEdit = false;
        this.editData = "";
        this.isShowingStats = false;
        this.personToSelect = Optional.empty();
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields with inline edit disabled, stats enabled.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean isShowingStats) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.isExit = exit;
        this.isShowingStats = isShowingStats;
        this.isInlineEdit = false;
        this.editData = "";
        this.personToSelect = Optional.empty();
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
        this.isExit = exit;
        this.isInlineEdit = isInlineEdit;
        this.editData = editData;
        this.isShowingStats = false;
        this.personToSelect = Optional.empty();
    }

    /**
     * Constructs a {@code CommandResult} with a specficic select person.
     */
    public CommandResult(String feedbackToUser, Person person) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.isExit = false;
        this.isShowingStats = false;
        this.isInlineEdit = false;
        this.editData = "";
        this.personToSelect = Optional.ofNullable(person);
    }

    private static String formatPersonForEdit(Person person, int index) {
        StringBuilder tagsString = new StringBuilder();
        for (Tag tag : person.getTags()) {
            tagsString.append(" t/").append(tag.tagName);
        }

        return String.format("edit " + index + " n/%s p/%s tele/%s e/%s a/%s%s",
                person.getName(),
                person.getPhone(),
                person.getTelegramHandle(),
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
        return isExit;
    }

    public boolean isShowStats() {
        return isShowingStats;
    }

    public Optional<Person> getPersonToSelect() {
        return personToSelect;
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
                && isExit == otherCommandResult.isExit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, isExit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", isExit)
                .toString();
    }

}

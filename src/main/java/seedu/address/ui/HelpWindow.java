package seedu.address.ui;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.PinCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnpinCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s1-cs2103t-w09-1.github.io/tp/UserGuide.html";
    public static final String USERGUIDE_LABEL = "Refer to the full user guide here: " + USERGUIDE_URL;
    public static final String HELP_MESSAGE = buildHelpMessage();

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private TextArea helpMessage;

    @FXML
    private Label userGuideLabel;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        userGuideLabel.setText(USERGUIDE_LABEL);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Builds the help message containing usage information for all commands.
     *
     * @return The formatted help message.
     */
    private static String buildHelpMessage() {
        StringBuilder sb = new StringBuilder();

        // List of all command usage messages
        List<String> commandUsages = Arrays.asList(
            AddCommand.MESSAGE_USAGE,
            ClearCommand.MESSAGE_USAGE,
            DeleteCommand.MESSAGE_USAGE,
            EditCommand.MESSAGE_USAGE,
            NoteCommand.MESSAGE_USAGE,
            ExitCommand.MESSAGE_USAGE,
            ExportCommand.MESSAGE_USAGE,
            FilterCommand.MESSAGE_USAGE,
            FindCommand.MESSAGE_USAGE,
            HelpCommand.MESSAGE_USAGE,
            ImportCommand.MESSAGE_USAGE,
            ListCommand.MESSAGE_USAGE,
            PinCommand.MESSAGE_USAGE,
            UnpinCommand.MESSAGE_USAGE,
            SortCommand.MESSAGE_USAGE,
            UndoCommand.MESSAGE_USAGE,
            RedoCommand.MESSAGE_USAGE,
            NoteCommand.MESSAGE_USAGE,
            StatsCommand.MESSAGE_USAGE
        );

        for (int i = 0; i < commandUsages.size(); i++) {
            sb.append(String.format("%d. %s", i + 1, commandUsages.get(i))).append("\n\n");
        }

        return sb.toString();
    }
}

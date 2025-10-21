package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel that displays detailed information of a selected {@code Person}.
 */
public class PersonInfoPanel extends UiPart<Region> {
    private static final String FXML = "PersonInfoPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private VBox infoBox;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Hyperlink telegram;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    private String telegramHandle;

    /**
     * Creates a {@code PersonInfoPanel} and initializes with placeholder text.
     */
    public PersonInfoPanel() {
        super(FXML);
        displayPerson(null);
    }

    /**
     * Displays the details of the given {@code Person} in the panel.
     * If {@code person} is {@code null}, shows placeholder text.
     *
     * @param person The person whose details to display, or {@code null} for default text.
     */
    public void displayPerson(Person person) {
        if (person == null) {
            name.setText("No person selected");
            phone.setText("-");
            telegram.setText("-");
            address.setText("-");
            email.setText("-");
            tags.getChildren().clear();
        } else {
            name.setText(person.getName().fullName);
            phone.setText(person.getPhone().value);
            boolean validTelegram = person.getTelegramHandle().isValid;
            telegramHandle = person.getTelegramHandle().value;
            if (telegramHandle.isEmpty()) {
                telegramHandle = "-";
            }
            telegram.setText(telegramHandle);
            telegram.setDisable(!validTelegram);
            address.setText(person.getAddress().value);
            email.setText(person.getEmail().value);
            tags.getChildren().clear();
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        Label tagLabel = new Label(tag.tagName);
                        tagLabel.getStyleClass().add("label");
                        tags.getChildren().add(tagLabel);
                    });
        }
    }

    private void openUri(String uri) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(uri));
            }
        } catch (Exception e) {
            logger.warning("Could not open link: " + uri + " (" + e.getMessage() + ")");
        }
    }

    @FXML
    private void onTelegramClick() {
        String handle = telegram.getText();
        if (!telegramHandle.isEmpty()) {
            String path = telegramHandle.trim().replaceFirst("^@", "");
            openUri("https://t.me/" + path);
        }
    }
}

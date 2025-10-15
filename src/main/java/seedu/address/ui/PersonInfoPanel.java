package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

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
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PersonInfoPanel() {
        super(FXML);
        displayPerson(null);
    }

    public void displayPerson(Person person) {
        if (person == null) {
            name.setText("No person selected");
            phone.setText("-");
            address.setText("-");
            email.setText("-");
            tags.getChildren().clear();
        } else {
            name.setText(person.getName().fullName);
            phone.setText(person.getPhone().value);
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
}

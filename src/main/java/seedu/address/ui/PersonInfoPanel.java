package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;


/**
 * Panel that displays detailed information of a selected {@code Person}.
 */
public class PersonInfoPanel extends UiPart<Region> {
    private static final String FXML = "PersonInfoPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final ReadOnlyAddressBook addressBook;

    @FXML
    private VBox infoBox;
    @FXML
    private Label name;
    @FXML
    private Hyperlink phone;
    @FXML
    private Hyperlink telegram;
    @FXML
    private Label address;
    @FXML
    private Hyperlink email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label note;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label telegramLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label notesLabel;

    private String telegramHandle;

    /**
     * Creates a {@code PersonInfoPanel} and initializes with placeholder text.
     */
    public PersonInfoPanel(ReadOnlyAddressBook addressBook) {
        super(FXML);
        this.addressBook = addressBook;
        displayPerson(null);
    }

    /**
     * Decides whether to display individual detail or overall statistics
     *
     * @param person The person whose details to display, or {@code null} for statistics.
     */
    public void displayPerson(Person person) {
        if (person == null) {
            displayStatistics();
        } else {
            displayPersonDetails(person);
        }
    }

    /**
     * Displays three key statistics in the LinkedUp default page.
     */
    private void displayStatistics() {
        int totalContacts = this.addressBook.getPersonList().size();
        List<String> topThreeTags = getTopThreeTags(new ArrayList<>(this.addressBook.getPersonList()));

        name.setText("LinkedUp Statistics");
        setPersonDisplay(false);

        VBox cardContainer = new VBox();
        cardContainer.setPrefHeight(200);

        // Total contacts
        VBox totalSection = new VBox(2);
        Text totalLabel = new Text("Total contacts");
        totalLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold;-fx-fill: #ffffff;"
                + "-fx-background-color: #4a4a4a; -fx-padding: 8 10;");
        Text totalValue = new Text(String.valueOf(totalContacts));
        totalValue.setStyle("-fx-font-size: 14; -fx-fill: #999999; -fx-padding: 6 0;");
        totalSection.getChildren().addAll(totalLabel, totalValue);

        // Top 3 tags
        VBox tagsSection = new VBox(4);
        Text tagsLabel = new Text("Top tags");
        tagsLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-fill: #ffffff;"
                + "-fx-background-color: #4a4a4a; -fx-padding: 8 10;");
        FlowPane tagFlow = new FlowPane();
        tagFlow.setStyle("-fx-hgap: 8;");
        for (String tag : topThreeTags) {
            Label tagLabel = new Label(tag);
            tagLabel.setStyle("-fx-font-size: 13; -fx-fill: #ffffff;"
                + "-fx-background-color: #2a95bd; -fx-padding: 3 4; -fx-text-fill: white;");
            tagFlow.getChildren().add(tagLabel);
        }
        tagsSection.getChildren().addAll(tagsLabel, tagFlow);

        // Most common address
        VBox addressSection = new VBox(4);
        Text addressLabel = new Text("Most common location");
        addressLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-fill: #ffffff;"
                + "-fx-background-color: #4a4a4a; -fx-padding: 8 10;");
        String mostCommonWord = getMostCommonAddressWord();
        Text addressValue = new Text(mostCommonWord.isEmpty() ? "N/A" : mostCommonWord);
        addressValue.setStyle("-fx-font-size: 13; -fx-fill: #999999; -fx-padding: 6 0;");
        addressSection.getChildren().addAll(addressLabel, addressValue);
        VBox container = new VBox(12);
        container.setStyle("-fx-spacing: 12;");
        container.getChildren().addAll(totalSection, tagsSection, addressSection);

        note.setGraphic(container);
        note.setText("");
    }

    /**
     * Displays all fields of a person's details including notes.
     *
     * @param person The person whose details to display
     */
    public void displayPersonDetails(Person person) {
        setPersonDisplay(true);
        note.setGraphic(null);

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
        note.setText(person.getNote().value);
        note.setWrapText(true);
        note.maxWidthProperty().bind(infoBox.widthProperty().subtract(24));
    }

    private void openUri(String uri) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(uri));
                logger.warning("Opened link: " + uri);
            }
        } catch (Exception e) {
            logger.warning("Could not open link: " + uri + " (" + e.getMessage() + ")");
        }
    }

    @FXML
    private void onPhoneClick() {
        String phoneText = phone.getText();
        if (!phoneText.isEmpty()) {
            String digits = phoneText.replaceAll("\\s+", "");
            openUri("tel:" + digits);
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

    @FXML
    private void onEmailClick() {
        String emailText = email.getText();
        if (!emailText.isEmpty()) {
            openUri("mailto:" + emailText.trim());
        }
    }

    private List<String> getTopThreeTags(List<Person> allPersons) {
        return allPersons.stream()
                .flatMap(person -> person.getTags().stream())
                .collect(Collectors.groupingBy(tag -> tag.tagName, Collectors.counting()))
                .entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public String getMostCommonAddressWord() {
        Map<String, Integer> wordCount = new HashMap<>();
        Set<String> addressWords = Set.of(
                "avenue", "ave", "street", "str", "road", "rd", "lane", "ln", "drive", "dr", "court", "ct"
        );

        for (Person person : addressBook.getPersonList()) {
            String[] words = person.getAddress().value.toLowerCase().split("[\\s,.-]+");

            for (String word : words) {
                if (word.matches(".*\\d.*")
                        || addressWords.contains(word)) {
                    continue;
                }
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        return wordCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    private void setPersonDisplay(boolean bool) {
        phone.setManaged(bool);
        phone.setVisible(bool);
        telegram.setManaged(bool);
        telegram.setVisible(bool);
        address.setManaged(bool);
        address.setVisible(bool);
        email.setManaged(bool);
        email.setVisible(bool);
        tags.setManaged(bool);
        tags.setVisible(bool);
        phoneLabel.setManaged(bool);
        phoneLabel.setVisible(bool);
        telegramLabel.setManaged(bool);
        telegramLabel.setVisible(bool);
        addressLabel.setManaged(bool);
        addressLabel.setVisible(bool);
        emailLabel.setManaged(bool);
        emailLabel.setVisible(bool);
        notesLabel.setManaged(bool);
        notesLabel.setVisible(bool);
    }
}

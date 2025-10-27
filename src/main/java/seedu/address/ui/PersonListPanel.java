package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private static final int PAGE_JUMP = 5;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        // focus on first list item when UI loads
        javafx.application.Platform.runLater(() -> {
            if (!personListView.getItems().isEmpty() && personListView.getSelectionModel().isEmpty()) {
                personListView.getSelectionModel().select(0);
            }
            personListView.requestFocus();
        });

        // intercept keyboard input
        personListView.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            KeyCode code = e.getCode();
            switch (code) {
            case UP -> {
                e.consume();
                selectRelative(-1); // previous list item
            }
            case DOWN -> {
                e.consume();
                selectRelative(1); // next list item
            }
            case PAGE_UP -> {
                e.consume();
                selectRelative(-PAGE_JUMP); // jump upwards few items
            }
            case PAGE_DOWN -> {
                e.consume();
                selectRelative(PAGE_JUMP); // jump downwards few items
            }
            case HOME -> {
                e.consume();
                selectIndex(0); // first list item
            }
            case END -> {
                e.consume();
                selectIndex(personListView.getItems().size() - 1); // last list item
            }
            // vim style keybind
            case J -> {
                e.consume();
                selectRelative(1); // next list item
            }
            case K -> {
                e.consume();
                selectRelative(-1); // previous list item
            }
            default -> {
                // allow rest to pass through
            }
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

    public ListView<Person> getPersonListView() {
        return personListView;
    }

    // move list item selection up or down
    private void selectRelative(int delta) {
        var items = personListView.getItems();
        if (items == null || items.isEmpty()) return;

        var sm = personListView.getSelectionModel();
        int current = Math.max(0, sm.getSelectedIndex());
        int next = Math.max(0, Math.min(current + delta, items.size() - 1));
        if (current != next) {
            sm.select(next);
            personListView.scrollTo(next);
        }
    }

    // move to a specific list item
    private void selectIndex(int index) {
        var items = personListView.getItems();
        if (items == null || items.isEmpty()) return;

        int bounded = Math.max(0, Math.min(index, items.size() - 1));
        personListView.getSelectionModel().select(bounded);
        personListView.scrollTo(bounded);
    }
}

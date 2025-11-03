package seedu.address.ui;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Creates an alert message for the user. */
    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText);

}

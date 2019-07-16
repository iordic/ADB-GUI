package application.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class DateTimePickerController implements Initializable {

    public ChoiceBox<Integer> choiceBoxHour;
    public ChoiceBox<Integer> choiceBoxMinute;
    public ChoiceBox<Integer> choiceBoxSecond;
    public DatePicker datePicker;
    private Stage stage;
    //private Scene scene;

    ObservableList<Integer> hoursListItems = FXCollections.observableArrayList();
    ObservableList<Integer> minutesListItems = FXCollections.observableArrayList();
    ObservableList<Integer> secondsListItems = FXCollections.observableArrayList();
    private DateTimePickerListener dateTimePickerListener;

    public interface DateTimePickerListener {
        void onDateSet(Calendar calendar);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Calendar calendar = Calendar.getInstance();
        choiceBoxHour.setItems(hoursListItems);
        choiceBoxMinute.setItems(minutesListItems);
        choiceBoxSecond.setItems(secondsListItems);

        for (int i = 0; i < 24; i++) {
            hoursListItems.add(i);
        }

        for (int i = 0; i < 60; i++) {
            minutesListItems.add((i));
            secondsListItems.add((i));
        }

        choiceBoxHour.getSelectionModel().select(calendar.get(Calendar.HOUR_OF_DAY));
        choiceBoxMinute.getSelectionModel().select(calendar.get(Calendar.MINUTE));
        choiceBoxSecond.getSelectionModel().select(calendar.get(Calendar.SECOND));
        LocalDate localDate = LocalDate.now();
        datePicker.setValue(localDate);
    }

    private void setStage(Stage stage, Scene scene) {
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
            }
        });

        this.stage = stage;
        //this.scene = scene;
    }

    public static void showScreen(Class<?> class1, DateTimePickerListener dateTimePickerListener) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(class1.getResource("/application/view/DateTimePicker.fxml"));

        Parent root1 = (Parent) fxmlLoader.load();

        DateTimePickerController controller = fxmlLoader.<DateTimePickerController>getController();
        controller.setListener(dateTimePickerListener);

        Stage stage = new Stage();
        /*stage.setHeight(100);
        stage.setWidth(500);*/
        stage.setTitle("Date / Time picker");
        Scene scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();

        controller.setStage(stage, scene);
    }

    private void setListener(DateTimePickerListener dateTimePickerListener) {
        this.dateTimePickerListener = dateTimePickerListener;
    }

    public void onSetClicked(ActionEvent actionEvent) {

        Calendar calendar = Calendar.getInstance();

        LocalDate localDate = datePicker.getValue();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth(),
                choiceBoxHour.getSelectionModel().getSelectedIndex(), choiceBoxMinute.getSelectionModel().getSelectedIndex(),
                choiceBoxSecond.getSelectionModel().getSelectedIndex());

        dateTimePickerListener.onDateSet(calendar);
        stage.close();
    }
}

package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.controller.Controller;

import java.io.IOException;


public class EmbeddedUI extends Application {

    private Controller controller;

    @Override
    public void init() throws Exception {
        super.init();
        controller = new Controller();
    }

    @Override
    public void start(Stage primaryStage) {

        TextArea appointment = new TextArea("");
        TextArea instructions = new TextArea("Please fill in the appointment id and then press the button");
        instructions.setEditable(Boolean.FALSE);
        instructions.setBackground(Background.EMPTY);
        Button start = new Button("START Appointment");
        start.setOnAction(event -> {

            Integer availableSlotNo = controller.getAvailableSlotNo(appointment.getText());
            if (availableSlotNo == null) {
                showMessage(primaryStage, "Something went wrong. No slots available. Please retry when your appointment starts!");
            }
        });
        Button end = new Button("END Appointment");
        end.setOnAction(event -> {
            try {
                controller.finishAppointment(appointment.getText());
            } catch (IOException e) {
                e.printStackTrace();
                showMessage(primaryStage, "Appointment could not be finished! Please try again!");
            }
            showMessage(primaryStage, "Appointment finished successfully!");
        });
        VBox vbox = new VBox(appointment, instructions, start, end);
        primaryStage.setTitle("Home Page");
        primaryStage.setScene(new Scene(vbox, 300, 275));
        primaryStage.show();
    }

    private void showMessage(Stage primaryStage, String message) {

        Button back = new Button("BACK");
        back.setOnAction(event -> start(primaryStage));
        TextArea instructions = new TextArea(message);
        VBox vbox = new VBox(instructions, back);
        primaryStage.setTitle("Instructions");
        primaryStage.setScene(new Scene(vbox, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

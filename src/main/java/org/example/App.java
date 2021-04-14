package org.example;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.io.*;
import java.util.concurrent.atomic.AtomicReference;

public class App extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        AtomicReference<File> fileOne = new AtomicReference<>();
        AtomicReference<File> fileSecond = new AtomicReference<>();
        TextField firstFile =
            new InputField().setType(InputType.TEXT).setDisable().setCoordinates(50, 50).setPrefWidth(300).getField();
        TextField secondFile =
            new InputField().setType(InputType.TEXT).setDisable().setCoordinates(375, 50).setPrefWidth(300).getField();
        TextField outputFile =
            new InputField().setType(InputType.TEXT).setDisable().setCoordinates(200, 200).setPrefWidth(300).getField();
        CheckBox checkBox = new CustomCheckbox().createCheckbox()
            .setText("Check this If you want Different records in both files(Invert Match)").setCoordinates(140, 170)
            .getCheckbox();

        final ProgressIndicator pin = new ProgressIndicator();
        pin.setProgress(-1.0f);
        final HBox progressIndicatorBox = new HBox();
        progressIndicatorBox.setAlignment(Pos.CENTER);
        progressIndicatorBox.getChildren().addAll(pin);
        progressIndicatorBox.setLayoutX(320);
        progressIndicatorBox.setLayoutY(300);

        progressIndicatorBox.setVisible(false);
        Button browseBtnFirstFile =
            new CustomButton().createButton().setCoordinates(150, 105).setText("Browse File 1").getButton();
        Button browseBtnSecondFile =
            new CustomButton().createButton().setCoordinates(450, 105).setText("Browse File 2").getButton();
        Button match = new CustomButton().createButton().setCoordinates(300, 250).setText("Match Files").getButton();

        browseBtnFirstFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
            fileChooser.setTitle("Choose csv");
            fileOne.set(fileChooser.showOpenDialog(primaryStage));
            if (fileOne.get() != null) {
                firstFile.setText(fileOne.get().getPath());
                firstFile.end();
            }
        });
        browseBtnSecondFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
            fileChooser.setTitle("Choose csv");
            fileSecond.set(fileChooser.showOpenDialog(primaryStage));
            if (fileSecond.get() != null) {
                secondFile.setText(fileSecond.get().getPath());
                secondFile.end();
            }

        });
        match.setOnAction(event -> {

            if ((firstFile.getText() != null && !firstFile.getText().isEmpty()) || (secondFile.getText() != null
                && !secondFile.getText().isEmpty())) {
                progressIndicatorBox.setVisible(true);
                new Thread(() -> {
                    String matchedString =
                        new MatchingUtil().read(firstFile.getText()).match(secondFile.getText(), checkBox.isSelected());
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile.getText()))) {
                        bw.append(matchedString);
                        bw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        progressIndicatorBox.setVisible(false);
                    }

                }).start();
            }

        });

        //creating a Group object
        Group root = new Group();
        outputFile.setText(System.getProperty("user.dir") + File.separator + "output.csv");

        ObservableList<Node> list = root.getChildren();

        list.addAll(browseBtnFirstFile, browseBtnSecondFile, firstFile, secondFile, progressIndicatorBox, match,
                    outputFile, checkBox);
        outputFile.end();
        //Creating a Scene by passing the group object, height and width
        Scene scene = new Scene(root, 700, 400);

        //setting color to the scene
        scene.setFill(Color.LIGHTGRAY);

        //Setting the title to Stage.
        primaryStage.setTitle("CSV Compare");

        //Adding the scene to Stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();
    }
}

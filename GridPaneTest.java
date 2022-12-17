package Project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GridPaneTest extends Application {
    public static void main(String[] args)
    {
        launch(args);
    }


    public void start(Stage primaryStage)
    {
        ListView listView = new ListView();
        GridPane schedule = new GridPane();
        listView.getItems().add(schedule);
        listView.setPadding(new Insets(15));

        schedule.setPadding(new Insets(15));
        schedule.setGridLinesVisible(true);

        schedule.setStyle("-fx-background-radius: 25; -fx-background-color: rgb(0, 153, 153);");
        Label[] weekDays = { new Label("Sunday"), new Label("Monday"),new Label("Tuesday"),
            new Label("Wednesday"),new Label("Thursday")};
        Label[] times = {new Label("7:00"), new Label("8:00"), new Label("9:00"), new Label("10:00"), new Label("11:00"),new Label("12:00"),
                new Label("1:00"),new Label("2:00"), new Label("3:00"), new Label("4:00"), new Label("5:00")};


        schedule.setVgap(50);

        Scene scene = new Scene(listView, 1350, 750);
        scene.setFill(Color.CYAN);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Test");
        primaryStage.show();

    }


}

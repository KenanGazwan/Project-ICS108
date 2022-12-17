package Project;

import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;

public class TableViewPage1{

    private TableView<Section> table;
    private ButtonsAdjustment add, remove;
    public TableViewPage1()
    {
        buildTable();
    }

    public void buildTable() {
        table = new TableView<>();

        TableColumn<Section, String> course_sec = new TableColumn<>("Course - Sec");
        course_sec.setCellValueFactory(new PropertyValueFactory<>("Course"));

        TableColumn<Section, String> day = new TableColumn<>("Day");
        day.setCellValueFactory(new PropertyValueFactory<>("Day"));

        TableColumn<Section, String> time = new TableColumn<>("Time");
        time.setCellValueFactory(new PropertyValueFactory<>("Time"));

        TableColumn<Section, String> location = new TableColumn<>("Location");
        location.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn<Section, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("Status"));

        add = new ButtonsAdjustment("Add");
        remove = new ButtonsAdjustment("Remove");

        table.getColumns().addAll(course_sec, time, day, location, add.getActionCol(), remove.getActionCol(), status);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setShape(new Circle());
        table.setPadding(new Insets(20, 120, 20, 120));

    }

    public ButtonsAdjustment getButton()
    {
        return add;
    }

    public TableView getTable()
    {
        return table;
    }


}
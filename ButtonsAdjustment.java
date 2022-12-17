package Project;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ButtonsAdjustment{
    private TableColumn actionCol;
    private String buttonTitle;
    private TilePane basket = new TilePane();
    private ArrayList<Schedule> scheduleComponents = new ArrayList<>();;

    public ButtonsAdjustment() {}
    public ButtonsAdjustment(String buttonTitle) {
        this.buttonTitle = buttonTitle;
        build();
    }

    public void build() {

        basket.setPadding(new Insets(15));
        basket.setPrefColumns(2);
        //For Test Purposes
        Button buttonTest = new Button("Print");
        buttonTest.setOnAction(e-> {scheduleComponents.forEach(x -> System.out.println(x.toString()));});
        basket.getChildren().add(buttonTest);


        actionCol = new TableColumn("");
        actionCol.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<Section, String>, TableCell<Section, String>> cellFactory = new Callback<>() {
            TableCell<Section, String> cell;
            @Override
            public TableCell call(final TableColumn<Section, String> param) {
                cell = new TableCell<Section, String>() {

                    final Button btn = new Button(buttonTitle);
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {

                                //The Button Of Section In Basket
                                Section section = getTableRow().getItem();

                                if (buttonTitle == "Add") {

                                    //This Condition to take care of duplications in the basket
                                    if(section.getStatus().equals("---")) {
                                        section.setStatus("Added"); //Change the Status of the Course-Sec
                                        section.setButton(new Button(section.getCourse() + "\n" + section.getTime())); //Initialize basket Button
                                        section.getButton().setPrefSize(150, 75); section.getButton().setRotate(-20);
                                        basket.getChildren().add(section.getButton());
                                        section.getButton().setOnAction(e-> addToSchedule(section)); //When the Course in the basket is clicked it will be added to the schedule
                                        getTableView().refresh();

                                    }

                                    if(section.getButton().isDisable())
                                        section.getButton().setDisable(false);


                                } else { //buttonTitle == "Remove"
                                    if (section.getButton() != null)
                                    {
                                        section.setStatus("---");
                                        basket.getChildren().remove(section.getButton());//DOES NOT WORK
                                        section.getButton().setDisable(true);
                                        getTableView().refresh();
                                    }
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };

                return cell;
            }

        };

        actionCol.setCellFactory(cellFactory);
    }

    public TableColumn getActionCol() {
        return actionCol;
    }

    public TilePane getBasket()
    {
        return basket;
    }

    private void addToSchedule(Section section)
    {
        int term = findTheTerm();
        int StartTime1 = 0, EndTime1 = 0;

        String justCourse = section.getCourse().split("-")[0];
        boolean flag1 = true, flag2 = true;
        String Days;

        //First Restriction (Course - Sec Conflict)
        for(int i = 0; i < scheduleComponents.size(); i++)
            if(justCourse.equals(scheduleComponents.get(i).getCourse().split("-")[0]))
                flag1 = false;

        //Second Restriction (Days Conflict) So need to Check More Deep (For Time)
        Days = section.getDay();
        for(int i = 0; i < scheduleComponents.size(); i++)
        {
            String Days2 = scheduleComponents.get(i).getDay();
            for(int j = 0; j < Days.length(); j++)
                for(int k = 0; k < Days2.length(); k++)
                    if(Days.substring(j).equals(Days2.substring(k)))
                    {
                        String[] time1 = section.getTime().split("-");
                        String[] time2 = scheduleComponents.get(i).getTime().split("-");

                        StartTime1 = Integer.parseInt(time1[0]);
                        EndTime1 = Integer.parseInt(time1[1]);

                        int StartTime2 = Integer.parseInt(time2[0]);
                        int EndTime2 = Integer.parseInt(time2[1]);

                        if(StartTime1 >= StartTime2 && EndTime1 <= EndTime2)
                            flag2 = false;
                    }
        }

            if(flag1 && flag2) //Not Duplication And no Time Conflict
            {
                scheduleComponents.add(new Schedule(section.getCourse(), section.getTime(), section.getDay(), section.getLocation(), term));
                char[] days = new char[section.getDay().length()];
                section.getDay().getChars(0, section.getDay().length()-1, days, 0);
                //doItVisually(section.getCourse(), StartTime1, EndTime1, days, section.getLocation());
            }

    }

    public ArrayList<Schedule> getScheduleComponents()
    {
        return scheduleComponents;
    }

    private int findTheTerm()
    {
        File file = new File("FinishedCourses.csv");

        try(Scanner scanner = new Scanner(file)) {

            String[] line = scanner.nextLine().split(",");
            int term = Integer.parseInt(line[1]);
            //find greats term value in the file
            while(scanner.hasNext())
            {
                line = scanner.nextLine().split(",");
                if(term < Integer.parseInt(line[1]))
                    term = Integer.parseInt(line[1]);
            }
            //return the correct term value depending on the last term in finished courses
            if(term % 10 == 1 || term % 10 == 2)
                return term + 1;
            else
                return term + 8;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Finished Courses File is Not There!");
        }
        return 1;
    }
}



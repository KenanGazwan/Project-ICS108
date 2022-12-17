package Project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main extends Application {
    BorderPane pane_2 = new BorderPane();
    BorderPane pane_1 = new BorderPane();
    Scene scene1 = new Scene(pane_1, 1350, 750);
    Scene scene2 = new Scene(pane_2, 1350, 750);
    TableViewPage1 tableBuilder = new TableViewPage1(); //Creating the table view for page1 with its Add, Remove buttons
    public void start(Stage primaryStage)
    {
        //Panes
            //Pane1
         //Label1 + Button1 + Pane2
        pane_1.setPadding(new Insets(15, 15, 15, 15));
        pane_1.setBackground(Background.fill(Color.ORANGE));
            //Pane2
        pane_2.setPadding(new Insets(15, 15, 15, 15));
        pane_2.setBackground(Background.fill(Color.ORANGE));
        WeekPane weekPane = new WeekPane();
        pane_2.setCenter(weekPane.getListView());
            //Pane 3
        HBox page1Header = new HBox();
        page1Header.setStyle("-fx-background-radius: 25; -fx-background-color: rgb(0, 153, 153);");
        page1Header.setPadding(new Insets(10));
        page1Header.setSpacing(850);
        BorderPane.setAlignment(page1Header, Pos.TOP_RIGHT);
        pane_1.setTop(page1Header);

        //Labels
        Label lb1 = new Label("Add Sections To Basket");
        lb1.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 30));
        lb1.setTextFill(Color.HONEYDEW);
        lb1.setAlignment(Pos.CENTER);
        page1Header.getChildren().add(lb1);

        Label lb2 = new Label("Modify Your Schedule");
        lb2.setFont(new Font("Calibri", 30));
        pane_2.setTop(lb2);
        BorderPane.setAlignment(lb2, Pos.TOP_CENTER);

        //Buttons
        //Start with a saved Schedule Button
        Button bt1 = new Button("Start With \nA Saved Schedule");
        bt1.setWrapText(true);
        bt1.setAlignment(Pos.TOP_RIGHT);
        bt1.setTooltip(new Tooltip("IF You Have Already A Saved Schedule You Can Start With It Now"));
        page1Header.getChildren().add(bt1);
        bt1.setOnAction(e->{

        });


        //Next Button
        Button bt2 = new Button("Next");
        bt2.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        bt2.setPrefSize(70, 45);
        pane_1.setBottom(bt2);
        BorderPane.setAlignment(bt2, Pos.BOTTOM_RIGHT);
        bt2.setTooltip(new Tooltip("Click!, To See The Basket And The Schedule"));
        bt2.setOnAction(e -> {
            primaryStage.setScene(scene2);
            primaryStage.setTitle("Page2");

        });

        //Return Button
        Button bt3 = new Button("Return");
        bt3.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
        bt3.setPrefSize(70, 45);
        pane_2.setBottom(bt3);
        BorderPane.setAlignment(bt3, Pos.BOTTOM_RIGHT);
        bt3.setOnAction(e -> {
            primaryStage.setScene(scene1);
            primaryStage.setTitle("Page1");
        });

        //Save Schedule Button
        Button bt4 = new Button("Save Schedule");
        bt4.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
        bt4.setPrefSize(150, 60);
        bt4.setRotate(-90);
        pane_2.setLeft(bt4);
        BorderPane.setAlignment(bt4, Pos.CENTER);
        bt4.setOnAction(e-> {
            saveTheSchedule(weekPane.getTheSchedule());
        });

        pane_2.setRight(tableBuilder.getButton().getBasket()); //Adding the basket created in ButtonsAddRemove in the pane_2
        pane_1.setCenter(tableBuilder.getTable());

        //Initializing The Files
        File courseOfferingFile = new File("CourseOffering 1.csv");
        File finishedCourseFile = new File("FinishedCourses.csv");
        File degreePlanFile = new File("DegreePlan.csv");

        //Finding the number of items in files by numFile method
        int numCOF = numFile(courseOfferingFile);
        int numFCF = numFile(finishedCourseFile);
        int numDPF = numFile(degreePlanFile);

        //initializing arrays for classes to fill files information inside
        Section[] sections = new Section[numCOF];
        Student[] finishedCourses = new Student[numFCF];
        Course[] courses = new Course[numDPF];

        try(Scanner scannerCOf = new Scanner(courseOfferingFile);
            Scanner scannerFC = new Scanner(finishedCourseFile);
            Scanner scannerDP = new Scanner(degreePlanFile)){

            //Filling degree plan courses in an array
            int index = 0;
            while (scannerDP.hasNext())
            {
                String[] values_p = scannerDP.nextLine().split(","); //course 0 , creditHours 1, preReq 2, coReq 3
                courses[index] = new Course(values_p[0], values_p[2], values_p[3]);
                index++;
            }

            //Filling finished courses in an array
            index = 0;
            while(scannerFC.hasNext()){
                String[] values_f = scannerFC.nextLine().split(",");
                finishedCourses[index] = new Student(values_f[0]);
                index++;
            }

            //Filling TableView By eligible Courses (matching preRequest and finishing courses conditions ) -Start
            String nothing = scannerCOf.nextLine();
            Section section;
            String line="";
            index = 0;

            while (scannerCOf.hasNext()){

                line = scannerCOf.nextLine();
                String[] values = line.split(","); //[0]:Course-Sec, [1]:Activity, [2]:CRN, [3]:Course Name, [4]:Instructor, [5]:Day, [6]:Time, [7]:Location, [8]:Status, [9]:WaitList
                String[] just_Course_section = values[0].split("-");
                String just_Course = just_Course_section[0];

                //Catching finished Courses and ensure for preRequest USING FLAG1 and FLAG2
                boolean flag1 = true;
                boolean flag2 = false;
                String preReq = returnPreRequisite(courses, just_Course);
                for(int i = 0; i < numFCF; i++) {
                    if (finishedCourses[i].getFinishedCourse().equals(just_Course))
                        flag1 = false;

                    if(finishedCourses[i].getFinishedCourse().equals(preReq))
                        flag2 = true;
                }
                if("None".equals(preReq) && flag1)
                    flag2 = true;

                //Filling the courses that skipped the restrictions successfully
                if(flag1 && flag2)
                {
                    section = new Section(values[0], values[6],values[5],values[7]);
                    sections[index] = section;
                    tableBuilder.getTable().getItems().add(section);
                    index++;
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        //Filling TableView By eligible Courses (matching preRequest and finishing courses conditions) -End


        primaryStage.setScene(scene1);
        primaryStage.setTitle("Page1");
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    //Method A Compute the number of items in the file
    public static int numFile(File file)
    {
        int num = 0;
        try (Scanner scanner = new Scanner(file))
        {
            while(scanner.hasNext())
            {
                num++;
                scanner.nextLine();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return num;
    }

    //Method B
    //Returning The preRequest OF The Given Course
    public static String returnPreRequisite(Course[] courses, String justCourse)
    {
        for(int i = 0; i < courses.length; i++)
        {
            if(justCourse.equals(courses[i].getCourse())) {
                return courses[i].getPreRequisite();
            }
        }
        return "None";
    }

    //Method C Save the schedule in a Binary File
    public void saveTheSchedule(ArrayList<Schedule> TheSchedule)
    {
        try(ObjectOutputStream bfile = new ObjectOutputStream(new FileOutputStream("Saved Schedule.dat")))
        {
            bfile.writeObject(TheSchedule);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


}




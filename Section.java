package Project;

import javafx.scene.control.Button;

public class Section extends Course {

    //Course
    //Time (From-To) (Days)
    //Location

    private String time;
    private String day;
    private String location;
    private Button button = new Button(""); // Button in the basket
    private String status;

    public Section(String course, String time, String day, String location) {
        super(course);
        this.time = time;
        this.day = day;
        this.location = location;
        this.status = "---";
    }

    public String getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }

    public String getLocation() {
        return location;
    }

    public Button getButton()
    {
        return button;
    }

    public void setButton(Button button)
    {
        this.button = button;
    }

    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
}

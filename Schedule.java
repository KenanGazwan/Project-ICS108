package Project;

public class Schedule extends Section {
    private int term;

    public Schedule(String course, String time, String day, String location, int term)
    {
        super(course, time, day, location);
        this.term = term;
    }

    //For Test purposes
    public String toString() {
        return getCourse() + " " + getTime() + " " + getDay() + " " + term;
    }

}

package Project;

public class Course {

    private String course;
    private String preRequisite;
    private String coRequisite;

    //Constructor 3
    public Course(String course)
    {
        this.course = course;
    }

    public Course(String course,  String preRequisite, String coRequisite)
    {
        this.course = course;
        this.coRequisite = coRequisite;
        this.preRequisite = preRequisite;
    }


    public String getCourse() {
        return course;
    }

    public String getPreRequisite()
    {
        return preRequisite;
    }

    public String getCoRequisite()
    {
        return coRequisite;
    }

    public String toString()
    {
        return course + " " + preRequisite + " " + coRequisite;
    }

    public String get_preRequest(String course)
    {
        return preRequisite;
    }
}

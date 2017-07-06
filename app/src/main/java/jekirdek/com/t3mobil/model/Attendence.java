package jekirdek.com.t3mobil.model;

/**
 * Created by cem on 6.07.2017.
 */
public class Attendence {

    private int id;
    private int scheduleId;
    private int instructorId;
    private int studentId;
    private int presence;
    private String studentNameSurname;
    private String lessonDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }

    public String getStudentNameSurname() {
        return studentNameSurname;
    }

    public void setStudentNameSurname(String studentNameSurname) {
        this.studentNameSurname = studentNameSurname;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    @Override
    public String toString() {
        return "Attendence{" +
                "id=" + id +
                ", scheduleId=" + scheduleId +
                ", instructorId=" + instructorId +
                ", studentId=" + studentId +
                ", presence=" + presence +
                ", studentNameSurname='" + studentNameSurname + '\'' +
                ", lessonDate='" + lessonDate + '\'' +
                '}';
    }
}

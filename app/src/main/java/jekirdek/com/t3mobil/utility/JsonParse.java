package jekirdek.com.t3mobil.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jekirdek.com.t3mobil.model.Attendence;
import jekirdek.com.t3mobil.model.Lesson;
import jekirdek.com.t3mobil.model.User;

/**
 *This class for only and main job is json parsing
 */
public class JsonParse {


    public User userJsonParser(String userJsonResponse){

        User user = new User();

        try {
            JSONArray jsonUserObject = new JSONArray(userJsonResponse);
            JSONObject jsonObject = jsonUserObject.getJSONObject(0);
            user.setId(jsonObject.getInt("id"));
            user.setName(jsonObject.getString("name"));
            user.setSurname(jsonObject.getString("surname"));
            user.setCitizenId(jsonObject.getString("citizenId"));
            user.setGender(jsonObject.getString("gender"));
            user.setEmail(jsonObject.getString("email"));
            user.setPassword(jsonObject.getString("password"));
            user.setPhoneNumber(jsonObject.getString("phoneNumber"));
            user.setUserType(jsonObject.getString("userType"));
            user.setSchoolName(jsonObject.getString("schoolName"));
            user.setSection(jsonObject.getString("section"));
            user.setClas(jsonObject.getString("class"));
            user.setGrade(jsonObject.getString("grade"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(user.toString());

        return user;
    }

    public ArrayList<String> getLessonList(String lessonJsonResponse){

        ArrayList<String> lessons = null;
        try {
            JSONArray jsonArray = new JSONArray(lessonJsonResponse);
            int jsonArrayLenght = jsonArray.length();
            lessons = new ArrayList<>();
            for (int i = 0; i < jsonArrayLenght; i++) {
                lessons.add(jsonArray.getJSONObject(i).getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lessons;
    }

    /**
     *
     * @param attendenceJsonResponse : tüm devamsızlık listesi
     * @return tüm listeyi döner fakat bir parametre(öğrenci ad soyad) daha alıp sadece gerekli öğrencinin devamsızlık listesi dönmesi gerekir
     */
    public Attendence[] getAttendenceList(String attendenceJsonResponse){

        Attendence[] attendences = null;

        try {
            JSONArray jsonArray = new JSONArray(attendenceJsonResponse);
            int jsonArrayLenght = jsonArray.length();
            attendences = new Attendence[jsonArrayLenght];
            for (int i = 0; i < jsonArrayLenght; i++) {
                attendences[i].setId(jsonArray.getJSONObject(i).getInt("id"));
                attendences[i].setScheduleId(jsonArray.getJSONObject(i).getInt("scheduleId"));
                attendences[i].setInstructorId(jsonArray.getJSONObject(i).getInt("instructorId"));
                attendences[i].setStudentId(jsonArray.getJSONObject(i).getInt("studentId"));
                attendences[i].setPresence(jsonArray.getJSONObject(i).getInt("presence"));
                attendences[i].setStudentNameSurname(jsonArray.getJSONObject(i).getString("studentNameSurname"));
                attendences[i].setLessonDate(jsonArray.getJSONObject(i).getString("lessonDate"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return attendences;
    }

}

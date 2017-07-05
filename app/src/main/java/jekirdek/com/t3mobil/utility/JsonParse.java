package jekirdek.com.t3mobil.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
            user.setGender(jsonObject.getString("gender"));
            user.setEmail(jsonObject.getString("email"));
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



}

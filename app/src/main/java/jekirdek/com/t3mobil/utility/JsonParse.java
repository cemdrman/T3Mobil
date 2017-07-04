package jekirdek.com.t3mobil.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jekirdek.com.t3mobil.model.User;

/**
 *
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
}

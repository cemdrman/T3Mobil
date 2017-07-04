package jekirdek.com.t3mobil.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

import jekirdek.com.t3mobil.model.User;

/**
 * Created by cem on 5.07.2017.
 */
public class BilgilerDB extends SQLiteOpenHelper {

    private static final int dbVersion = 1;
    private static final String dbName = "bilgiler_db";
    private static final String tblName = "Bilgiler";

    public BilgilerDB(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("db","creating db");
        db.execSQL("CREATE TABLE " + tblName +" (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, surname TEXT, gender TEXT, email TEXT," +
                "password TEXT, phoneNumber TEXT, userType TEXT," +
                "schoolName TEXT, section TEXT, class TEXT, " +
                "grade TEXT)");
        Log.d("db","created db");
    }

    public void saveUser(User user){
        Log.d("db","user saving");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("surname",user.getSurname());
        values.put("gender",user.getGender());
        values.put("email",user.getEmail());
        values.put("password",user.getPassword());
        values.put("phoneNumber",user.getPhoneNumber());
        values.put("userType",user.getUserType());
        values.put("schoolName",user.getSchoolName());
        values.put("section",user.getSection());
        values.put("class",user.getClas());
        values.put("grade",user.getGrade());
        db.insert(tblName, null, values);
        db.close();
        Log.d("db","user saved");
    }

    public User getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tblName;
        Cursor cursor = db.rawQuery(query,null);
        User user = new User();
        cursor.moveToFirst();
        user.setId(cursor.getInt(0));
        user.setName(cursor.getString(1));
        user.setSurname(cursor.getString(2));
        user.setGender(cursor.getString(3));
        user.setEmail(cursor.getString(4));
        user.setPassword(cursor.getString(5));
        user.setPhoneNumber(cursor.getString(6));
        user.setUserType(cursor.getString(7));

        return user;
    }

    public int getRowCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tblName;
        Cursor cursor = db.rawQuery(query,null);
        int rowCount = cursor.getCount();
        cursor.close();
        return rowCount;
    }

    public void resetTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(tblName,null,null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
package jekirdek.com.t3mobil.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by cem
 * Kullanıcının email ve şifresi her girişinde sorulmaması için
 * database kayıt yapılır
 */
public class TokenDB extends SQLiteOpenHelper {

    private static final int dbVersion = 1;
    private static final String dbName = "token_database";
    private static final String tblName = "TOKEN";

    public TokenDB(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("db","creating db");
        db.execSQL("CREATE TABLE " + tblName +" (id INTEGER PRIMARY KEY AUTOINCREMENT,token TEXT, staffID TEXT)");
        Log.d("db","created db");
    }

    public void saveToken(String token, int id){
        Log.d("db","saving token");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("token",token);
        values.put("staffID",id);
        db.insert(tblName, null, values);
        db.close();
        Log.d("db","token saved");
    }

    public String getToken(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tblName;
        Cursor cursor = db.rawQuery(query,null);
        String token = null;
        HashMap<String,String> map = new HashMap<>();
        if (cursor.moveToFirst()) {
            token = cursor.getString(1);
        }
        return token;
    }

    public int getID(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tblName;
        Cursor cursor = db.rawQuery(query,null);
        String id = null;
        HashMap<String,String> map = new HashMap<>();
        if (cursor.moveToFirst()) {
            id = cursor.getString(2);
        }
        return Integer.valueOf(id);
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

package sjtu.com.apdetector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by colin1434 on 2015/5/15.
 */
public class DataBase extends SQLiteOpenHelper {
    static DataBase data;
    public static DataBase GetData(Context context){
        data=new DataBase(context);
        return data;
    }
    public DataBase(Context context) {
        super(context, "data", null, 1);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE data(_id integer primary key,time text,ap text," +
                "longtitude text,latitude text);");
    }
    public void InsertData(String username,String ap,String lo,String la){
        SQLiteDatabase mydb= data.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("time", username);
        cv.put("ap", ap);
        cv.put("longtitude", lo);
        cv.put("latitude", la);
        mydb.insert("data", null, cv);
        mydb.close();
    }
    public boolean IsEquals(String ap){
        boolean rem=true;
        SQLiteDatabase db=data.getReadableDatabase();
        Cursor cursor = db.rawQuery("select ap from data",null);
        if(cursor.moveToLast()){
            if(ap.equals(cursor.getString(0))) rem=false;
        }
        else rem=true;
        cursor.close();
        db.close();
        return rem;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

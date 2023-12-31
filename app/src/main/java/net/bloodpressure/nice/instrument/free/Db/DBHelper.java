package net.bloodpressure.nice.instrument.free.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static SQLiteOpenHelper mInstance;

    private final static String name = "blood_pressure.db";

    public static SQLiteOpenHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new DBHelper(context, name, null, 1);
        }
        return mInstance;
    }

    private DBHelper(Context context, String name,
                                SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table blood_pressure(_id integer primary key autoincrement,pulse integer,diastolic integer,systolic integer,title integer,timestamp timestamp )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
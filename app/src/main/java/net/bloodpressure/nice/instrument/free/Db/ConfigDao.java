package net.bloodpressure.nice.instrument.free.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;


import net.bloodpressure.nice.instrument.free.bean.BloodBean;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ConfigDao {
    private SQLiteOpenHelper myDbHelper;

    private Context context;
    public ConfigDao(Context context){
        myDbHelper = DBHelper.getInstance(context);

        this.context = context;
    }

    //添加配置
    public int add(BloodBean bloodBean){
        int diastolic = bloodBean.getDiastolic();
        String timestamp = bloodBean.getTimestamp();
        String title = bloodBean.getTitle();
        int systolic = bloodBean.getSystolic();
        int pulse = bloodBean.getPulse();



        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        if(db.isOpen()){

                ContentValues values = new ContentValues();

                values.put("pulse", pulse);
                values.put("diastolic", diastolic);
                values.put("systolic", systolic);
                values.put("title", title);
                values.put("timestamp", timestamp);

                db.insert("blood_pressure", "_id", values);
                db.close();
                return 0;
        }
        return 1;
    }

    //更新Hook配置
    public void update(int select_id,BloodBean bloodBean){

        System.out.println(select_id);
        int diastolic = bloodBean.getDiastolic();
        String timestamp = bloodBean.getTimestamp();
      //  long id = bloodBean.getId();
        String title = bloodBean.getTitle();
        int systolic = bloodBean.getSystolic();
        int pulse = bloodBean.getPulse();


        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues values = new ContentValues();

          //  values.put("_id", id);
            values.put("pulse", pulse);
            values.put("diastolic", diastolic);
            values.put("systolic", systolic);
            values.put("title", title);
            values.put("timestamp", timestamp);


            db.update("blood_pressure", values, " _id = ? ", new String[]{String.valueOf(select_id)});
            db.close();
        }

    }

    //得到所有Hook配置
    public ArrayList<BloodBean> getAllBlood(){
        ArrayList<BloodBean> bloodBeans = new ArrayList<>();

        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor c = db.query("blood_pressure", new String[]{"_id","pulse","diastolic","systolic","title","timestamp"}, null, null, null, null, null,null);
            while(c.moveToNext()){
                int ID = c.getInt(0);
                int pulse = c.getInt(1);
                int diastolic = c.getInt(2);
                int systolic = c.getInt(3);
                String title = c.getString(4);
                String timestamp = c.getString(5);


                bloodBeans.add(new BloodBean(ID,pulse,diastolic,systolic,timestamp,title));
            }
            c.close();
            db.close();
        }
        return bloodBeans;
    }

    /*public ArrayList<AppInfo> findByPgName(String pgName){
        ArrayList<AppInfo> AppHookInfo = new ArrayList<AppInfo>();
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor c = db.query("HookAppInfo", new String[]{"hook_app_AppName","hook_app_AppImage","hook_app_pgName","hook_app_class","hook_app_modelName","hook_app_canshu","hook_app_return","hook_app_BZname","_id"}, "hook_app_pgName=?",new String[]{pgName}, null, null, null);
            while(c.moveToNext()){
                String hook_app_AppName = c.getString(0);
                byte[]  in = c.getBlob(1);
                Bitmap hook_app_AppImage = BitmapFactory.decodeByteArray(in, 0, in.length);
                String hook_app_pgName = c.getString(2);
                String hook_app_class = c.getString(3);
                String hook_app_modelName = c.getString(4);
                String hook_app_canshu = c.getString(5);
                String hook_app_return = c.getString(6);
                String hook_app_BZname = c.getString(7);
                int ID = c.getInt(8);
                AppHookInfo.add(new AppInfo(hook_app_pgName,hook_app_AppName,hook_app_AppImage,hook_app_class,hook_app_modelName,hook_app_return,hook_app_canshu,hook_app_BZname,ID));
            }
            c.close();
            db.close();
        }
        return AppHookInfo;
    }
    public ArrayList<AppInfo> findAppName(Boolean distinct){
        ArrayList<AppInfo> AppHookInfo = new ArrayList<AppInfo>();
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor c = db.query(distinct,"HookAppInfo", new String[]{"hook_app_AppName","hook_app_AppImage","hook_app_pgName"}, null, null, null, null, null,null);
            while(c.moveToNext()){
                String hook_app_AppName = c.getString(0);
                byte[]  in = c.getBlob(1);
                Bitmap hook_app_AppImage = BitmapFactory.decodeByteArray(in, 0, in.length);
                String hook_app_pgName = c.getString(2);
                AppHookInfo.add(new AppInfo(hook_app_pgName,hook_app_AppName,hook_app_AppImage,null,null,null,null,null));
            }
            c.close();
            db.close();
        }
        return AppHookInfo;
    }*/

    public void deleteBloodById(int id){
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        int result = db.delete("blood_pressure", "_id=?", new String[]{String.valueOf(id)});
    }

}

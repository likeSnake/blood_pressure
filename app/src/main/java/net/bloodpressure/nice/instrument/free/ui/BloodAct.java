package net.bloodpressure.nice.instrument.free.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import net.bloodpressure.nice.instrument.free.App;
import net.bloodpressure.nice.instrument.free.Db.ConfigDao;
import net.bloodpressure.nice.instrument.free.R;
import net.bloodpressure.nice.instrument.free.ad.InterstitialAd;
import net.bloodpressure.nice.instrument.free.bean.BloodBean;
import net.bloodpressure.nice.instrument.free.util.MyUtil;

import java.io.Serializable;
import java.util.Calendar;

public class BloodAct extends AppCompatActivity implements View.OnClickListener{
    private EditText systolic_input,diastolic_input,pulse_input;

    private TextView t_year, t_month, t_hour, t_minute, prompt, title;
    private ImageView level,indicator,delete,close;
    private float startLocation = 0.0f;
    private Button add_save;
    private String pulse;
    private String diastolic;
    private String systolic;
    private Boolean isAdd = true;
    private int bloodId;
    private String time;
    private Dialog dialog;
    private String prompt_text ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_blood);

        initLoading();
        initUi();
        initDate();
        initListener();
    }


    public void initUi(){
        systolic_input = findViewById(R.id.systolic_input);
        diastolic_input = findViewById(R.id.diastolic_input);
        pulse_input = findViewById(R.id.pulse_input);
        prompt = findViewById(R.id.prompt);
        title = findViewById(R.id.add_title);
        level = findViewById(R.id.level);
        indicator = findViewById(R.id.indicator);
        t_year = findViewById(R.id.year);
        t_month = findViewById(R.id.month);
        t_hour = findViewById(R.id.hour);
        t_minute = findViewById(R.id.minute);
        add_save = findViewById(R.id.add_save);
        delete = findViewById(R.id.delete);
        close = findViewById(R.id.close);
    }

    @SuppressLint("SetTextI18n")
    public void initDate(){
        setTime();

    }

    public void setTime(){
        Serializable bl = getIntent().getSerializableExtra("bloodBean");

        if (bl!=null){
            isAdd = false;
            prompt_text = "The deletion was successful !";
            setData((BloodBean) bl);
        }else {
            isAdd = true;
            prompt_text = "Save successfully !";
            delete.setVisibility(View.GONE);

            Calendar c = Calendar.getInstance();
            String year = c.get(Calendar.YEAR)+"";
            String month = (c.get(Calendar.MONTH)+1)+"";
            String day = c.get(Calendar.DAY_OF_MONTH)+"";
            String hour = c.get(Calendar.HOUR_OF_DAY)+"";
            String minute = c.get(Calendar.MINUTE)+"";


            t_year.setText(year);
            t_month.setText(month+"/"+day);
            t_hour.setText(hour);
            t_minute.setText(minute);
        }
    }

    public void setData(BloodBean b){
        delete.setVisibility(View.VISIBLE);

        String timestamp = b.getTimestamp();
        time = timestamp;

        int diastolic = b.getDiastolic();
        int pulse = b.getPulse();
        String t = b.getTitle();
        int systolic = b.getSystolic();
        bloodId = b.getId();


        title.setText(t);

        systolic_input.setText(String.valueOf(systolic));
        diastolic_input.setText(String.valueOf(diastolic));
        pulse_input.setText(String.valueOf(pulse));




        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(timestamp));

        String year = c.get(Calendar.YEAR)+"";
        String month = (c.get(Calendar.MONTH)+1)+"";
        String day = c.get(Calendar.DAY_OF_MONTH)+"";
        String hour = c.get(Calendar.HOUR_OF_DAY)+"";
        String minute = c.get(Calendar.MINUTE)+"";


        t_year.setText(year);
        t_month.setText(month+"/"+day);
        t_hour.setText(hour);
        t_minute.setText(minute);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    runOnUiThread(()->{
                        diastolicImagePosition(String.valueOf(systolic), 220f);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();



      //  diastolicImagePosition(String.valueOf(systolic), 220f);
    }

    public void initListener(){
        add_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdd){
                    saveDate();
                }else {
                    updateBlood();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBlood();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        systolic_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_NULL) {

                    if (!v.getText().toString().equals("")) {
                        String sy = v.getText().toString();
                        systolic = sy;
                        diastolicImagePosition(sy, 220f);

                 //   return true;
                  }

                }
                return false;
            }

        });

        diastolic_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_NULL) {
                    if (!v.getText().toString().equals("")) {
                        String p = v.getText().toString();
                        diastolic = p;

                        diastolicImagePosition(p, 180f);

                    }
                }
                return false;

            }

        });
    }
    @SuppressLint("SetTextI18n")
    private void diastolicImagePosition(String diastolicText,float max) {
        int diastolicPressure = Integer.parseInt(diastolicText);
        if (diastolicPressure < 60) {

            prompt.setText("Systolic < 90, or diastolic < 60");
            title.setText("Low Blood Pressure");

        } else if (diastolicPressure <= 80) {

            prompt.setText("Systolic < 90-120, or Diastolic 60-80");
            title.setText("Low Blood Pressure");

        } else if (diastolicPressure <= 100) {

            prompt.setText("Systolic < 121-129, or Diastolic 80-84");
            title.setText("Low Blood Pressure");
        } else if (diastolicPressure <= 120) {

            prompt.setText("Systolic < 130-139, or Diastolic 85-89");
            title.setText("Hypertension Severe");

        } else if (diastolicPressure < 160) {

            prompt.setText("Systolic >180, or Diastolic 90-119");
            title.setText("Hypertension Severe");

        } else {

            prompt.setText("Systolic < 90, or Diastolic >= 120");
            title.setText("Hypertension Severe");

        }

        regulateIndex((float) (diastolicPressure/max));


    }


    public void regulateIndex(float percentage){
        System.out.println(level.getWidth());

        AnimationSet animationSet = new AnimationSet(true);
        float i = (float)(indicator.getWidth() / level.getWidth())-0.5f;

        MyUtil.MyLog(i);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, startLocation,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF, i*percentage,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF,0.0f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF,0.0f);

        //3秒完成动画
        translateAnimation.setDuration(2000);
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        animationSet.setFillAfter(true);

        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(translateAnimation);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                startLocation = i*percentage;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //启动动画
        level.startAnimation(animationSet);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public void updateBlood(){
        if (!diastolic_input.getText().toString().equals("")&&!systolic_input.getText().toString().equals("")&&!pulse_input.getText().toString().equals("")){

            dialog.show();

            ConfigDao configDao = new ConfigDao(BloodAct.this);
            configDao.update(bloodId,new BloodBean(Integer.parseInt(pulse_input.getText().toString()), Integer.parseInt(diastolic_input.getText().toString()), Integer.parseInt(systolic_input.getText().toString()),time, title.getText().toString()));



            loadAd();
        }
    }
    public void saveDate(){
        if (!diastolic_input.getText().toString().equals("")&&!systolic_input.getText().toString().equals("")&&!pulse_input.getText().toString().equals("")){
            dialog.show();

            String TimeMillis = String.valueOf(System.currentTimeMillis());
            ConfigDao configDao = new ConfigDao(BloodAct.this);
            configDao.add(new BloodBean(Integer.parseInt(pulse_input.getText().toString()), Integer.parseInt(diastolic_input.getText().toString()), Integer.parseInt(systolic_input.getText().toString()), TimeMillis, title.getText().toString()));



            loadAd();
        }

    }
    public void deleteBlood(){
        dialog.show();
        ConfigDao configDao = new ConfigDao(BloodAct.this);
        configDao.deleteBloodById(bloodId);
        loadAd();
    }

    public void initLoading(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_simple);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        AVLoadingIndicatorView id = dialog.findViewById(R.id.avi);
        id.show();


    }

    public void loadAd(){
        InterstitialAd ad = new InterstitialAd(BloodAct.this, new App.OnShowAdCompleteListener() {
            @Override
            public void onShowAdComplete() {

            }

            @Override
            public void TurnoffAds() {
                startActivity(new Intent(BloodAct.this,MainAct.class));
                finish();
                MyUtil.MyToast(BloodAct.this,prompt_text);
            }

            @Override
            public void onFailedToLoad() {
                MyUtil.MyLog("加载失败");
                startActivity(new Intent(BloodAct.this,MainAct.class));
                finish();
                MyUtil.MyToast(BloodAct.this,prompt_text);
            }

            @Override
            public void onAdFailedToShow() {
                MyUtil.MyLog("显示失败");
                startActivity(new Intent(BloodAct.this,MainAct.class));
                finish();
                MyUtil.MyToast(BloodAct.this,prompt_text);
            }

            @Override
            public void onAdLoaded() {
                InterstitialAd.showAd();
            }

            @Override
            public void onClicked() {

            }
        });

        ad.loadAd();
    }
}
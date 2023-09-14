package net.bloodpressure.nice.instrument.free.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import net.bloodpressure.nice.instrument.free.R;
import net.bloodpressure.nice.instrument.free.util.MyUtil;
import net.bloodpressure.nice.instrument.free.util.TimerUtil;

import java.util.Arrays;

public class StartAct extends AppCompatActivity {
    private ProgressBar progressBar;
    private TimerUtil t;
    private int progressStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initUi();
        startTimer();

    }

    public void initUi(){
        progressBar = findViewById(R.id.progressBar);

    }

    public void startTimer(){
        //lodProgressBar.setVisibility(View.VISIBLE);
        final boolean[] loadFirst = {true};
        t = new TimerUtil(10, 100, new TimerUtil.TimerListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                progressStatus++;
                progressBar.setProgress(progressStatus);
                if (progressStatus>=30&& loadFirst[0]){
                    loadFirst[0] = false;
                //    loadOpenAd();
                    startAct();
                }

            }

            @Override
            public void onFinish() {
                MyUtil.MyLog("加载超时");
                startAct();

            }
        });
        t.start();
    }
    public void startAct() {
        t.cancel();
        startActivity(new Intent(StartAct.this, MainAct.class));
        finish();

    }

}
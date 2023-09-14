package net.bloodpressure.nice.instrument.free.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import net.bloodpressure.nice.instrument.free.R;

public class ProtocolAct extends AppCompatActivity implements View.OnClickListener{

    private ImageView more_clean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_protocol);
        initUI();
        initListener();
    }

    public void initUI(){
        more_clean = findViewById(R.id.more_clean);

    }

    public void initListener(){
        more_clean.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.more_clean){
            finish();
        }

    }
}
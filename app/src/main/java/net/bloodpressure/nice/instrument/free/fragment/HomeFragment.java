package net.bloodpressure.nice.instrument.free.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.bloodpressure.nice.instrument.free.Db.ConfigDao;
import net.bloodpressure.nice.instrument.free.Db.DBHelper;
import net.bloodpressure.nice.instrument.free.R;
import net.bloodpressure.nice.instrument.free.adapter.BloodListAdapter;
import net.bloodpressure.nice.instrument.free.bean.BloodBean;
import net.bloodpressure.nice.instrument.free.ui.BloodAct;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ConstraintLayout layout_none;
    private ConstraintLayout layout;
    private Button none;
    private ImageView start_add;
    private TextView systolic_text,diastolic_text,pulse_text;
    private RecyclerView blood_recycler;
    private BloodListAdapter bloodListAdapter;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView(rootView);
        initDate();
        initListener();
        return rootView;
    }

    public void initView(View rootView) {
        layout_none = rootView.findViewById(R.id.layout_none);
        none = rootView.findViewById(R.id.bt_none);
        layout = rootView.findViewById(R.id.layout);
        systolic_text = rootView.findViewById(R.id.systolic_text);
        diastolic_text = rootView.findViewById(R.id.diastolic_text);
        pulse_text = rootView.findViewById(R.id.pulse_text);
        blood_recycler = rootView.findViewById(R.id.blood_recycler);
        start_add = rootView.findViewById(R.id.start_add);

    }

    public void initDate() {
        getBloodInfo();
    }

    public void getBloodInfo(){
        ConfigDao dao = new ConfigDao(context);
        ArrayList<BloodBean> allBlood = dao.getAllBlood();
        if (allBlood.isEmpty()){
            layout_none.setVisibility(View.VISIBLE);
        }else {
            layout_none.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);

            int allSystolic=0;
            int allDiastolic=0;
            int allPulse=0;


            for (BloodBean bloodBean : allBlood) {
                allSystolic+=bloodBean.getSystolic();
                allDiastolic+=bloodBean.getDiastolic();
                allPulse+=bloodBean.getPulse();

            }

            systolic_text.setText(String.valueOf(allSystolic/allBlood.size()));
            diastolic_text.setText(String.valueOf(allDiastolic/allBlood.size()));
            pulse_text.setText(String.valueOf(allPulse/allBlood.size()));

            start(false,allBlood);
        }


    }

    public void initListener() {
        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, BloodAct.class));
            }
        });
        start_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, BloodAct.class));
            }
        });
    }


    public void start(Boolean b, ArrayList<BloodBean> list){
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        bloodListAdapter = new BloodListAdapter(list,context);
        if(b){
            blood_recycler.scrollToPosition(bloodListAdapter.getItemCount()-1);
        }
        blood_recycler.setLayoutManager(manager);
        blood_recycler.setAdapter(bloodListAdapter);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getBloodInfo();
    }
}
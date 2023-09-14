package net.bloodpressure.nice.instrument.free.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import net.bloodpressure.nice.instrument.free.R;
import net.bloodpressure.nice.instrument.free.ui.ProtocolAct;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private Button more;


    public SettingsFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        initView(rootView);
        initDate();
        initListener();
        return rootView;
    }

    public void initView(View rootView) {
        more = rootView.findViewById(R.id.more);

    }

    public void initDate() {

    }

    public void initListener() {
        more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.more){
            startActivity(new Intent(context,ProtocolAct.class));
        }
    }
}
package net.bloodpressure.nice.instrument.free.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.bloodpressure.nice.instrument.free.R;
import net.bloodpressure.nice.instrument.free.bean.BloodBean;
import net.bloodpressure.nice.instrument.free.ui.BloodAct;


import java.util.Date;
import java.util.List;

public class BloodListAdapter extends RecyclerView.Adapter<BloodListAdapter.ViewHolder> {

    private List<BloodBean> list;
    private Context context;

    public BloodListAdapter(List<BloodBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BloodBean BloodBean = list.get(position);
        int diastolic = BloodBean.getDiastolic();
        holder.diastolic.setText(String.valueOf(diastolic));

        int pulse = BloodBean.getPulse();
        holder.pulse.setText(String.valueOf(pulse));
        int systolic = BloodBean.getSystolic();

        String timestamp = BloodBean.getTimestamp();

        Date date = new Date(Long.parseLong(timestamp));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat h = new SimpleDateFormat("HH:mm");
        String formattedDate = sdf.format(date);
        String hour = h.format(date);

        holder.time.setText(formattedDate);
        holder.hour.setText(hour);

        holder.systolic.setText(String.valueOf(systolic));
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), BloodAct.class);
            intent.putExtra("bloodBean",BloodBean);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView diastolic, systolic, pulse,time,hour;
        private ImageView edit;

        public ViewHolder(View view) {
            super(view);
            diastolic = view.findViewById(R.id.diastolic_item);
            systolic = view.findViewById(R.id.systolic_item);
            pulse = view.findViewById(R.id.pulse_item);
            edit = view.findViewById(R.id.rcv_edit);
            time = view.findViewById(R.id.rcv_time);
            hour = view.findViewById(R.id.rcv_hour);
        }
    }
}

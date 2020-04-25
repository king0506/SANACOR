package com.example.sanacorapp.SensorAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sanacorapp.Model01.sensor;
import com.example.sanacorapp.R;

import java.util.List;

public class SensorAdapter extends ArrayAdapter<sensor> {

    private List<sensor> list_of_sensors;
    private Context mCtx;

    public SensorAdapter(List<sensor> S , Context c)
    {
        super(c, R.layout.list_sensors_reading, S);
        this.list_of_sensors = S;
        this.mCtx = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.list_sensors_reading, null, true);

        TextView tv_gp = (TextView) view.findViewById(R.id.tv_gp);
        TextView tv_tt = (TextView) view.findViewById(R.id.tv_tt);
        TextView tv_temp = (TextView) view.findViewById(R.id.tv_temp);
        TextView tv_ph = (TextView) view.findViewById(R.id.tv_ph);
        TextView tv_sal = (TextView) view.findViewById(R.id.tv_sal);
        TextView tv_turb = (TextView) view.findViewById(R.id.tv_turb);
        TextView tv_do = (TextView) view.findViewById(R.id.tv_do);

        sensor sens = list_of_sensors.get(position);
        tv_gp.setText(sens.getS_gp());
        tv_tt.setText(sens.getS_tt());
        tv_temp.setText(sens.getS_temp());
        tv_ph.setText(sens.getS_ph());
        tv_sal.setText(sens.getS_sal());
        tv_turb.setText(sens.getS_turb());
        tv_do.setText(sens.getS_do());

        return view;
    }
}

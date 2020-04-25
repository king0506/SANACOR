package com.example.sanacorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sanacorapp.LoadingScreen.loadScreen;
import com.example.sanacorapp.Model01.sensor;
import com.example.sanacorapp.SensorAdapter.SensorAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class _dash extends AppCompatActivity {
    String ip_load , ports;
     int x = 0, y = 0 , z=0, c=0 , vs=0;
    TextView host_port_t, txt_temp , txt_ph, txt_sal, txt_turb, txt_do;
    TextView cur_temp  , label_temp, cur_ph  , label_ph, cur_sal  , label_sal, cur_turb  , label_turb , cur_do  , label_do;
    ImageView tempUp , tempDown, phUp , phDown, salUp , salDown , turbUp , turbDown, doUp , doDown;
    BarChart barChart;
    LinearLayout mlayout,mlayout2, trg1, trg2, trg3, trg4, trg5;
    LayoutInflater layoutInflater, layoutInflater2;
    Button btn1 , btn2 ,btn3 , btn4, btn_view_log, btn_tbl_temp, btn_tbl_ph,btn_tbl_sal,btn_tbl_turb,btn_tbl_do;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__dash);



        //host_port_t
        host_port_t = (TextView)findViewById(R.id.host_port_t);

        getTempStats();
        getPHStats();
        getSALStats();

        btn_view_log = (Button)findViewById(R.id.btn_view_log);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);


        mlayout2 = (LinearLayout)findViewById(R.id.mlayout2);
        mlayout = (LinearLayout)findViewById(R.id.mlayout);
        tbl_img();
        layoutInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        View myview = layoutInflater.inflate(R.layout.main_table_info, null, false);
        txt_temp = (TextView) myview.findViewById(R.id.txt_temp);
        txt_ph = (TextView) myview.findViewById(R.id.txt_ph);
        txt_sal = (TextView) myview.findViewById(R.id.txt_sal);
        txt_turb = (TextView) myview.findViewById(R.id.txt_turb);
        txt_do = (TextView) myview.findViewById(R.id.txt_do);


        label_temp = (TextView) myview.findViewById(R.id.label_temp);
        cur_temp = (TextView) myview.findViewById(R.id.cur_temp);
        tempUp = (ImageView) myview.findViewById(R.id.tempUp);
        tempDown = (ImageView) myview.findViewById(R.id.tempDown);


        label_ph = (TextView) myview.findViewById(R.id.label_ph);
        cur_ph = (TextView) myview.findViewById(R.id.cur_ph);
        phUp = (ImageView) myview.findViewById(R.id.phUp);
        phDown = (ImageView) myview.findViewById(R.id.phDown);

        label_sal = (TextView) myview.findViewById(R.id.label_sal);
        cur_sal = (TextView) myview.findViewById(R.id.cur_sal);
        salUp = (ImageView) myview.findViewById(R.id.salUp);
        salDown = (ImageView) myview.findViewById(R.id.salDown);



        btn_tbl_temp = (Button) myview.findViewById(R.id.btn_tbl_temp);
        btn_tbl_ph = (Button) myview.findViewById(R.id.btn_tbl_ph);
        btn_tbl_sal = (Button) myview.findViewById(R.id.btn_tbl_sal);
        btn_tbl_turb = (Button) myview.findViewById(R.id.btn_tbl_turb);
        btn_tbl_do = (Button) myview.findViewById(R.id.btn_tbl_do);

        trg1 = (LinearLayout)myview.findViewById(R.id.trg1);
        trg2 = (LinearLayout)myview.findViewById(R.id.trg2);
        trg3 = (LinearLayout)myview.findViewById(R.id.trg3);
        trg4 = (LinearLayout)myview.findViewById(R.id.trg4);
        trg5 = (LinearLayout)myview.findViewById(R.id.trg5);
        mlayout.addView(myview);

        btn_tbl_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTempStats();

                if(x==0)
                {
                    x = 1;
                    txt_temp.setText("DAY");
                }
                else if(x==1)
                {
                    x++;
                    txt_temp.setText("MONTH");
                }
                else if (txt_temp.getText().equals("MONTH"))
                {
                    x = 0;
                    txt_temp.setText("WEEK");
                }


            }
        });

        btn_tbl_ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txt_ph
                getPHStats();
                if(y==0)
                {
                    y = 1;
                    txt_ph.setText("DAY");
                }
                else if(y==1)
                {
                    y++;
                    txt_ph.setText("MONTH");
                }
                else if (txt_ph.getText().equals("MONTH"))
                {
                    y = 0;
                    txt_ph.setText("WEEK");
                }

            }
        });

        btn_tbl_sal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txt_ph
                getSALStats();
                if(z==0)
                {
                    z = 1;
                    txt_sal.setText("DAY");
                }
                else if(z==1)
                {
                    z++;
                    txt_sal.setText("MONTH");
                }
                else if (txt_sal.getText().equals("MONTH"))
                {
                    z = 0;
                    txt_sal.setText("WEEK");
                }

            }
        });

        btn_tbl_turb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txt_ph
                if(c==0)
                {
                    c = 1;
                    txt_turb.setText("DAY");
                }
                else if(c==1)
                {
                    c++;
                    txt_turb.setText("MONTH");
                }
                else if (txt_turb.getText().equals("MONTH"))
                {
                    c = 0;
                    txt_turb.setText("WEEK");
                }

            }
        });

        btn_tbl_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txt_ph
                if(vs==0)
                {
                    vs = 1;
                    txt_do.setText("DAY");
                }
                else if(vs==1)
                {
                    vs++;
                    txt_do.setText("MONTH");
                }
                else if (txt_do.getText().equals("MONTH"))
                {
                    vs = 0;
                    txt_do.setText("WEEK");
                }

            }
        });

        btn_view_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent invokeLoadScren = new Intent(_dash.this , _list_of_reading.class);
                startActivity(invokeLoadScren);

            }
        });


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip_shared = preferences.getString("shared_ip", "defaultValue");
        String port_shared = preferences.getString("shared_port", "defaultValue");

        ip_load = ip_shared;
        ports = port_shared;


        host_port_t.setText(ip_load +" : "+ ports);

     //   BarDataSet barDataSet1 = new BarDataSet(dataValues1() , "Datas");
     //   BarData barData = new BarData();
     //   barData.addDataSet(barDataSet1);

     //   barChart.setData(barData);
     //   barChart.invalidate();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent invokeLoadScren = new Intent(getApplicationContext() , _data_aqc.class);
                startActivity(invokeLoadScren);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent invokeConfig = new Intent(getApplicationContext() , _pond_control.class);
                startActivity(invokeConfig);
            }
        });


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent invokeConfig = new Intent(getApplicationContext() , _config.class);
                startActivity(invokeConfig);
            }
        });

    }

    private void tbl_img()
    {
        layoutInflater2 = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View myview2 = layoutInflater2.inflate(R.layout.main_img_table, null, false);

        mlayout2.addView(myview2);
    }

    private ArrayList<BarEntry>dataValues1(){
        ArrayList<BarEntry>dataVals = new ArrayList<BarEntry>();
        dataVals.add(new BarEntry(1,27));
        dataVals.add(new BarEntry(2,42));
        dataVals.add(new BarEntry(3,75));
        dataVals.add(new BarEntry(4,10));
        dataVals.add(new BarEntry(5,18));
        return dataVals;
    }

    private void getTempVal()
    {
        String path_rcv_cons_pref = "http://"+"192.168.1.4"+"/z/percent_rate_temp.php";
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, path_rcv_cons_pref ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONObject object = new JSONObject(String.valueOf(response));
                            JSONArray jsonArray = object.getJSONArray("v");
                            for(int i = 0; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                               String ff = jsonObject.getString("temp");
                                label_temp.setText(ff);
                            }
                        }
                        catch (JSONException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }});
        Handler.getInstance(getApplicationContext()).addToRequestQueue(objectRequest);

    }

    private void getTempStats()
    {
        String path_rcv_cons_pref = "http://"+"192.168.254.80"+"/z/temp_stats.php";
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, path_rcv_cons_pref ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONObject object = new JSONObject(String.valueOf(response));
                            JSONArray jsonArray = object.getJSONArray("v");

                                JSONObject jsonObject_now = jsonArray.getJSONObject(0);
                                JSONObject jsonObject_recent = jsonArray.getJSONObject(1);
                                String nn = jsonObject_recent.getString("temp");
                                String rr = jsonObject_now.getString("temp");

                                double a = Double.parseDouble(nn) - Double.parseDouble(rr);

                                double _nn = Double.parseDouble(rr);
                                double _rr = Double.parseDouble(nn);

                                if(_rr > _nn)
                                {
                                    tempDown.setVisibility(View.VISIBLE);
                                    tempUp.setVisibility(View.GONE);
                                }
                                else
                                {
                                    tempDown.setVisibility(View.GONE);
                                    tempUp.setVisibility(View.VISIBLE);
                                }
                                label_temp.setText( rr +"   ["+ nn +"]");
                                cur_temp.setText(a + "%");
                        }
                        catch (JSONException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }});
        Handler.getInstance(getApplicationContext()).addToRequestQueue(objectRequest);

    }

    private void getPHStats()
    {
        String path_rcv_cons_pref = "http://"+"192.168.254.80"+"/z/ph_stats.php";
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, path_rcv_cons_pref ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONObject object = new JSONObject(String.valueOf(response));
                            JSONArray jsonArray = object.getJSONArray("v");

                            JSONObject jsonObject_now = jsonArray.getJSONObject(1);
                            JSONObject jsonObject_recent = jsonArray.getJSONObject(0);
                            String nn = jsonObject_recent.getString("ph");
                            String rr = jsonObject_now.getString("ph");

                            double a = Double.parseDouble(nn) - Double.parseDouble(rr);

                            double _nn = Double.parseDouble(rr);
                            double _rr = Double.parseDouble(nn);

                            if(_rr > _nn)
                            {
                                phDown.setVisibility(View.GONE);
                                phUp.setVisibility(View.VISIBLE);
                                cur_ph.setTextColor(Color.GREEN);
                            }
                            else
                            {
                                phDown.setVisibility(View.VISIBLE);
                                phUp.setVisibility(View.GONE);
                                cur_ph.setTextColor(Color.RED);
                            }
                            label_ph.setText( rr +"   ["+ nn +"]");
                            cur_ph.setText(a + "%");
                        }
                        catch (JSONException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }});
        Handler.getInstance(getApplicationContext()).addToRequestQueue(objectRequest);

    }

    private void getSALStats()
    {
        String path_rcv_cons_pref = "http://"+"192.168.254.80"+"/z/sal_stats.php";
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, path_rcv_cons_pref ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONObject object = new JSONObject(String.valueOf(response));
                            JSONArray jsonArray = object.getJSONArray("v");

                            JSONObject jsonObject_now = jsonArray.getJSONObject(0);
                            JSONObject jsonObject_recent = jsonArray.getJSONObject(1);
                            String nn = jsonObject_recent.getString("sal");
                            String rr = jsonObject_now.getString("sal");

                            double a = Double.parseDouble(nn) - Double.parseDouble(rr);

                            double _nn = Double.parseDouble(rr);
                            double _rr = Double.parseDouble(nn);

                            if(_rr > _nn)
                            {
                                salDown.setVisibility(View.VISIBLE);
                                salUp.setVisibility(View.GONE);
                                cur_sal.setTextColor(Color.RED);
                            }
                            else
                            {
                                salDown.setVisibility(View.GONE);
                                salUp.setVisibility(View.VISIBLE);
                                cur_sal.setTextColor(Color.GREEN);
                            }
                            label_sal.setText( rr +"   ["+ nn +"]");
                            cur_sal.setText(a + "%");
                        }
                        catch (JSONException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }});
        Handler.getInstance(getApplicationContext()).addToRequestQueue(objectRequest);

    }

}

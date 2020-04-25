package com.example.sanacorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.sanacorapp.spinner_for_gplist.Downloader;
import com.example.sanacorapp.spinner_for_pond_title.Downloader_2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class _data_aqc extends AppCompatActivity {

    RequestQueue requestQueue;

    //init widget
    TextView t1,t2,t3,t4,t5;
    LinearLayout lc_searching, lc_error , lc_head;

    //init String
    String ip_ , command, datas_rcv , ip_load;
    String ip_shared;
    String port_shared;

    int ports;

    //init for TCP
    static Socket ss;
    InputStreamReader in;
    Handler handler = new Handler();
    Thread inThread;
    DataOutputStream dos;


    //init for database content
   // Spinner sp, sp2;

    //Shared Content
    TextView ipshared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__data_aqc);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Shared Pref...
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip_shared = preferences.getString("shared_ip", "defaultValue");
        port_shared = preferences.getString("shared_port", "defaultValue");

        inThread = new Thread(new SereverThread());
        inThread.start();

        ipshared = (TextView)findViewById(R.id.ipshared);
        t1 = (TextView)findViewById(R.id.temp_l);
        t2 = (TextView)findViewById(R.id.ph_l);
        t3 = (TextView)findViewById(R.id.sal_l);
        t4 = (TextView)findViewById(R.id.turb_l);
        t5 = (TextView)findViewById(R.id.do_l);

        lc_searching = (LinearLayout) findViewById(R.id.lc_searching);
        lc_error = (LinearLayout) findViewById(R.id.lc_errors);
        lc_head = (LinearLayout) findViewById(R.id.head_panel);
        ipshared.setText(String.format("%s : %s", ip_shared, port_shared));



        //TCP start


    }

    //TCP Rcv Con
    class SereverThread implements Runnable {
        String a;
        @Override
        public void run() {
            try
            {
                ss = new Socket( ip_shared , Integer.parseInt(port_shared));   // -------------- REMEMBER COMMAND HERE
                lc_searching.setVisibility(View.VISIBLE);
                lc_error.setVisibility(View.GONE);

                while (true)
                {
                    in = new InputStreamReader(ss.getInputStream());
                    BufferedReader bf;  bf = new BufferedReader(in);
                    datas_rcv = bf.readLine();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            // load_in(datas_rcv);
                            dataSycn(datas_rcv);
                            if(datas_rcv.contains("x"))
                            {
                                ipshared.setText("Rcv :" + datas_rcv);
                                lc_head.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
            catch (ExceptionInInitializerError ex)
            {
                ex.printStackTrace();
            }
        }

    }

    class BGTask extends AsyncTask<String , Void , String>
    {


        @Override
        protected String doInBackground(String... params) {
           String ip_s = params[0];
           String command_s = params[1];
            try
            {
                ss = new Socket(ip_s, Integer.parseInt(port_shared)); // ip_shared ,
                dos = new DataOutputStream(ss.getOutputStream());
                dos.writeBytes(StringFormatter.convertStringToUTF8(command_s));

                dos.close();
                ss.close();
            }
            catch (IOException e)
            {
                return null;
            }

            return null;
        }
    }


    public void dataSycn(String s)
    {
        if(s.contains("ps1"))
        {
            ipshared.setText("MADMAX");
            Toast.makeText(getApplicationContext(), "Here ps1 code", Toast.LENGTH_LONG).show();
        }
        if(s.contains("ps2"))
        {
            ipshared.setText("ddd");
            Toast.makeText(getApplicationContext(), "Here ps2 code", Toast.LENGTH_LONG).show();
        }
        if(s.contains("ps3"))
        {
            Toast.makeText(getApplicationContext(), "Here ps3 code", Toast.LENGTH_LONG).show();
        }
        if(s.contains("ps4"))
        {
            Toast.makeText(getApplicationContext(), "Here ps4 code", Toast.LENGTH_LONG).show();
        }
        if(s.contains("ps5"))
        {
            Toast.makeText(getApplicationContext(), "Here ps5 code", Toast.LENGTH_LONG).show();
        }

        if(s.contains("vxvxcv"))
        {
            Toast.makeText(getApplicationContext(), "Here 0-x-5 code", Toast.LENGTH_LONG).show();
        }

        if(s.contains("online"))
        {

        }
        if(s.contains("ps6"))
        {
            Toast.makeText(getApplicationContext(), "Here ps6 code", Toast.LENGTH_LONG).show();
        }
    }


    //Method for database content

    private void inserData(){

        StringRequest request = new StringRequest(Request.Method.POST,"http://"+"192.168.1.101"+"/dxx/insertStudent.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> parameters = new HashMap<String, String>();
                parameters.put("fname", "Kizzie Mae");
                parameters.put("lname", "Gonato");
                parameters.put("age", "20");
                Toast.makeText(getApplicationContext(), "All Sensors Inserted", Toast.LENGTH_SHORT).show();
                return parameters;
            }
        };
        requestQueue.add(request);
    }


    public void ins_temp(View v)
    {
        try{
            inserData();
            Toast.makeText(getApplicationContext(), "Data Inserted".toString(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            ex.getStackTrace();
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //-------------------TCP Command


    public void CommandData(String s)
    {
        BGTask b = new BGTask();
        b.execute( ip_shared , s);  // -------------- REMEMBER COMMAND HERE
    }

    public void _btn_temp(View v)
    {
        CommandData("TEMP");
    }

    public void _btn_ph(View v)
    {
        CommandData("PH");
    }

    public void _btn_sal(View v)
    {
        CommandData("SAL");
    }

    public void _btn_turb(View v)
    {
        CommandData("TURB");
    }

    public void _btn_do(View v)
    {
        CommandData("DO");
    }

    public void _btn_trig_all(View v)
    {
        CommandData("ALL SENX");
    }




    private void GetDataReading()
    {
        String t1_s , t2_s, t3_s , t4_s , t5_s;
        t1_s = t1.getText().toString();
        t2_s = t2.getText().toString();
        t3_s = t3.getText().toString();
        t4_s = t4.getText().toString();
        t5_s = t5.getText().toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("t1", t1_s);
        editor.putString("t2", t2_s);
        editor.putString("t3", t3_s);
        editor.putString("t4", t4_s);
        editor.putString("t5", t5_s);
        editor.apply();
    }


    public void saveLog(View v)
    {
        GetDataReading();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String t1_shared = preferences.getString("t1", "defaultValue");
        String t2_shared = preferences.getString("t2", "defaultValue");
        String t3_shared = preferences.getString("t3", "defaultValue");
        String t4_shared = preferences.getString("t4", "defaultValue");
        String t5_shared = preferences.getString("t5", "defaultValue");

        String urlAddress="http://"+"192.168.1.4"+"/z/gp_list.php";
        String urlAddress2="http://"+"192.168.1.4"+"/z/title_list.php";

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(_data_aqc.this);
        View mView = getLayoutInflater().inflate(R.layout.diagsave, null);

        //------------Init Widgets

        final TextView t1s = (TextView) mView.findViewById(R.id.t1);
        final TextView t2s = (TextView) mView.findViewById(R.id.t2);
        final TextView t3s = (TextView) mView.findViewById(R.id.t3);
        final TextView t4s = (TextView) mView.findViewById(R.id.t4);
        final TextView t5s = (TextView) mView.findViewById(R.id.t5);

        t1s.setText(t1_shared);
        t2s.setText(t2_shared);
        t3s.setText(t3_shared);
        t4s.setText(t4_shared);
        t5s.setText(t5_shared);

        Button saving_datas_ = (Button) mView.findViewById(R.id._save_btn);
        Button viewing_datas_ = (Button) mView.findViewById(R.id._view_log_data);

        Spinner sp_ = (Spinner) mView.findViewById(R.id.spinnerGPlist_);
        Spinner sp2_ = (Spinner) mView.findViewById(R.id.spinner_title_list_);
        sp_.setSelection(1);
        sp2_.setSelection(1);

        //-------------Published Spinner Items

        Downloader d=new Downloader(_data_aqc.this,urlAddress,sp_);
        Downloader_2 sd=new Downloader_2(_data_aqc.this,urlAddress2,sp2_);

        d.execute();
        sd.execute();

        //--------------Button Command

        saving_datas_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue_;
                requestQueue_ = Volley.newRequestQueue(getApplicationContext()); //http://localhost/z/insert_all_sens.php
                //StringRequest request = new StringRequest(Request.Method.POST,"http://"+"192.168.1.101"+"/dxx/insertStudent.php", new Response.Listener<String>() {
                StringRequest request = new StringRequest(Request.Method.POST,"http://"+ip_load+"/z/insert_all_sens.php", new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String , String> parameters = new HashMap<String, String>();
                        parameters.put("temp", t1s.getText().toString());
                        parameters.put("ph", t2s.getText().toString());
                        parameters.put("sal", t3s.getText().toString());
                        parameters.put("turb", t4s.getText().toString());
                        parameters.put("dos", t5s.getText().toString());
                        parameters.put("gp", "GP DRIA");
                        parameters.put("title", "TITLE DIRIA");
                        return parameters;
                    }
                };
                requestQueue_.add(request);
                Toast.makeText(getApplicationContext() , "Froie Buang", Toast.LENGTH_LONG).show();
            }
        });

        viewing_datas_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), _list_of_reading.class);
                startActivity(intent);
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    //-----------------------------------------INSERTIONS

    private void insert_single_sensor()
    {
        RequestQueue requestQueue_s;
        requestQueue_s = Volley.newRequestQueue(getApplicationContext()); /// ASLISDAN ANG PATH FUTURE <----- ISA ISA ANG FILE SA PHP INSERTION NA CODE
        StringRequest request = new StringRequest(Request.Method.POST,"http://"+"192.168.1.101"+"/dxx/insertStudent.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> parameters = new HashMap<String, String>();
                parameters.put("fname", "Kizzie Mae");
                parameters.put("lname", "Gonato");
                parameters.put("age", "20");
                return parameters;
            }
        };
        requestQueue_s.add(request);
        Toast.makeText(getApplicationContext() , "Froie Buang", Toast.LENGTH_LONG).show();
    }

    private void insert_all_sensor()
    {
        RequestQueue requestQueue_s;
        requestQueue_s = Volley.newRequestQueue(getApplicationContext()); /// ASLISDAN ANG PATH FUTURE <----- ISA ISA ANG FILE SA PHP INSERTION NA CODE
        StringRequest request = new StringRequest(Request.Method.POST,"http://"+"192.168.1.101"+"/dxx/insertStudent.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> parameters = new HashMap<String, String>();
                parameters.put("fname", "Kizzie Mae");
                parameters.put("lname", "Gonato");
                parameters.put("age", "20");
                return parameters;
            }
        };
        requestQueue_s.add(request);
        Toast.makeText(getApplicationContext() , "Froie Buang", Toast.LENGTH_LONG).show();
    }

}



























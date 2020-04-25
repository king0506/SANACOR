package com.example.sanacorapp;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanacorapp.Model01.sensor;
import com.example.sanacorapp.SensorAdapter.SensorAdapter;
import com.example.sanacorapp.spinner_for_gplist.Downloader;
import com.example.sanacorapp.spinner_for_pond_title.Downloader_2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class _list_of_reading extends AppCompatActivity {
    LinearLayout make_filter;
    Button do_filter;
    TextView datepicker_1,datepicker_2 , jumpdate_t, jumptime_t , ipshared;
    DatePickerDialog Dpicker1,Dpicker2, Dpicker3;
    List<sensor> list_of_sensors;
    ListView listView;
    String thisip ="192.168.1.4";

    String pond_String_holder = "";
    String title_String_holder = "";
    String time_String_holder = "";
    String date_String_holder = "";

    String f_holder_time = "";
    String f_holder_date = "";
    String f_holder_pond = "";
    String f_holder_title = "";


    String  d1 , d2 , gp_StringHolder, title_StringHolder, timeHolder = "";
    int trig1 = 0 , trig2 = 0;

    String date_holder = "sample";
    String URL_preferences_views = "";
    ImageView jumpdate;


     Spinner sp_, sp2_;

    int holder_1 , holder_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__list_of_reading);

        sp_ = (Spinner) findViewById(R.id.spinnerGPlist_01);
        sp2_ = (Spinner) findViewById(R.id.spinner_title_list_01);

        make_filter = (LinearLayout) findViewById(R.id.make_filter);
        do_filter = (Button) findViewById(R.id.do_filter);
        ipshared =(TextView) findViewById(R.id.ipshared);
        jumptime_t =(TextView) findViewById(R.id.jumptime_t);
        jumpdate_t =(TextView) findViewById(R.id.jumpdate_t);
        datepicker_1=(TextView) findViewById(R.id.dtp1);
        datepicker_2=(TextView) findViewById(R.id.dtp2);
        jumpdate = (ImageView) findViewById(R.id.jumpdate);



        listView = (ListView)findViewById(R.id.list_sens);
        //--------------------------------
        list_of_sensors = new ArrayList<>();

        URL_preferences_views = "http://"+thisip+"/z/view_searching.php/?";

        popSpinner();

        datepicker_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                Dpicker1 = new DatePickerDialog(_list_of_reading.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                 datepicker_1.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                 d1 = datepicker_1.getText().toString();
                            }
                        }, year, month, day);
                Dpicker1.show();
            }
        });

        datepicker_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                Dpicker2 = new DatePickerDialog(_list_of_reading.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datepicker_2.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                Dpicker2.show();
            }
        });

        jumpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String a;
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                Dpicker3 = new DatePickerDialog(_list_of_reading.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                jumpdate_t.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                String[] date_cute_on = jumpdate_t.getText().toString().split("-"); //.split(",");
                                String _year = date_cute_on[0];
                                String _month = date_cute_on[1];
                                String _day = date_cute_on[2];
                                //  int x = Integer.parseInt(_month) - 1 ;
                                //  String _get_val = String.valueOf(x) + "-" + _day;
                                String _get_val = _month + "-" + _day;
                                jumptodate_s(_get_val);
                            }
                        }, year, month, day);
                Dpicker3.show();
                list_of_sensors.clear();
                listView.clearTextFilter();


            }
        });

        clear_Filter();

        sp_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(trig1>0){
                    gp_StringHolder = sp_.getSelectedItem().toString(); //string value
                    //  Toast.makeText(getApplicationContext(), gp_StringHolder ,Toast.LENGTH_LONG).show();
                    list_of_sensors.clear();
                    listView.clearTextFilter();
                    getGP(gp_StringHolder);
                }
                else
                {
                    trig1++;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp2_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(trig2>0){
                    holder_2 = sp2_.getSelectedItemPosition() + 1;
                    //title_StringHolder = String.valueOf(holder_2); // indexing
                    title_StringHolder = sp2_.getSelectedItem().toString();
                    //   Toast.makeText(getApplicationContext(), title_StringHolder ,Toast.LENGTH_LONG).show();
                    list_of_sensors.clear();
                    listView.clearTextFilter();
                    getTitle(title_StringHolder);
                }
                else
                {
                    trig2++;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       // showList("1");
    }

    public void make_filter(View v)
    {
        do_filter.setVisibility(View.GONE);
        make_filter.setVisibility(View.VISIBLE);
    }

    public void clearFilter(View v)
    {
        clear_Filter();
    }

    private void clear_Filter()
    {
        do_filter.setVisibility(View.VISIBLE);
        make_filter.setVisibility(View.GONE);
        list_of_sensors.clear();
        listView.clearTextFilter();
        URL_preferences_views = "http://"+thisip+"/z/view_searching.php/?";
        String path_rcv_cons_pref = "http://"+thisip+"/z/view_searching.php";

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
                                sensor s = new sensor(jsonObject.getString("pond_ID"),jsonObject.getString("title_gp"),jsonObject.getString("temp"),jsonObject.getString("ph"), jsonObject.getString("sal"),
                                        jsonObject.getString("turb"), jsonObject.getString("dos"));
                                list_of_sensors.add(s);
                            }
                            SensorAdapter adapter = new SensorAdapter(list_of_sensors, getApplicationContext());
                            listView.setAdapter(adapter);
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


    private void jumptodate_s(String param4)
    {
        //URL_preferences_views     =   http://"+"192.168.1.4"+"/z/view_searching.php
        //http://192.168.1.4/z/view_searching.php/?thisDate=2020-02-27
        String path_rcv_cons_pref = "http://"+thisip+"/z/view_searching.php/?thisDate="+param4;

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
                                sensor s = new sensor(jsonObject.getString("pond_ID"),jsonObject.getString("title_gp"),jsonObject.getString("temp"),jsonObject.getString("ph"), jsonObject.getString("sal"),
                                        jsonObject.getString("turb"), jsonObject.getString("dos"));
                                list_of_sensors.add(s);
                            }
                            SensorAdapter adapter = new SensorAdapter(list_of_sensors, getApplicationContext());
                            listView.setAdapter(adapter);
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

    private void getTitle(String param2)
    {

        String path_rcv_cons_pref = "http://"+thisip+"/z/view_searching.php/?title="+param2;

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
                                sensor s = new sensor(jsonObject.getString("pond_ID"),jsonObject.getString("title_gp"),jsonObject.getString("temp"),jsonObject.getString("ph"), jsonObject.getString("sal"),
                                        jsonObject.getString("turb"), jsonObject.getString("dos"));
                                list_of_sensors.add(s);
                            }
                            SensorAdapter adapter = new SensorAdapter(list_of_sensors, getApplicationContext());
                            listView.setAdapter(adapter);
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

    private void getGP(String param1)
    {

        String path_rcv_cons_pref = "http://"+thisip+"/z/view_searching.php/?gpn="+param1;

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
                                sensor s = new sensor(jsonObject.getString("pond_ID"),jsonObject.getString("title_gp"),jsonObject.getString("temp"),jsonObject.getString("ph"), jsonObject.getString("sal"),
                                        jsonObject.getString("turb"), jsonObject.getString("dos"));
                                list_of_sensors.add(s);
                            }
                            SensorAdapter adapter = new SensorAdapter(list_of_sensors, getApplicationContext());
                            listView.setAdapter(adapter);
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

    private void getVal(String param1, String param2, String param3 , String param4)
    {

        String path_rcv_cons_pref = "http://"+thisip+"/z/view_searching.php/?gpn="+param1+"&&title="+param2+"&&thisTime="+param3+"&&thisDate="+param4;

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
                                sensor s = new sensor(jsonObject.getString("pond_ID"),jsonObject.getString("title_gp"),jsonObject.getString("temp"),jsonObject.getString("ph"), jsonObject.getString("sal"),
                                        jsonObject.getString("turb"), jsonObject.getString("dos"));
                                list_of_sensors.add(s);
                            }
                            SensorAdapter adapter = new SensorAdapter(list_of_sensors, getApplicationContext());
                            listView.setAdapter(adapter);
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


    private void popSpinner()
    {

       // sp_.setSelection(1);
      //  sp2_.setSelection(1);

        //-------------Published Spinner Items
        String urlAddress="http://"+thisip+"/z/gp_list.php";
        String urlAddress2="http://"+thisip+"/z/title_list.php";

        Downloader d=new Downloader(_list_of_reading.this,urlAddress,sp_);
        Downloader_2 sd=new Downloader_2(_list_of_reading.this,urlAddress2,sp2_);

        d.execute();
        sd.execute();

    }


    public void btngetTimeAt5(View v)
    {
        list_of_sensors.clear();
        listView.clearTextFilter();
        getVal("", "","05:","");
    }

    public void btngetTimeAt3(View v)
    {
        list_of_sensors.clear();
        listView.clearTextFilter();
        getVal("", "" , "03:", "");
    }


    public void do_filtering(View v)
    {
        pond_String_holder = "";
        title_String_holder = "";
        time_String_holder = "";
        date_String_holder = "";

        f_holder_time = "";
        f_holder_date = "";
        f_holder_pond = "";
        f_holder_title = "";

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("TEMPERATURE");
        arrayList.add("POTENTIAL HYDROGEN");
        arrayList.add("SALINITY");
        arrayList.add("TURBIDITY");
        arrayList.add("DISSOLVED OXYGEN");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(_list_of_reading.this);
        View mView = getLayoutInflater().inflate(R.layout.filter_datas, null);

        final TextView select_time_ = (TextView) mView.findViewById(R.id.select_time_);
        final TextView select_date_ = (TextView) mView.findViewById(R.id.select_date_); // select_pond_
        final Spinner select_pond_ = (Spinner) mView.findViewById(R.id.select_pond_);
        final Spinner select_title_ = (Spinner) mView.findViewById(R.id.select_title_); // select_sensor1_
        final Spinner select_sensor1_ = (Spinner) mView.findViewById(R.id.select_sensor1_);
        final Spinner select_sensor2_ = (Spinner) mView.findViewById(R.id.select_sensor2_);
        final CheckBox ch_time = (CheckBox) mView.findViewById(R.id.ch_time);
        final CheckBox ch_date = (CheckBox) mView.findViewById(R.id.ch_date);
        final CheckBox ch_pond = (CheckBox) mView.findViewById(R.id.ch_pond);
        final CheckBox ch_title = (CheckBox) mView.findViewById(R.id.ch_title);
        final CheckBox ch_batch = (CheckBox) mView.findViewById(R.id.ch_batch);
        final CheckBox ch_test = (CheckBox) mView.findViewById(R.id.ch_test);

        final TextView sample_tv = (TextView) mView.findViewById(R.id.sample_tv);

        final Button btn_close_dialog = (Button) mView.findViewById(R.id.btn_close_dialog);
        final Button make_filter_to = (Button) mView.findViewById(R.id.make_filter_to);

        select_sensor1_.setAdapter(arrayAdapter);
        select_sensor2_.setAdapter(arrayAdapter);

        Downloader d=new Downloader(_list_of_reading.this,"http://"+thisip+"/z/gp_list.php",select_pond_);
        Downloader_2 sd=new Downloader_2(_list_of_reading.this,"http://"+thisip+"/z/title_list.php",select_title_);

        d.execute();
        sd.execute();

        ch_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if(checked)
                {

                    if(select_time_.getText().toString().equals("(Select Time)"))
                    {
                        Toast.makeText(getApplicationContext(), "Please Select Time!", Toast.LENGTH_SHORT).show();
                        ch_time.setChecked(false);
                    }
                    else{
                        time_String_holder = f_holder_time;
                        Toast.makeText(getApplicationContext(), time_String_holder, Toast.LENGTH_SHORT).show();
                        sample_tv.setText(sample_tv.getText().toString() + time_String_holder);
                    }

                }
                else{
                    time_String_holder = "";
                    Toast.makeText(getApplicationContext(), "Nothing", Toast.LENGTH_SHORT).show();

                }
            }
        });

        ch_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if(checked)
                {
                    if(select_date_.getText().toString().equals("(Select Date)"))
                    {
                        Toast.makeText(getApplicationContext(), "Please Select Date!", Toast.LENGTH_SHORT).show();
                        ch_date.setChecked(false);

                    }
                    else
                    {
                        String[] datas_rcv_list_cons_pref = f_holder_date.split("-");

                        String cut_on_month = datas_rcv_list_cons_pref[1];
                        String cut_on_day = datas_rcv_list_cons_pref[2];
                        date_String_holder = cut_on_month+"-"+cut_on_day;
                        Toast.makeText(getApplicationContext(), date_String_holder , Toast.LENGTH_SHORT).show();
                        sample_tv.setText(sample_tv.getText().toString() + date_String_holder);
                    }

                }
                else{
                    date_String_holder = "";
                    Toast.makeText(getApplicationContext(), "Nothing", Toast.LENGTH_SHORT).show();

                }
            }
        });

        ch_pond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if(checked)
                {
                    pond_String_holder = f_holder_pond;
                    Toast.makeText(getApplicationContext(), pond_String_holder, Toast.LENGTH_SHORT).show();
                    sample_tv.setText(sample_tv.getText().toString() + pond_String_holder);
                }
                else{
                    pond_String_holder = "";
                    Toast.makeText(getApplicationContext(), "Nothing", Toast.LENGTH_SHORT).show();

                }
            }
        });

        ch_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if(checked)
                {
                    title_String_holder = f_holder_title;
                    Toast.makeText(getApplicationContext(), title_String_holder, Toast.LENGTH_SHORT).show();
                    sample_tv.setText(sample_tv.getText().toString() + title_String_holder);
                }
                else{
                    title_String_holder = "";
                    Toast.makeText(getApplicationContext(), "Nothing", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ch_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if(checked)
                {
                    ch_test.setChecked(false);

                }
                else{

                }
            }
        });

        ch_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if(checked)
                {
                    ch_batch.setChecked(false);
                }
                else{
                }
            }
        });

        select_pond_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                f_holder_pond = select_pond_.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), f_holder_pond, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        select_title_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                f_holder_title = select_title_.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), f_holder_title, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        select_time_.setOnClickListener(new View.OnClickListener() {
            final Calendar calendar = Calendar.getInstance();
            final String format = "";
            final int hour = calendar.get(Calendar.HOUR_OF_DAY);
            final int mins = calendar.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(_list_of_reading.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //select_time_.setText(hourOfDay +":"+minute);
                        select_time_.setText(showTime(hourOfDay , minute, format));

                        String[] time_cut_on = select_time_.getText().toString().split(":"); //.split(",");
                        String inHour = time_cut_on[0];
                        String inMins = time_cut_on[1];

                        select_time_.setText(inHour+":"+inMins);
                        f_holder_time = "0"+inHour + ":";
                        Toast.makeText(getApplicationContext(), f_holder_time, Toast.LENGTH_SHORT).show();
                    }
                }, hour, mins, false);

                timePickerDialog.show();

            }
        });

        select_date_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                Dpicker1 = new DatePickerDialog(_list_of_reading.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String a = year + "-" +"0"+(monthOfYear + 1) + "-" + dayOfMonth;
                                select_date_.setText(a);

                                String[] time_cut_on = select_date_.getText().toString().split("-"); //.split(",");
                                String inYear = time_cut_on[0];
                                String inMonth = time_cut_on[1];
                                String inDays = time_cut_on[2];


                                f_holder_date = a;
                                Toast.makeText(getApplicationContext(), f_holder_date, Toast.LENGTH_SHORT).show();

                            }
                        }, year, month, day);
                Dpicker1.show();
            }
        });

        mBuilder.setView(mView);
       final AlertDialog dialog = mBuilder.create();

        make_filter_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ----TO REMEMBER final variables is | // title_String_holder | pond_String_holder | date_String_holder | time_String_holder  ###### filter_by_to("","","","","");

                if(!(ch_time.isChecked() || ch_date.isChecked() || ch_pond.isChecked() || ch_title.isChecked()))
                {
                    Toast.makeText(getApplicationContext(), "Check  Required!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    filter_by_to(time_String_holder ,date_String_holder,pond_String_holder,title_String_holder,"");
                    Toast.makeText(getApplicationContext(), "Filtering please wait...", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }


            }
        });


        btn_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();

    } //

    private void filter_by_to(String times , String dates, String ponds, String titles, String categ)
    {
        list_of_sensors.clear();
        listView.clearTextFilter();
//        String path_rcv_cons_pref = "http://"+"192.168.1.4"+"/z/view_searching.php/?gpn="+ponds+"&&title="+titles+"&&thisTime="+times+"&&thisDate="+dates;
        String path_rcv_cons_pref = "http://"+thisip+"/z/view_searching.php/?gpn="+ponds+"&&title="+titles+"&&thisTime="+times+"&&thisDate="+dates;

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
                                sensor s = new sensor(jsonObject.getString("pond_ID"),jsonObject.getString("title_gp"),jsonObject.getString("temp"),jsonObject.getString("ph"), jsonObject.getString("sal"),
                                        jsonObject.getString("turb"), jsonObject.getString("dos"));
                                list_of_sensors.add(s);
                            }
                            SensorAdapter adapter = new SensorAdapter(list_of_sensors, getApplicationContext());
                            listView.setAdapter(adapter);
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

    private String showTime(int hour, int mins , String format)
    {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        timeHolder = String.valueOf(hour)+":"+String.valueOf(mins)+ " " + format;

        //time.setText(new StringBuilder().append(hour).append(" : ").append(mins)
          //      .append(" ").append(format));
        return timeHolder;
    }

    public void backDash(View v)
    {
        Intent invokeLoadScren = new Intent(this , _dash.class);
        startActivity(invokeLoadScren);
    }

}
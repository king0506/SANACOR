package com.example.sanacorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sanacorapp.spinner_for_gplist.Downloader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class _pond_control extends AppCompatActivity {

    //init for TCP
    static Socket ss;
    InputStreamReader in;
    android.os.Handler handler = new Handler();
    Thread inThread;
    DataOutputStream dos;
    String ip_shared = "192.168.1.11", port_shared ,datas_rcv;
    String urlAddress="http://"+ip_shared+"/z/gp_list.php";

    int x = 10;

    Spinner sp_ , sp_2;

    Button _b_drainage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__pond_control);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       // ip_shared = preferences.getString("shared_ip", "defaultValue");
       // port_shared = preferences.getString("shared_port", "defaultValue");

        sp_ = (Spinner)findViewById(R.id.sp_drng); //
        sp_2 = (Spinner)findViewById(R.id.sp_mtr);

        Downloader drngs_ = new Downloader(this,urlAddress,sp_);
        Downloader mtrs_ = new Downloader(this,urlAddress,sp_2);

        drngs_.execute();
        mtrs_.execute();

        _b_drainage = (Button)findViewById(R.id._b_drainage);

        _b_drainage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gps = sp_.getSelectedItem().toString();

                if(_b_drainage.getText().equals("DRAINAGE : OFF"))
                {
                    _b_drainage.setTextColor(Color.GREEN);
                    CommandData("dpnd"+", "+"drng"+", "+gps+", "+"on" + ", null, null");
                    _b_drainage.setText("DRAINAGE : ON");
                }

                else
                {

                    _b_drainage.setTextColor(Color.RED);
                    CommandData("dpnd"+", "+"drng"+", "+gps+", "+"off" + ", null, null");
                    _b_drainage.setText("DRAINAGE : OFF");
                }

            }
        });

    }

  /*  class SereverThread implements Runnable {
        String a;
        @Override
        public void run() {
            try
            {
                ss = new Socket( ip_shared , Integer.parseInt(port_shared));   // -------------- REMEMBER COMMAND HERE


                while (true)
                {
                    in = new InputStreamReader(ss.getInputStream());
                    BufferedReader bf;  bf = new BufferedReader(in);
                    datas_rcv = bf.readLine();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            get_switch_status(datas_rcv);

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

    }*/

  public void setControlMotor(View v)
  {

      Intent mtrs = new Intent(getApplicationContext() , _motor_control.class);
      startActivity(mtrs);
  }

    public void CommandData(String s)
    {
        BGTask b = new BGTask();
        b.execute( ip_shared , s);  // -------------- REMEMBER COMMAND HERE
    }

    class BGTask extends AsyncTask<String , Void , String>
    {
        @Override
        protected String doInBackground(String... params) {
            String ip_s = params[0];
            String command_s = params[1];
            try
            {
                ss = new Socket(ip_s, 7479); // ip_shared ,
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
}

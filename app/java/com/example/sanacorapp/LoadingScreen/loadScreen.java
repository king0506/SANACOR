package com.example.sanacorapp.LoadingScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.example.sanacorapp.*;

public class loadScreen extends AppCompatActivity {

    String datas_rcv , ip_load;
    int ports_;
    Intent intent;
    TextView msg;
    ImageView imageView , imgLoad;


    static Socket ss;
    InputStreamReader in;
    Handler handler = new Handler();
    Thread inThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        imageView = (ImageView) findViewById(R.id.imgErr);
        imgLoad = (ImageView) findViewById(R.id.imgLoad);
        msg = (TextView)findViewById(R.id._t_msg);
        imgLoad.setVisibility(View.GONE);

        shared_pref();


        inThread = new Thread(new SereverThreads());
        inThread.start();

    }

    private void shared_pref()
    {
        try{
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String ip_shared = preferences.getString("shared_ip", "defaultValue");
            String port_shared = preferences.getString("shared_port", "defaultValue");

            ip_load = ip_shared;
            ports_ = Integer.parseInt(port_shared);

        }
        catch (ArrayIndexOutOfBoundsException arr)
        {}
    }

    class SereverThreads implements Runnable {
        @Override
        public void run() {
            try
            {
                ss = new Socket( ip_load , ports_);   // -------------- REMEMBER COMMAND HERE
                imageView.setVisibility(View.GONE);
                imgLoad.setVisibility(View.VISIBLE);
                msg.setText("Waiting to the device to connect...");

                while (true)
                {
                    in = new InputStreamReader(ss.getInputStream());
                    BufferedReader bf;  bf = new BufferedReader(in);
                    datas_rcv = bf.readLine();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                           if(datas_rcv.contains("xonlinex"))
                           {
                               intent = new Intent(getApplicationContext(), _data_aqc.class);
                               startActivity(intent);
                               finish();
                           }
                           else
                               {
                                 //  Toast.makeText(getApplicationContext() , "Waiting...", Toast.LENGTH_SHORT).show();
                               }
                        }
                    });
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void retryCon(View v)
    {
        recreate();
    }

}

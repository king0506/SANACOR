package com.example.sanacorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanacorapp.spinner_for_gplist.Downloader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class _motor_control extends AppCompatActivity {

    TextView rpm_text;
    Spinner sp_;
    int rpm_ = 0;
    ImageView g1,g2,g3,g4,g5,g6;
    ImageView o1,o2,o3,o4,o5,o6;
    ImageView r1,r2,r3,r4,r5,r6;
    ImageView btn_up , btn_down;


    //init for TCP
    static Socket ss;
    InputStreamReader in;
    android.os.Handler handler = new Handler();
    Thread inThread;
    DataOutputStream dos;
    int flag = 0;
    String ip_shared = "192.168.1.11", port_shared ,datas_rcv;

    String urlAddress="http://"+ip_shared+"/z/gp_list.php";

    Switch mtr_Switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__motor_control);

        sp_ = (Spinner)findViewById(R.id.spin_gps_listing);

        g1 = (ImageView) findViewById(R.id.l1);
        g2 = (ImageView) findViewById(R.id.l2);
        g3 = (ImageView) findViewById(R.id.l3);
        g4 = (ImageView) findViewById(R.id.l4);
        g5 = (ImageView) findViewById(R.id.l5);
        g6 = (ImageView) findViewById(R.id.l6);

        o1 = (ImageView) findViewById(R.id.l7);
        o2 = (ImageView) findViewById(R.id.l8);
        o3 = (ImageView) findViewById(R.id.l9);
        o4 = (ImageView) findViewById(R.id.l10);
        o5 = (ImageView) findViewById(R.id.l11);
        o6 = (ImageView) findViewById(R.id.l12);

        r1 = (ImageView) findViewById(R.id.l13);
        r2 = (ImageView) findViewById(R.id.l14);
        r3 = (ImageView) findViewById(R.id.l15);
        r4 = (ImageView) findViewById(R.id.l16);
        r5 = (ImageView) findViewById(R.id.l17);
        r6 = (ImageView) findViewById(R.id.l18);

        btn_up = (ImageView) findViewById(R.id.btn_up_speed);
        btn_down = (ImageView) findViewById(R.id.btn_down_speed);
        mtr_Switch = (Switch) findViewById(R.id.mtr_Switch);

        rpm_text = (TextView)findViewById(R.id.rmp_indect);


        Downloader d =new Downloader(this,urlAddress,sp_);
        d.execute();

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String gps = sp_.getSelectedItem().toString();

               // upSpeed(rpm_);
                rpm_ = upSpeed(rpm_);
                String holdSpeed = String.valueOf(rpm_)+"0";

                rpm_text.setText(String.valueOf(rpm_) + "000" );
                Commandmotor("dpnd"+", "+"mtr"+", "+gps+", "+"prop"+", "+holdSpeed + ", null");
            }
        });

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gps = sp_.getSelectedItem().toString();
               // downSpeed(rpm_);
                rpm_ = downSpeed(rpm_);

                String holdSpeed = String.valueOf(rpm_)+"0";

                rpm_text.setText(String.valueOf(rpm_) + "000" );
                Commandmotor("dpnd"+", "+"mtr"+", "+gps+", "+"prop"+", "+holdSpeed +", null");
            }
        });

        mtr_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String gps = sp_.getSelectedItem().toString();
                if (isChecked) {
                    Commandmotor("dpnd"+", "+"mtr"+", "+gps+", "+"switch"+", "+"on" +", null");
                    Toast.makeText(getApplicationContext(),"ON" , Toast.LENGTH_SHORT).show();
                } else {
                    Commandmotor("dpnd"+", "+"mtr"+", "+gps+", "+"switch"+", "+"off" +", null");
                    Toast.makeText(getApplicationContext(),"OFF" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private int upSpeed(int rpm)
    {
        if(rpm<=17)
        {
            rpm ++;

            if(rpm == 1)
            {
                g1.setVisibility(View.VISIBLE);
            //    Toast.makeText(this,gps , Toast.LENGTH_SHORT).show();
            }
            if(rpm == 2)
            {
                g1.setVisibility(View.VISIBLE);
                g2.setVisibility(View.VISIBLE);
            }
            if(rpm == 3)
            {
                g2.setVisibility(View.VISIBLE);
                g3.setVisibility(View.VISIBLE);
            }
            if(rpm == 4)
            {
                g3.setVisibility(View.VISIBLE);
                g4.setVisibility(View.VISIBLE);
            }
            if(rpm == 5)
            {
                g4.setVisibility(View.VISIBLE);
                g5.setVisibility(View.VISIBLE);
            }
            if(rpm == 6)
            {
                g5.setVisibility(View.VISIBLE);
                g6.setVisibility(View.VISIBLE);
            }
            if(rpm == 7)
            {
                g6.setVisibility(View.VISIBLE);
                o1.setVisibility(View.VISIBLE);
            }
            if(rpm == 8)
            {
                o1.setVisibility(View.VISIBLE);
                o2.setVisibility(View.VISIBLE);
            }
            if(rpm == 9)
            {
                o2.setVisibility(View.VISIBLE);
                o3.setVisibility(View.VISIBLE);
            }
            if(rpm == 10)
            {
                o3.setVisibility(View.VISIBLE);
                o4.setVisibility(View.VISIBLE);
            }
            if(rpm == 11)
            {
                o4.setVisibility(View.VISIBLE);
                o5.setVisibility(View.VISIBLE);
            }
            if(rpm == 12)
            {
                o5.setVisibility(View.VISIBLE);
                o6.setVisibility(View.VISIBLE);
            }
            if(rpm == 13)
            {
                o6.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
            }
            if(rpm == 14)
            {
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
            }
            if(rpm == 15)
            {
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
            }
            if(rpm == 16)
            {
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
            }
            if(rpm == 17)
            {
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
            }
            if(rpm == 18)
            {
                r5.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
            }
        }

        else
        {
            Toast.makeText(this,"LIMIT REACH" , Toast.LENGTH_SHORT).show();
        }
        return rpm;
    }

    private int downSpeed(int rpm)
    {

        if(rpm>=1)
        {            rpm --;

            if(rpm == 1)
            {
                g1.setVisibility(View.INVISIBLE);
                g2.setVisibility(View.INVISIBLE);
            }
            if(rpm == 2)
            {
                g2.setVisibility(View.INVISIBLE);
                g3.setVisibility(View.INVISIBLE);
            }
            if(rpm == 3)
            {
                g3.setVisibility(View.INVISIBLE);
                g4.setVisibility(View.INVISIBLE);
            }
            if(rpm == 4)
            {
                g4.setVisibility(View.INVISIBLE);
                g5.setVisibility(View.INVISIBLE);
            }
            if(rpm == 5)
            {
                g5.setVisibility(View.INVISIBLE);
                g6.setVisibility(View.INVISIBLE);
            }
            if(rpm == 6)
            {
                g6.setVisibility(View.INVISIBLE);
                o1.setVisibility(View.INVISIBLE);
            }
            if(rpm == 7)
            {
                o1.setVisibility(View.INVISIBLE);
                o2.setVisibility(View.INVISIBLE);
            }
            if(rpm == 8)
            {
                o2.setVisibility(View.INVISIBLE);
                o3.setVisibility(View.INVISIBLE);
            }
            if(rpm == 9)
            {
                o3.setVisibility(View.INVISIBLE);
                o4.setVisibility(View.INVISIBLE);
            }
            if(rpm == 10)
            {
                o4.setVisibility(View.INVISIBLE);
                o5.setVisibility(View.INVISIBLE);
            }
            if(rpm == 11)
            {
                o5.setVisibility(View.INVISIBLE);
                o6.setVisibility(View.INVISIBLE);
            }
            if(rpm == 12)
            {
                o6.setVisibility(View.INVISIBLE);
                r1.setVisibility(View.INVISIBLE);
            }
            if(rpm == 13)
            {
                r1.setVisibility(View.INVISIBLE);
                r2.setVisibility(View.INVISIBLE);
            }
            if(rpm == 14)
            {
                r2.setVisibility(View.INVISIBLE);
                r3.setVisibility(View.INVISIBLE);
            }
            if(rpm == 15)
            {
                r3.setVisibility(View.INVISIBLE);
                r4.setVisibility(View.INVISIBLE);
            }
            if(rpm == 16)
            {
                r4.setVisibility(View.INVISIBLE);
                r5.setVisibility(View.INVISIBLE);
            }
            if(rpm == 17)
            {
                r5.setVisibility(View.INVISIBLE);
                r6.setVisibility(View.INVISIBLE);
            }
            if(rpm == 18)
            {
                r6.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            Toast.makeText(this,"LIMIT REACH" , Toast.LENGTH_SHORT).show();
        }

        return rpm;
    }

    public void Commandmotor(String s)
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

package com.example.sanacorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class _config extends AppCompatActivity {

    EditText ips , ports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__config);

        ips = (EditText)findViewById(R.id.iphost_t);
        ports = (EditText)findViewById(R.id.port_t);

    }

    public void saveCon(View v)
    {
        String ip_to_share = ips.getText().toString();
        String port_share =  ports.getText().toString();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("shared_ip", ip_to_share);
        editor.putString("shared_port", port_share);
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), _dash.class);
        startActivity(intent);
        finish();
    }


}

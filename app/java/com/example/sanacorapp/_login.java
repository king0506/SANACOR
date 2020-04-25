package com.example.sanacorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

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

public class _login extends AppCompatActivity {

    EditText et_username , et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__login);

        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
    }

    public void login_(View v)
    {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        String type = "login";

        loginWorker loginWorker = new loginWorker(this);
        loginWorker.execute(type, username, password);


    }

    class loginWorker extends AsyncTask<String, Void ,String>
    {
        Context context;
        AlertDialog alertDialog;


        loginWorker(Context ctx)
        {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];

            if(type.equals("login"))
            {
                String login_url = "http://192.168.1.4/z/login.php";
                String username_s = params[1];
                String password_s = params[2];
                try {

                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username_s, "UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password_s, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "" , line = "";

                    while ((line = bufferedReader.readLine()) != null)
                    {
                        result+=line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            String username = params[0];
            String password = params[0];

            return null;
        }

        @Override
        protected void onPreExecute() {

            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Login");
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("yes"))
            {
                Intent invokeConfig = new Intent(getApplicationContext() , _dash.class);
                startActivity(invokeConfig);
                alertDialog.setMessage("Login Successfuly!");
                alertDialog.show();
                finish();
            }
            else
            {
                alertDialog.setMessage("Invalid Account");
                alertDialog.show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}

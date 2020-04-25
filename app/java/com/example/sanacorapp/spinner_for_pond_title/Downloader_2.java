package com.example.sanacorapp.spinner_for_pond_title;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Downloader_2 extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    Spinner sp2;
    ProgressDialog pd;

    public Downloader_2(Context c, String urlAddress, Spinner sp2) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.sp2 = sp2;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Downloading..");
        pd.setMessage("Retrieving data...Please wait");
        //pd.show();
        pd.hide();
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.retrieveData();
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        pd.dismiss();

        sp2.setAdapter(null);

        if(s != null)
        {
            Parser_2 p=new Parser_2(c,s,sp2);
            p.execute();
        }
        else {
            Toast.makeText(c,"No data retrieved",Toast.LENGTH_SHORT).show();
        }
    }

    private String retrieveData()
    {

        HttpURLConnection con= Connector_2.connect(urlAddress);
        if(con==null)
        {
            return null;
        }

        try
        {
            InputStream is=con.getInputStream();
            BufferedReader br= new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer receivedData=new StringBuffer();
            while ((line=br.readLine()) != null)
            {
                receivedData.append(line+"n");
            }
            br.close();
            is.close();
            return receivedData.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
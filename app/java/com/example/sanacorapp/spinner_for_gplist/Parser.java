package com.example.sanacorapp.spinner_for_gplist;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser extends AsyncTask<Void,Void,Integer> {

    Context c;
    String data;
    ArrayList names=new ArrayList();
    Spinner sp;

    public Parser(Context c, String data,Spinner sp) {
        this.c = c;
        this.data = data;
        this.sp=sp;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if(integer==1)
        {
            ArrayAdapter adapter=new ArrayAdapter(c,android.R.layout.simple_spinner_item,names); //simple_list_item_1
            sp.setAdapter(adapter);

        }else {
            Toast.makeText(c,"Unable To Parse",Toast.LENGTH_SHORT).show();
        }
    }

    private int parse()
    {
        try
        {
            JSONArray ja=new JSONArray(data);
            JSONObject jo=null;

            for (int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                String name=jo.getString("pond_ID");
                names.add(name);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
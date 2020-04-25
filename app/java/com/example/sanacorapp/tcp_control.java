package com.example.sanacorapp;

import android.os.Handler;
import android.view.View;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class tcp_control implements Runnable {
    String a , datas_rcv;
    //init for TCP
    static Socket ss;
    InputStreamReader in;
    android.os.Handler handler = new Handler();
    Thread inThread;
    DataOutputStream dos;

    @Override
    public void run() {
        try
        {
            ss = new Socket( "192.168.254.92" , 7479);   // -------------- REMEMBER COMMAND HERE

            while (true)
            {
                in = new InputStreamReader(ss.getInputStream());
                BufferedReader bf;  bf = new BufferedReader(in);
                datas_rcv = bf.readLine();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        // load_in(datas_rcv);
                        bridgeDatas(datas_rcv);
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

    private void bridgeDatas(String s)
    {
        returnDatas(s);
    }

    public String returnDatas(String s)
    {


        return a;
    }
}
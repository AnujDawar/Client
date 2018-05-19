package com.example.anujdawar.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        Intent intent;

        if (sharedPref.getString("ipAddress", "").equals("") || sharedPref.getString("portNumber", "").equals("") ||
                sharedPref.getString("Dev1Status", "").equals("") &&
                        sharedPref.getString("Dev2Status", "").equals("") &&
                        sharedPref.getString("Dev3Status", "").equals("") &&
                        sharedPref.getString("Dev4Status", "").equals("") &&
                        sharedPref.getString("Dev5Status", "").equals(""))


            intent = new Intent("com.example.anujdawar.client.MainActivity");

        else
            intent = new Intent("com.example.anujdawar.client.DevicesActivity");


        startActivity(intent);

    }
}
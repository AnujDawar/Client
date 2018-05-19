package com.example.anujdawar.client;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Locale;

import static com.example.anujdawar.client.SavedCommandsActivity.EditFlag;

public class MainActivity extends AppCompatActivity
{
    Handler h1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            resultString = msg.obj.toString();

            if(resultString.equals("S"));
            {
                res = 1;
                CarLockTone.start();

                try {
                    dataIn.close();
                    dataOut.close();
                    s.close();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                Intent deviceIntent = new Intent("com.example.anujdawar.client.DevicesActivity");
                startActivity(deviceIntent);
            }
        }
    };


    MediaPlayer CarLockTone, Bleats, Trash;
    private static int DeleteFlag = 0, threadStartFlag = 0;
    private static String resultString;
    private int checkInputFlag = 0;
    public EditText ipAddress, port, Device1Status, Device2Status, Device3Status, Device4Status, Device5Status;
    public TextView ResultBox;
    public static Socket s;
    public static DataInputStream dataIn;
    public static DataOutputStream dataOut;
    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor myEditor;
    private static int res;
    private CheckBox check1, check2, check3, check4, check5;
    protected static TextToSpeech textToSpeech;
    protected static int result;



    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            dataIn.close();
            dataOut.close();
            s.close();
        }catch (Exception e)
        {   }
        if(textToSpeech != null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Thread textThread = new Thread(new Runnable() {
            @Override
            public void run() {
                textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {

                        if(i == TextToSpeech.SUCCESS)
                            result = textToSpeech.setLanguage(Locale.getDefault());

                            //result = textToSpeech.setLanguage(Locale.UK);

                        else
                            Toast.makeText(getApplicationContext(), "Your phone doesn't support this feature", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        textThread.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_all_inclusive_white_24dp);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("  All Inclusive");

        CarLockTone = MediaPlayer.create(this,R.raw.carock);
        Bleats = MediaPlayer.create(this, R.raw.bleatsounds);
        Trash = MediaPlayer.create(this, R.raw.whip);

        ipAddress = (EditText) findViewById(R.id.ipAddressTextBox);
        port = (EditText) findViewById(R.id.portNumberTextBox);
        ResultBox = (TextView) findViewById(R.id.resultTextBoxMain);
        Device1Status = (EditText) findViewById(R.id.Device1TextBox);
        Device2Status = (EditText) findViewById(R.id.Device2TextBox);
        Device3Status = (EditText) findViewById(R.id.Device3TextBox);
        Device4Status = (EditText) findViewById(R.id.Device4TextBox);
        Device5Status = (EditText) findViewById(R.id.Device5TextBox);

        check1 = (CheckBox) findViewById(R.id.checkBox1);
        check2 = (CheckBox) findViewById(R.id.checkBox2);
        check3 = (CheckBox) findViewById(R.id.checkBox3);
        check4 = (CheckBox) findViewById(R.id.checkBox4);
        check5 = (CheckBox) findViewById(R.id.checkBox5);

        sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        ipAddress.setText(sharedPref.getString("ipAddress",""));
        port.setText(sharedPref.getString("portNumber",""));
        Device1Status.setText(sharedPref.getString("Dev1Status",""));
        Device2Status.setText(sharedPref.getString("Dev2Status",""));
        Device3Status.setText(sharedPref.getString("Dev3Status",""));
        Device4Status.setText(sharedPref.getString("Dev4Status",""));
        Device5Status.setText(sharedPref.getString("Dev5Status",""));
        check1.setChecked(Boolean.parseBoolean(sharedPref.getString("CheckBox1","")));
        check2.setChecked(Boolean.parseBoolean(sharedPref.getString("CheckBox2","")));
        check3.setChecked(Boolean.parseBoolean(sharedPref.getString("CheckBox3","")));
        check4.setChecked(Boolean.parseBoolean(sharedPref.getString("CheckBox4","")));
        check5.setChecked(Boolean.parseBoolean(sharedPref.getString("CheckBox5","")));

        myEditor = sharedPref.edit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.info_id:

                EditFlag = false;

                if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA)
                    Toast.makeText(MainActivity.this, "your device doesn't support this feature" , Toast.LENGTH_SHORT).show();

                else
                    textToSpeech.speak("Well, Thanks for learning",TextToSpeech.QUEUE_FLUSH,null);

                AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                a_builder.setMessage("This App is Created By : \n\t\t\t\tAnuj Dawar\nFor Suggestions, " +
                        "Contact : \n\t\t\t\tanujdawar95@gmail.com\n\t\t\t\tVersion 3.2.1").setCancelable(false)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = a_builder.create();
                alert.setTitle("About Developer");
                alert.show();

                break;

            case R.id.Clear_id:

                EditFlag = false;

                if (threadStartFlag == 0) {

                    ipAddress.setText("");
                    port.setText("");
                    Device1Status.setText("");
                    Device2Status.setText("");
                    Device3Status.setText("");
                    Device4Status.setText("");
                    Device5Status.setText("");
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                    check5.setChecked(false);

                    ipAddress.setEnabled(true);
                    port.setEnabled(true);
                    Device1Status.setEnabled(true);
                    Device2Status.setEnabled(true);
                    Device3Status.setEnabled(true);
                    Device4Status.setEnabled(true);
                    Device5Status.setEnabled(true);
                    check1.setEnabled(true);
                    check2.setEnabled(true);
                    check3.setEnabled(true);
                    check4.setEnabled(true);
                    check5.setEnabled(true);


                    Trash.start();

                    myEditor.putString("ipAddress", ipAddress.getText().toString());
                    myEditor.putString("portNumber", port.getText().toString());
                    myEditor.putString("Dev1Status", Device1Status.getText().toString());
                    myEditor.putString("Dev2Status", Device2Status.getText().toString());
                    myEditor.putString("Dev3Status", Device3Status.getText().toString());
                    myEditor.putString("Dev4Status", Device4Status.getText().toString());
                    myEditor.putString("Dev5Status", Device5Status.getText().toString());

                    myEditor.putString("CheckBox1",String.valueOf(check1.isChecked()));
                    myEditor.putString("CheckBox2",String.valueOf(check2.isChecked()));
                    myEditor.putString("CheckBox3",String.valueOf(check3.isChecked()));
                    myEditor.putString("CheckBox4",String.valueOf(check4.isChecked()));
                    myEditor.putString("CheckBox5",String.valueOf(check5.isChecked()));

                    myEditor.apply();

                } else {

                    threadStartFlag = 0;

                    ipAddress.setEnabled(true);
                    port.setEnabled(true);
                    Device1Status.setEnabled(true);
                    Device2Status.setEnabled(true);
                    Device3Status.setEnabled(true);
                    Device4Status.setEnabled(true);
                    Device5Status.setEnabled(true);
                    check1.setEnabled(true);
                    check2.setEnabled(true);
                    check3.setEnabled(true);
                    check4.setEnabled(true);
                    check5.setEnabled(true);

                    Trash.start();

                    myEditor.putString("ipAddress", ipAddress.getText().toString());
                    myEditor.putString("portNumber", port.getText().toString());
                    myEditor.putString("Dev1Status", Device1Status.getText().toString());
                    myEditor.putString("Dev2Status", Device2Status.getText().toString());
                    myEditor.putString("Dev3Status", Device3Status.getText().toString());
                    myEditor.putString("Dev4Status", Device4Status.getText().toString());
                    myEditor.putString("Dev5Status", Device5Status.getText().toString());

                    myEditor.putString("CheckBox1",String.valueOf(check1.isChecked()));
                    myEditor.putString("CheckBox2",String.valueOf(check2.isChecked()));
                    myEditor.putString("CheckBox3",String.valueOf(check3.isChecked()));
                    myEditor.putString("CheckBox4",String.valueOf(check4.isChecked()));
                    myEditor.putString("CheckBox5",String.valueOf(check5.isChecked()));

                    myEditor.apply();

                }

                break;

            case R.id.next_id:

                EditFlag = false;

                myEditor.putString("ipAddress", ipAddress.getText().toString());
                myEditor.putString("portNumber", port.getText().toString());
                myEditor.putString("Dev1Status", Device1Status.getText().toString());
                myEditor.putString("Dev2Status", Device2Status.getText().toString());
                myEditor.putString("Dev3Status", Device3Status.getText().toString());
                myEditor.putString("Dev4Status", Device4Status.getText().toString());
                myEditor.putString("Dev5Status", Device5Status.getText().toString());

                myEditor.putString("CheckBox1",String.valueOf(check1.isChecked()));
                myEditor.putString("CheckBox2",String.valueOf(check2.isChecked()));
                myEditor.putString("CheckBox3",String.valueOf(check3.isChecked()));
                myEditor.putString("CheckBox4",String.valueOf(check4.isChecked()));
                myEditor.putString("CheckBox5",String.valueOf(check5.isChecked()));

                myEditor.apply();

                ConnectButtonCall();

                if (checkInputFlag != 0) {
                    ipAddress.setEnabled(true);
                    port.setEnabled(true);
                    Device1Status.setEnabled(true);
                    Device2Status.setEnabled(true);
                    Device3Status.setEnabled(true);
                    Device4Status.setEnabled(true);
                    Device5Status.setEnabled(true);

                    check1.setEnabled(true);
                    check2.setEnabled(true);
                    check3.setEnabled(true);
                    check4.setEnabled(true);
                    check5.setEnabled(true);
                }

                break;

            case R.id.addCommands_id:

                EditFlag = false;

                myEditor.putString("ipAddress", ipAddress.getText().toString());
                myEditor.putString("portNumber", port.getText().toString());
                myEditor.putString("Dev1Status", Device1Status.getText().toString());
                myEditor.putString("Dev2Status", Device2Status.getText().toString());
                myEditor.putString("Dev3Status", Device3Status.getText().toString());
                myEditor.putString("Dev4Status", Device4Status.getText().toString());
                myEditor.putString("Dev5Status", Device5Status.getText().toString());

                myEditor.putString("CheckBox1",String.valueOf(check1.isChecked()));
                myEditor.putString("CheckBox2",String.valueOf(check2.isChecked()));
                myEditor.putString("CheckBox3",String.valueOf(check3.isChecked()));
                myEditor.putString("CheckBox4",String.valueOf(check4.isChecked()));
                myEditor.putString("CheckBox5",String.valueOf(check5.isChecked()));

                myEditor.apply();

                Intent addCommandIntent = new Intent("com.example.anujdawar.client.AddCommandIntentClass");
                startActivity(addCommandIntent);

                break;
        }

        return super.onOptionsItemSelected(item);

    }

    public void ConnectButtonCall()
        {
            ipAddress.setEnabled(false);
            port.setEnabled(false);
            Device1Status.setEnabled(false);
            Device2Status.setEnabled(false);
            Device3Status.setEnabled(false);
            Device4Status.setEnabled(false);
            Device5Status.setEnabled(false);

            check1.setEnabled(false);
            check2.setEnabled(false);
            check3.setEnabled(false);
            check4.setEnabled(false);
            check5.setEnabled(false);

            if(ipAddress.getText().toString().equals(""))
            {
                checkInputFlag = 1;
                Toast.makeText(MainActivity.this, "IP Address Field Cannot be Empty", Toast.LENGTH_SHORT).show();
                ipAddress.setEnabled(true);
                port.setEnabled(true);
                Device1Status.setEnabled(true);
                Device2Status.setEnabled(true);
                Device3Status.setEnabled(true);
                Device4Status.setEnabled(true);
                Device5Status.setEnabled(true);

                check1.setEnabled(true);
                check2.setEnabled(true);
                check3.setEnabled(true);
                check4.setEnabled(true);
                check5.setEnabled(true);

                return;
            }

            if(port.getText().toString().equals(""))
            {
                checkInputFlag = 2;
                Toast.makeText(MainActivity.this, "Port Number Field Cannot be Empty", Toast.LENGTH_SHORT).show();
                ipAddress.setEnabled(true);
                port.setEnabled(true);
                Device1Status.setEnabled(true);
                Device2Status.setEnabled(true);
                Device3Status.setEnabled(true);
                Device4Status.setEnabled(true);
                Device5Status.setEnabled(true);

                check1.setEnabled(true);
                check2.setEnabled(true);
                check3.setEnabled(true);
                check4.setEnabled(true);
                check5.setEnabled(true);

                return;
            }

            if(Device1Status.getText().toString().equals("") &&
                    Device2Status.getText().toString().equals("") &&
                    Device3Status.getText().toString().equals("") &&
                    Device4Status.getText().toString().equals("") &&
                    Device5Status.getText().toString().equals(""))
            {
                Toast.makeText(MainActivity.this, "Name At Least 1 Device", Toast.LENGTH_SHORT).show();
                ipAddress.setEnabled(true);
                port.setEnabled(true);
                Device1Status.setEnabled(true);
                Device2Status.setEnabled(true);
                Device3Status.setEnabled(true);
                Device4Status.setEnabled(true);
                Device5Status.setEnabled(true);

                check1.setEnabled(true);
                check2.setEnabled(true);
                check3.setEnabled(true);
                check4.setEnabled(true);
                check5.setEnabled(true);

                checkInputFlag = 3;
                return;
            }

            Thread connectThread = new Thread(new Runnable()
            {
            @Override
            public void run()
            {

                try
                {
                    s = new Socket(sharedPref.getString("ipAddress",""),Integer.parseInt(sharedPref.getString("portNumber","")));
                    dataIn = new DataInputStream(s.getInputStream());
                    dataOut = new DataOutputStream(s.getOutputStream());

                    while(true)
                    {
                        byte temp = dataIn.readByte();
                        char convert = (char) temp;
                        Message msg = Message.obtain();
                        msg.obj = convert;
                        h1.sendMessage(msg);
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        });
            connectThread.start();
        }

 }
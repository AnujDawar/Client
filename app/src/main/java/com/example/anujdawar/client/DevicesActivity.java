package com.example.anujdawar.client;

//   sharedPref device name recognition
//   voice recognition improvement

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.anujdawar.client.MainActivity.result;
import static com.example.anujdawar.client.MainActivity.textToSpeech;
import static com.example.anujdawar.client.AddCommandIntentClass.db;
import static com.example.anujdawar.client.SavedCommandsActivity.EditFlag;

public class DevicesActivity extends AppCompatActivity {

     Handler h1 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                resultString += msg.obj.toString();

                device1.setEnabled(true);
                device2.setEnabled(true);
                device3.setEnabled(true);
                device4.setEnabled(true);
                device5.setEnabled(true);
                seek1.setEnabled(true);
                seek2.setEnabled(true);
                seek3.setEnabled(true);
                seek4.setEnabled(true);
                seek5.setEnabled(true);

//                if(!startServiceFlag)
//                {
//                    startServiceFlag = true;
//                    Intent intent = new Intent(getApplicationContext(), MyService.class);
//                    startService(intent);
//                }

                if (resultString.contains("SET:")) {

                    int myindex = resultString.indexOf(":");

                    if (resultString.length() == myindex + 6) {

                        setFlag = 0;

                        if (checkStatus1)
                            seek1.setProgress(Integer.parseInt(String.valueOf(resultString.charAt(myindex + 1))));
                        else if (resultString.charAt(myindex + 1) == '0')
                            device1.setChecked(false);
                        else
                            device1.setChecked(true);

                        if (checkStatus2)
                            seek2.setProgress(Integer.parseInt(String.valueOf(resultString.charAt(myindex + 2))));
                        else if (resultString.charAt(myindex + 2) == '0')
                            device2.setChecked(false);
                        else
                            device2.setChecked(true);

                        if (checkStatus3)
                            seek3.setProgress(Integer.parseInt(String.valueOf(resultString.charAt(myindex + 3))));
                        else if (resultString.charAt(myindex + 3) == '0')
                            device3.setChecked(false);
                        else
                            device3.setChecked(true);

                        if (checkStatus4)
                            seek4.setProgress(Integer.parseInt(String.valueOf(resultString.charAt(myindex + 4))));
                        else if (resultString.charAt(myindex + 4) == '0')
                            device4.setChecked(false);
                        else
                            device4.setChecked(true);

                        if (checkStatus5)
                            seek5.setProgress(Integer.parseInt(String.valueOf(resultString.charAt(myindex + 5))));
                        else if (resultString.charAt(myindex + 5) == '0')
                            device5.setChecked(false);
                        else
                            device5.setChecked(true);

                        resultString = "";
                        setFlag = 1;
                    }
                }

                if(resultString.contains("DATA:")) {

                    dataReceivedFlag = true;

                    int myindex = resultString.indexOf(":");

                    if(resultString.length() == myindex + 6) {

                        myTextResult.setText("");

                        if (readFlag == 1)
                        {
                            resultString2 = resultString;
                            readFlag = 2;
                        }

                        else if (readFlag == 2)
                        {
                            int compareResult = resultString.compareTo(resultString2);

                            if (compareResult != 0) {
                                int myindex2 = resultString.indexOf(":");

                                if (resultString.charAt(myindex2 + 1) != resultString2.charAt(myindex2 + 1)) {
                                    if (checkStatus1)
                                    {
                                        convertedText = sharedPref.getString("dev1Name","") + " at Level ";

                                        if(seek1.getProgress() == 4)
                                            convertedText += "Max";

                                        else
                                            convertedText += String.valueOf(seek1.getProgress());

                                        //Toast.makeText(DevicesActivity.this, "Device 1 : " + String.valueOf(seek1.getProgress()),
                                        //        Toast.LENGTH_SHORT).show();
                                    }

                                    else if(device1.isChecked())
                                    {
                                        convertedText = "Device 1 turned on";
//                                        Toast.makeText(DevicesActivity.this, "Device 1 : ON", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        convertedText = "Device 1 turned off";
//                                        Toast.makeText(DevicesActivity.this, "Device 1 : OFF", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (resultString.charAt(myindex2 + 2) != resultString2.charAt(myindex2 + 2)) {
                                    if (checkStatus2)
                                    {
                                        convertedText = sharedPref.getString("dev2Name","") + " at Level ";

                                        if(seek2.getProgress() == 4)
                                            convertedText += "Max";

                                        else
                                            convertedText += String.valueOf(seek2.getProgress());

//                                        Toast.makeText(DevicesActivity.this, "Device 2 : " + String.valueOf(seek2.getProgress()),
//                                                Toast.LENGTH_SHORT).show();
                                    }

                                    else if (device2.isChecked())
                                    {
                                        convertedText = "Device 2 turned on";
//                                        Toast.makeText(DevicesActivity.this, "Device 2 : ON", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        convertedText = "Device 2 turned off";
//                                        Toast.makeText(DevicesActivity.this, "Device 2 : OFF", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (resultString.charAt(myindex2 + 3) != resultString2.charAt(myindex2 + 3)) {
                                    if (checkStatus3)
                                    {
                                        convertedText = sharedPref.getString("dev3Name","") + " at Level ";

                                        if(seek3.getProgress() == 4)
                                            convertedText += "Max";

                                        else
                                            convertedText += String.valueOf(seek3.getProgress());

//                                        Toast.makeText(DevicesActivity.this, "Device 3 : " + String.valueOf(seek3.getProgress()),
//                                                Toast.LENGTH_SHORT).show();
                                    }

                                    else if (device3.isChecked())
                                    {
                                        convertedText = "Device 3 turned on";
//                                        Toast.makeText(DevicesActivity.this, "Device 3 : ON", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        convertedText = "Device 3 turned off";
//                                        Toast.makeText(DevicesActivity.this, "Device 3 : OFF", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (resultString.charAt(myindex2 + 4) != resultString2.charAt(myindex2 + 4)) {
                                    if (checkStatus4)
                                    {
                                        convertedText = sharedPref.getString("dev4Name","") + " at Level ";

                                        if(seek4.getProgress() == 4)
                                            convertedText += "Max";

                                        else
                                            convertedText += String.valueOf(seek4.getProgress());

//                                        Toast.makeText(DevicesActivity.this, "Device 4 : " + String.valueOf(seek4.getProgress()),
//                                                Toast.LENGTH_SHORT).show();
                                    }

                                    else if (device4.isChecked())
                                    {
                                        convertedText = "Device 4 turned on";
//                                        Toast.makeText(DevicesActivity.this, "Device 4 : ON", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        convertedText = "Device 4 turned off";
//                                        Toast.makeText(DevicesActivity.this, "Device 4 : OFF", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                if (resultString.charAt(myindex2 + 5) != resultString2.charAt(myindex2 + 5)) {
                                    if (checkStatus5)
                                    {
                                        convertedText = sharedPref.getString("dev5Name","") + " at Level ";

                                        if(seek5.getProgress() == 4)
                                            convertedText += "Max";

                                        else
                                            convertedText += String.valueOf(seek5.getProgress());

//                                        Toast.makeText(DevicesActivity.this, "Device 5 : " + String.valueOf(seek5.getProgress()),
//                                                Toast.LENGTH_SHORT).show();
                                    }

                                    else if (device5.isChecked())
                                    {
                                        convertedText = "Device 5 turned on";
//                                        Toast.makeText(DevicesActivity.this, "Device 5 : ON", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        convertedText = "Device 5 turned off";
//                                        Toast.makeText(DevicesActivity.this, "Device 5 : OFF", Toast.LENGTH_SHORT).show();
                                    }
                                }

//                                SpeakButtonListener();
                                resultString2 = resultString;
                                resultString = "";
                                dataReceivedFlag = false;
                            }
                        }
                        resultString = "";
                    }
                }
            }
        };

    private static boolean startServiceFlag = false;
    private static String resultString, resultString2;
    private static int readFlag = 1;
    private static int setFlag = 0;
    private static String Output;
    private static int res;
    private static DataOutputStream dataOut;
    private static DataInputStream dataIn;
    private static Socket s;
    private static SharedPreferences sharedPref;
    SharedPreferences.Editor myEditor;
    private static SeekBar seek1, seek2, seek3, seek4, seek5;
    private TextView nameSeek1, nameSeek2, nameSeek3, nameSeek4, nameSeek5;
    private static Switch device1, device2, device3, device4, device5;
    private static String ipAddress;
    private static String port;
    public static String dev1Name;
    public static String dev2Name;
    public static String dev3Name;
    public static String dev4Name;
    public static String dev5Name;
    private static boolean checkStatus1, checkStatus2, checkStatus3, checkStatus4, checkStatus5;
    private static TextView myTextResult;
    protected MediaPlayer Settings_mediaPLayer;
    private static boolean dataReceivedFlag = false;

    private static String textToConvert = "", convertedText = "";
    private ImageButton imgButton;
    private static int micClickedFlag = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.devices_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.settings_id)
        {
            EditFlag = false;

            try {
                dataOut.close();
                dataIn.close();
                s.close();

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            Settings_mediaPLayer = MediaPlayer.create(this, R.raw.slip);
            Intent SettingsIntent = new Intent("com.example.anujdawar.client.MainActivity");
            startActivity(SettingsIntent);
        }

        else if(item.getItemId() == R.id.mic_id) {

            //Thread micThread = new Thread(new Runnable() {
            //    @Override
            //    public void run() {

                   // micClickedFlag = 1;
                    promptSpeechRecognition();
            //    }
            //});
            //micThread.start();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_);

        android.support.v7.app.ActionBar devicesActionBar = getSupportActionBar();
        if (devicesActionBar != null) {
            devicesActionBar.setDisplayUseLogoEnabled(true);
        }
        if (devicesActionBar != null) {
            devicesActionBar.setDisplayShowHomeEnabled(true);
        }
        assert devicesActionBar != null;
        devicesActionBar.setTitle("  Appliances");

        Settings_mediaPLayer = MediaPlayer.create(this, R.raw.slip);

        device1 = (Switch) findViewById(R.id.switch1);
        device2 = (Switch) findViewById(R.id.switch2);
        device3 = (Switch) findViewById(R.id.switch3);
        device4 = (Switch) findViewById(R.id.switch4);
        device5 = (Switch) findViewById(R.id.switch5);

        seek1 = (SeekBar) findViewById(R.id.seekBar1);
        seek2 = (SeekBar) findViewById(R.id.seekBar2);
        seek3 = (SeekBar) findViewById(R.id.seekBar3);
        seek4 = (SeekBar) findViewById(R.id.seekBar4);
        seek5 = (SeekBar) findViewById(R.id.seekBar5);

        nameSeek1 = (TextView) findViewById(R.id.seekName1);
        nameSeek2 = (TextView) findViewById(R.id.seekName2);
        nameSeek3 = (TextView) findViewById(R.id.seekName3);
        nameSeek4 = (TextView) findViewById(R.id.seekName4);
        nameSeek5 = (TextView) findViewById(R.id.seekName5);

        myTextResult = (TextView) findViewById(R.id.myResult);

        sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        ipAddress = sharedPref.getString("ipAddress", "");
        port = sharedPref.getString("portNumber", "");
        dev1Name = sharedPref.getString("Dev1Status", "");
        dev2Name = sharedPref.getString("Dev2Status", "");
        dev3Name = sharedPref.getString("Dev3Status", "");
        dev4Name = sharedPref.getString("Dev4Status", "");
        dev5Name = sharedPref.getString("Dev5Status", "");

        checkStatus1 = Boolean.parseBoolean(sharedPref.getString("CheckBox1",""));
        checkStatus2 = Boolean.parseBoolean(sharedPref.getString("CheckBox2",""));
        checkStatus3 = Boolean.parseBoolean(sharedPref.getString("CheckBox3",""));
        checkStatus4 = Boolean.parseBoolean(sharedPref.getString("CheckBox4",""));
        checkStatus5 = Boolean.parseBoolean(sharedPref.getString("CheckBox5",""));

        device1.setText(dev1Name);
        device2.setText(dev2Name);
        device3.setText(dev3Name);
        device4.setText(dev4Name);
        device5.setText(dev5Name);

        nameSeek1.setText(dev1Name);
        nameSeek2.setText(dev2Name);
        nameSeek3.setText(dev3Name);
        nameSeek4.setText(dev4Name);
        nameSeek5.setText(dev5Name);

        seek1.setVisibility(View.VISIBLE);
        seek2.setVisibility(View.VISIBLE);
        seek3.setVisibility(View.VISIBLE);
        seek4.setVisibility(View.VISIBLE);
        seek5.setVisibility(View.VISIBLE);

        nameSeek1.setVisibility(View.VISIBLE);
        nameSeek2.setVisibility(View.VISIBLE);
        nameSeek3.setVisibility(View.VISIBLE);
        nameSeek4.setVisibility(View.VISIBLE);
        nameSeek5.setVisibility(View.VISIBLE);

        device1.setVisibility(View.VISIBLE);
        device2.setVisibility(View.VISIBLE);
        device3.setVisibility(View.VISIBLE);
        device4.setVisibility(View.VISIBLE);
        device5.setVisibility(View.VISIBLE);


        if (device1.getText().toString().equals(""))
        {
            device1.setVisibility(View.GONE);
            seek1.setVisibility(View.GONE);
            nameSeek1.setVisibility(View.GONE);
        }

        else if(checkStatus1)
            device1.setVisibility(View.INVISIBLE);
        else
        {
            seek1.setVisibility(View.INVISIBLE);
            nameSeek1.setVisibility(View.INVISIBLE);
        }

        if (device2.getText().toString().equals(""))
        {
            device2.setVisibility(View.GONE);
            seek2.setVisibility(View.GONE);
            nameSeek2.setVisibility(View.GONE);
        }

        else if(checkStatus2)
            device2.setVisibility(View.INVISIBLE);
        else
        {
            seek2.setVisibility(View.INVISIBLE);
            nameSeek2.setVisibility(View.INVISIBLE);
        }

        if (device3.getText().toString().equals(""))
        {
            seek3.setVisibility(View.GONE);
            nameSeek3.setVisibility(View.GONE);
            device3.setVisibility(View.GONE);
        }

        else if(checkStatus3)
            device3.setVisibility(View.INVISIBLE);
        else
        {
            seek3.setVisibility(View.INVISIBLE);
            nameSeek3.setVisibility(View.INVISIBLE);
        }

        if (device4.getText().toString().equals(""))
        {
            device4.setVisibility(View.GONE);
            seek4.setVisibility(View.GONE);
            nameSeek4.setVisibility(View.GONE);
        }

        else if(checkStatus4)
            device4.setVisibility(View.INVISIBLE);
        else
        {
            seek4.setVisibility(View.INVISIBLE);
            nameSeek4.setVisibility(View.INVISIBLE);
        }

        if (device5.getText().toString().equals(""))
        {
            seek5.setVisibility(View.GONE);
            nameSeek5.setVisibility(View.GONE);
            device5.setVisibility(View.GONE);
        }

        else if(checkStatus5)
            device5.setVisibility(View.INVISIBLE);
        else
        {
            seek5.setVisibility(View.INVISIBLE);
            nameSeek5.setVisibility(View.INVISIBLE);
        }

        device1.setEnabled(false);
        device2.setEnabled(false);
        device3.setEnabled(false);
        device4.setEnabled(false);
        device5.setEnabled(false);
        seek1.setEnabled(false);
        seek2.setEnabled(false);
        seek3.setEnabled(false);
        seek4.setEnabled(false);
        seek5.setEnabled(false);

        device1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(setFlag == 1) {
                    int state1, state2, state3, state4, state5;

                    if (checkStatus1)
                        state1 = seek1.getProgress();
                    else if (device1.isChecked())
                        state1 = 4;
                    else
                        state1 = 0;

                    if (checkStatus2)
                        state2 = seek2.getProgress();
                    else if (device2.isChecked())
                        state2 = 4;
                    else
                        state2 = 0;

                    if (checkStatus3)
                        state3 = seek3.getProgress();
                    else if (device3.isChecked())
                        state3 = 4;
                    else
                        state3 = 0;

                    if (checkStatus4)
                        state4 = seek4.getProgress();
                    else if (device4.isChecked())
                        state4 = 4;
                    else
                        state4 = 0;

                    if (checkStatus5)
                        state5 = seek5.getProgress();
                    else if (device5.isChecked())
                        state5 = 4;
                    else
                        state5 = 0;

                    click(String.valueOf(state1) + String.valueOf(state2) + String.valueOf(state3) + String.valueOf(state4) + String.valueOf(state5));
                }
            }
        });

        device2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(setFlag == 1) {

                    int state1, state2, state3, state4, state5;

                    if (checkStatus1)
                        state1 = seek1.getProgress();
                    else if (device1.isChecked())
                        state1 = 4;
                    else
                        state1 = 0;

                    if (checkStatus2)
                        state2 = seek2.getProgress();
                    else if (device2.isChecked())
                        state2 = 4;
                    else
                        state2 = 0;

                    if (checkStatus3)
                        state3 = seek3.getProgress();
                    else if (device3.isChecked())
                        state3 = 4;
                    else
                        state3 = 0;

                    if (checkStatus4)
                        state4 = seek4.getProgress();
                    else if (device4.isChecked())
                        state4 = 4;
                    else
                        state4 = 0;

                    if (checkStatus5)
                        state5 = seek5.getProgress();
                    else if (device5.isChecked())
                        state5 = 4;
                    else
                        state5 = 0;

                    click(String.valueOf(state1) + String.valueOf(state2) + String.valueOf(state3) + String.valueOf(state4) + String.valueOf(state5));
                }
            }
        });

        device3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(setFlag == 1) {

                    int state1, state2, state3, state4, state5;

                    if (checkStatus1)
                        state1 = seek1.getProgress();
                    else if (device1.isChecked())
                        state1 = 4;
                    else
                        state1 = 0;

                    if (checkStatus2)
                        state2 = seek2.getProgress();
                    else if (device2.isChecked())
                        state2 = 4;
                    else
                        state2 = 0;

                    if (checkStatus3)
                        state3 = seek3.getProgress();
                    else if (device3.isChecked())
                        state3 = 4;
                    else
                        state3 = 0;

                    if (checkStatus4)
                        state4 = seek4.getProgress();
                    else if (device4.isChecked())
                        state4 = 4;
                    else
                        state4 = 0;

                    if (checkStatus5)
                        state5 = seek5.getProgress();
                    else if (device5.isChecked())
                        state5 = 4;
                    else
                        state5 = 0;

                    click(String.valueOf(state1) + String.valueOf(state2) + String.valueOf(state3) + String.valueOf(state4) + String.valueOf(state5));
                }
            }
        });

        device4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(setFlag == 1) {

                    int state1, state2, state3, state4, state5;

                    if (checkStatus1)
                        state1 = seek1.getProgress();
                    else if (device1.isChecked())
                        state1 = 4;
                    else
                        state1 = 0;

                    if (checkStatus2)
                        state2 = seek2.getProgress();
                    else if (device2.isChecked())
                        state2 = 4;
                    else
                        state2 = 0;

                    if (checkStatus3)
                        state3 = seek3.getProgress();
                    else if (device3.isChecked())
                        state3 = 4;
                    else
                        state3 = 0;

                    if (checkStatus4)
                        state4 = seek4.getProgress();
                    else if (device4.isChecked())
                        state4 = 4;
                    else
                        state4 = 0;

                    if (checkStatus5)
                        state5 = seek5.getProgress();
                    else if (device5.isChecked())
                        state5 = 4;
                    else
                        state5 = 0;

                    click(String.valueOf(state1) + String.valueOf(state2) + String.valueOf(state3) + String.valueOf(state4) + String.valueOf(state5));
                }
            }
        });

        device5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(setFlag == 1) {

                    int state1, state2, state3, state4, state5;

                    if (checkStatus1)
                        state1 = seek1.getProgress();
                    else if (device1.isChecked())
                        state1 = 4;
                    else
                        state1 = 0;

                    if (checkStatus2)
                        state2 = seek2.getProgress();
                    else if (device2.isChecked())
                        state2 = 4;
                    else
                        state2 = 0;

                    if (checkStatus3)
                        state3 = seek3.getProgress();
                    else if (device3.isChecked())
                        state3 = 4;
                    else
                        state3 = 0;

                    if (checkStatus4)
                        state4 = seek4.getProgress();
                    else if (device4.isChecked())
                        state4 = 4;
                    else
                        state4 = 0;

                    if (checkStatus5)
                        state5 = seek5.getProgress();
                    else if (device5.isChecked())
                        state5 = 4;
                    else
                        state5 = 0;

                    click(String.valueOf(state1) + String.valueOf(state2) + String.valueOf(state3) + String.valueOf(state4) + String.valueOf(state5));
                }
            }
        });


        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int yostate;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                yostate = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(setFlag == 1) {

                    int state1, state2, state3, state4, state5;

                    if (checkStatus1)
                        state1 = seek1.getProgress();
                    else if (device1.isChecked())
                        state1 = 4;
                    else
                        state1 = 0;

                    if (checkStatus2)
                        state2 = seek2.getProgress();
                    else if (device2.isChecked())
                        state2 = 4;
                    else
                        state2 = 0;

                    if (checkStatus3)
                        state3 = seek3.getProgress();
                    else if (device3.isChecked())
                        state3 = 4;
                    else
                        state3 = 0;

                    if (checkStatus4)
                        state4 = seek4.getProgress();
                    else if (device4.isChecked())
                        state4 = 4;
                    else
                        state4 = 0;

                    if (checkStatus5)
                        state5 = seek5.getProgress();
                    else if (device5.isChecked())
                        state5 = 4;
                    else
                        state5 = 0;

//                    if(!dataReceivedFlag)
                        click(String.valueOf(state1) + String.valueOf(state2) + String.valueOf(state3) + String.valueOf(state4) + String.valueOf(state5));
                }
            }
        });

        seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int yostate;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b){
                yostate = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(setFlag == 1) {

                    int state1, state2, state3, state4, state5;

                    if (checkStatus1)
                        state1 = seek1.getProgress();
                    else if (device1.isChecked())
                        state1 = 4;
                    else
                        state1 = 0;

                    if (checkStatus2)
                        state2 = seek2.getProgress();
                    else if (device2.isChecked())
                        state2 = 4;
                    else
                        state2 = 0;

                    if (checkStatus3)
                        state3 = seek3.getProgress();
                    else if (device3.isChecked())
                        state3 = 4;
                    else
                        state3 = 0;

                    if (checkStatus4)
                        state4 = seek4.getProgress();
                    else if (device4.isChecked())
                        state4 = 4;
                    else
                        state4 = 0;

                    if (checkStatus5)
                        state5 = seek5.getProgress();
                    else if (device5.isChecked())
                        state5 = 4;
                    else
                        state5 = 0;

                    click(String.valueOf(state1) + String.valueOf(state2) + String.valueOf(state3) + String.valueOf(state4) + String.valueOf(state5));
                }
            }
        });

        seek3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int yostate;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                yostate = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(setFlag == 1) {
                    int state1, state2, state3, state4, state5;

                    if (checkStatus1)
                        state1 = seek1.getProgress();
                    else if (device1.isChecked())
                        state1 = 4;
                    else
                        state1 = 0;

                    if (checkStatus2)
                        state2 = seek2.getProgress();
                    else if (device2.isChecked())
                        state2 = 4;
                    else
                        state2 = 0;

                    if (checkStatus3)
                        state3 = seek3.getProgress();
                    else if (device3.isChecked())
                        state3 = 4;
                    else
                        state3 = 0;

                    if (checkStatus4)
                        state4 = seek4.getProgress();
                    else if (device4.isChecked())
                        state4 = 4;
                    else
                        state4 = 0;

                    if (checkStatus5)
                        state5 = seek5.getProgress();
                    else if (device5.isChecked())
                        state5 = 4;
                    else
                        state5 = 0;

                    click(String.valueOf(state1) + String.valueOf(state2) + String.valueOf(state3) + String.valueOf(state4) + String.valueOf(state5));
                }
            }
        });

        seek4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int yostate;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                yostate = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(setFlag == 1) {

                    int state1, state2, state3, state4, state5;

                    if (checkStatus1)
                        state1 = seek1.getProgress();
                    else if (device1.isChecked())
                        state1 = 4;
                    else
                        state1 = 0;

                    if (checkStatus2)
                        state2 = seek2.getProgress();
                    else if (device2.isChecked())
                        state2 = 4;
                    else
                        state2 = 0;

                    if (checkStatus3)
                        state3 = seek3.getProgress();
                    else if (device3.isChecked())
                        state3 = 4;
                    else
                        state3 = 0;

                    if (checkStatus4)
                        state4 = seek4.getProgress();
                    else if (device4.isChecked())
                        state4 = 4;
                    else
                        state4 = 0;

                    if (checkStatus5)
                        state5 = seek5.getProgress();
                    else if (device5.isChecked())
                        state5 = 4;
                    else
                        state5 = 0;

                    click(String.valueOf(state1) + String.valueOf(state2) + String.valueOf(state3) + String.valueOf(state4) + String.valueOf(state5));
                }
            }
        });

        seek5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int yostate;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                yostate = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(setFlag == 1) {

                    int state1, state2, state3, state4, state5;

                    if (checkStatus1)
                        state1 = seek1.getProgress();
                    else if (device1.isChecked())
                        state1 = 4;
                    else
                        state1 = 0;

                    if (checkStatus2)
                        state2 = seek2.getProgress();
                    else if (device2.isChecked())
                        state2 = 4;
                    else
                        state2 = 0;

                    if (checkStatus3)
                        state3 = seek3.getProgress();
                    else if (device3.isChecked())
                        state3 = 4;
                    else
                        state3 = 0;

                    if (checkStatus4)
                        state4 = seek4.getProgress();
                    else if (device4.isChecked())
                        state4 = 4;
                    else
                        state4 = 0;

                    if (checkStatus5)
                        state5 = seek5.getProgress();
                    else if (device5.isChecked())
                        state5 = 4;
                    else
                        state5 = 0;

                    click(String.valueOf(state1) + String.valueOf(state2) + String.valueOf(state3) + String.valueOf(state4) + String.valueOf(state5));
                }
            }
        });

        res = 0;
        ConnectButtonCall();

        final Thread autoSync = new Thread(new Runnable() {
            @Override
            public void run() {

                while(true) {
                    click("S");
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            }
        });
        autoSync.start();
    }

    public void ConnectButtonCall()
    {

        device1.setEnabled(false);
        device2.setEnabled(false);
        device3.setEnabled(false);
        device4.setEnabled(false);
        device5.setEnabled(false);
        seek1.setEnabled(false);
        seek2.setEnabled(false);
        seek3.setEnabled(false);
        seek4.setEnabled(false);
        seek5.setEnabled(false);

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

    public void click(final String s) {

        Thread sendTo = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    dataOut.writeBytes(s);
                    dataOut.flush();
                    dataOut.flush();

                }catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
        sendTo.start();
    }

    public void promptSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something?");

        try {
            startActivityForResult(intent, 100);
        }catch (ActivityNotFoundException a)
        {
            Toast.makeText(getApplicationContext(), "Your Device Doesn't Support This Language yo", Toast.LENGTH_SHORT).show();
        }
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
           // myTextResult.setText(textToConvert);
        }
    };

    Handler clearScreen = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            myTextResult.setText("");
        }
    };

    public void onActivityResult(int request_code, int result_code, Intent intent)
    {
        super.onActivityResult(request_code, result_code, intent);

        switch(request_code)
        {
            case 100:
                if(result_code == RESULT_OK && intent != null) {
                    ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textToConvert = (result.get(0));

                    textToConvert = textToConvert.toLowerCase();
                    handler.sendEmptyMessage(0);

                    Cursor myCursor = db.viewAllData();

                    boolean milGyaDatabaseMeFlag = false;

                    while (myCursor.moveToNext()) {
                        if (textToConvert.equals(String.valueOf(myCursor.getString(1)))) {
                            String device1State = myCursor.getString(2);
                            String device2State = myCursor.getString(3);
                            String device3State = myCursor.getString(4);
                            String device4State = myCursor.getString(5);
                            String device5State = myCursor.getString(6);

                            if (device1State.equals("ON")) {
                                if (!device1.isChecked())
                                    device1.setChecked(true);
                            } else if (device1State.equals("OFF"))
                                if (device1.isChecked())
                                    device1.setChecked(false);

                            if (device2State.equals("ON")) {
                                if (!device2.isChecked())
                                    device2.setChecked(true);
                            } else if (device2State.equals("OFF"))
                                if (device2.isChecked())
                                    device2.setChecked(false);

                            if (device3State.equals("ON")) {
                                if (!device3.isChecked())
                                    device3.setChecked(true);
                            } else if (device3State.equals("OFF"))
                                if (device3.isChecked())
                                    device3.setChecked(false);

                            if (device4State.equals("ON")) {
                                if (!device4.isChecked())
                                    device4.setChecked(true);
                            } else if (device5State.equals("OFF"))
                                if (device5.isChecked())
                                    device5.setChecked(false);

                            if (device5State.equals("ON")) {
                                if (!device5.isChecked())
                                    device5.setChecked(true);
                            } else if (device5State.equals("OFF"))
                                if (device5.isChecked())
                                    device5.setChecked(false);

                            milGyaDatabaseMeFlag = true;
                            break;
                        }
                    }

                    if (milGyaDatabaseMeFlag == false) {
                        if (

                                textToConvert.contains("device") && textToConvert.contains("one") && textToConvert.contains(" on") ||
                                        (textToConvert.contains("debice") && textToConvert.contains("one") && textToConvert.contains(" on")) ||
                                        (textToConvert.contains("device") && textToConvert.contains("1") && textToConvert.contains(" on")) ||
                                        (textToConvert.contains("debice") && textToConvert.contains("1") && textToConvert.contains(" on")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains(" on") && textToConvert.contains("erst")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains(" on") && textToConvert.contains("isrt")) ||
                                        (textToConvert.contains("ice") && textToConvert.contains(" on") && textToConvert.contains("st")) ||
                                        (textToConvert.contains(dev1Name) && textToConvert.contains(" on"))
                                )

//                        if (setFlag == 1)
                            device1.setChecked(true);

                        else if (textToConvert.contains("ice") && textToConvert.contains("one") && textToConvert.contains(" of") ||
                                (textToConvert.contains("ice") && textToConvert.contains("1") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("un") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("one") && textToConvert.contains("off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("1") && textToConvert.contains("off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("un") && textToConvert.contains("off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains(" off") && textToConvert.contains("st")) ||
                                (textToConvert.contains("ice") && textToConvert.contains(" off") && textToConvert.contains("erst")) ||
                                (textToConvert.contains("ice") && textToConvert.contains(" off") && textToConvert.contains("irst")) ||
                                (textToConvert.contains("ice") && textToConvert.contains(" of") && textToConvert.contains("st")) ||
                                (textToConvert.contains("ice") && textToConvert.contains(" of") && textToConvert.contains("erst")) ||
                                (textToConvert.contains("ice") && textToConvert.contains(" of") && textToConvert.contains("irst")) ||
                                (textToConvert.contains(dev1Name) && textToConvert.contains("off")) ||
                                (textToConvert.contains(dev1Name) && textToConvert.contains("of"))
                                )

                            device1.setChecked(false);

                        if (textToConvert.contains("ice") && textToConvert.contains("two") && textToConvert.contains(" on") ||
                                (textToConvert.contains("ice") && textToConvert.contains("to") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("2") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("nd") && textToConvert.contains(" on")) ||
                                (textToConvert.contains(dev2Name) && textToConvert.contains(" on"))
                                )

                            device2.setChecked(true);

                        else if (textToConvert.contains("ice") && textToConvert.contains("two") && textToConvert.contains(" off") ||
                                (textToConvert.contains("ice") && textToConvert.contains("two") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("to") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("to") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("2") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("2") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("nd") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("nd") && textToConvert.contains(" of")) ||
                                (textToConvert.contains(dev2Name) && textToConvert.contains(" off")) ||
                                (textToConvert.contains(dev2Name) && textToConvert.contains(" of"))

                                )

                            device2.setChecked(false);

                        if (textToConvert.contains("ice") && textToConvert.contains("three") && textToConvert.contains(" on") ||
                                (textToConvert.contains("ice") && textToConvert.contains("thre") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("thri") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("tree") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("3") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("3") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rd") && textToConvert.contains(" on")) ||
                                (textToConvert.contains(dev3Name) && textToConvert.contains(" on"))
                                )

                            device3.setChecked(true);

                        else if (textToConvert.contains("ice") && textToConvert.contains("three") && textToConvert.contains(" off") ||
                                (textToConvert.contains("ice") && textToConvert.contains("thre") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("thri") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("tree") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("3") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("three") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("thre") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("thri") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("tree") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("3") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rd") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rd") && textToConvert.contains(" of")) ||
                                (textToConvert.contains(dev3Name) && textToConvert.contains(" off")) ||
                                (textToConvert.contains(dev3Name) && textToConvert.contains(" of"))

                                )

                            device3.setChecked(false);

                        if (textToConvert.contains("ice") && textToConvert.contains("four") && textToConvert.contains(" on") ||
                                (textToConvert.contains("ice") && textToConvert.contains("for") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("fur") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("4") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("phore") && textToConvert.contains(" on")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rth") && textToConvert.contains(" on")) ||
                                (textToConvert.contains(dev4Name) && textToConvert.contains(" on"))
                                )

                            device4.setChecked(true);

                        else if (textToConvert.contains("ice") && textToConvert.contains("four") && textToConvert.contains(" off") ||
                                (textToConvert.contains("ice") && textToConvert.contains("for") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("fur") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("4") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("phore") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("four") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("for") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("fur") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("4") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("phore") && textToConvert.contains(" of")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rth") && textToConvert.contains(" off")) ||
                                (textToConvert.contains("ice") && textToConvert.contains("rth") && textToConvert.contains(" of")) ||
                                (textToConvert.contains(dev4Name) && textToConvert.contains(" off")) ||
                                (textToConvert.contains(dev4Name) && textToConvert.contains(" of"))
                                )

                            device4.setChecked(false);

                        if (textToConvert.equals("device 5 on") || textToConvert.equals("device five on"))
                            device5.setChecked(true);

                        else if (textToConvert.equals("fuck off") || textToConvert.equals("fuck of"))
                            device5.setChecked(false);
                    }
                    // call SpeakButtonListener() when you receive data from esp
                    break;

                }
        }
    }

    public void SpeakButtonListener() {

        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA)
            Toast.makeText(getApplicationContext(), "your device doesn't support this feature", Toast.LENGTH_SHORT).show();

        else
            textToSpeech.speak(convertedText, TextToSpeech.QUEUE_FLUSH, null);

        convertedText = "";
    }
}

package com.example.anujdawar.client;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.anujdawar.client.DevicesActivity.dev1Name;
import static com.example.anujdawar.client.DevicesActivity.dev2Name;
import static com.example.anujdawar.client.DevicesActivity.dev3Name;
import static com.example.anujdawar.client.DevicesActivity.dev4Name;
import static com.example.anujdawar.client.DevicesActivity.dev5Name;
import static com.example.anujdawar.client.MainActivity.sharedPref;
import static com.example.anujdawar.client.SavedCommandsActivity.EditFlag;
import static com.example.anujdawar.client.SavedCommandsActivity.tempCommand;
import static com.example.anujdawar.client.SavedCommandsActivity.tempDev1;
import static com.example.anujdawar.client.SavedCommandsActivity.tempDev2;
import static com.example.anujdawar.client.SavedCommandsActivity.tempDev3;
import static com.example.anujdawar.client.SavedCommandsActivity.tempDev4;
import static com.example.anujdawar.client.SavedCommandsActivity.tempDev5;
import static com.example.anujdawar.client.SavedCommandsActivity.tempID;

public class AddCommandIntentClass extends AppCompatActivity {

    protected static boolean SaveSuccessError = false;
    protected static ArrayAdapter<CharSequence> adapter;
    protected static EditText customInputCommandText;
    protected static TextView device1CustomName, device2CustomName, device3CustomName, device4CustomName, device5CustomName;
    protected static Spinner customSpinner1, customSpinner2, customSpinner3, customSpinner4, customSpinner5;
    protected static ImageButton customMicButton;
    protected static String inputText;

    protected static DatabaseForSavedCommands db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_command_menu_file, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.viewSavedCommands_id:

                Intent addCommandIntent = new Intent("com.example.anujdawar.client.SavedCommandsActivity");
                startActivity(addCommandIntent);
                break;

            case R.id.settings_id:

                Intent SettingsIntent = new Intent("com.example.anujdawar.client.MainActivity");
                startActivity(SettingsIntent);
                break;

            case R.id.saveCommand_id:

                if (customInputCommandText.getText().toString().equals(""))
                    Toast.makeText(this, "Custom Command Cannot Be Empty", Toast.LENGTH_SHORT).show();

                else if (EditFlag) {
                    boolean isUpdate = db.updateData(tempID,
                            customInputCommandText.getText().toString(),
                            customSpinner1.getSelectedItem().toString(),
                            customSpinner2.getSelectedItem().toString(),
                            customSpinner3.getSelectedItem().toString(),
                            customSpinner4.getSelectedItem().toString(),
                            customSpinner5.getSelectedItem().toString()
                    );

                    if (isUpdate)
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Not Updated", Toast.LENGTH_SHORT).show();

                    customInputCommandText.setText("");
                    customSpinner1.setSelection(0);
                    customSpinner2.setSelection(0);
                    customSpinner3.setSelection(0);
                    customSpinner4.setSelection(0);
                    customSpinner5.setSelection(0);
                } else {

                    inputText = customInputCommandText.getText().toString();

                    boolean ifInserted = db.insertData(customInputCommandText.getText().toString(),
                            customSpinner1.getSelectedItem().toString(),
                            customSpinner2.getSelectedItem().toString(),
                            customSpinner3.getSelectedItem().toString(),
                            customSpinner4.getSelectedItem().toString(),
                            customSpinner5.getSelectedItem().toString()
                    );

                    if (ifInserted)
                        Toast.makeText(this, "Command Inserted", Toast.LENGTH_SHORT).show();

                    else
                        Toast.makeText(this, "Command Not Inserted", Toast.LENGTH_SHORT).show();

                    customInputCommandText.setText("");
                    customSpinner1.setSelection(0);
                    customSpinner2.setSelection(0);
                    customSpinner3.setSelection(0);
                    customSpinner4.setSelection(0);
                    customSpinner5.setSelection(0);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_command);

        setTitle(R.string.addCommandActivity);

        dev1Name = sharedPref.getString("Dev1Status", "");
        dev2Name = sharedPref.getString("Dev2Status", "");
        dev3Name = sharedPref.getString("Dev3Status", "");
        dev4Name = sharedPref.getString("Dev4Status", "");
        dev5Name = sharedPref.getString("Dev5Status", "");

        customInputCommandText = (EditText) findViewById(R.id.customCommandTyped);

        device1CustomName = (TextView) findViewById(R.id.addCommandDevice1);
        device2CustomName = (TextView) findViewById(R.id.addCommandDevice2);
        device3CustomName = (TextView) findViewById(R.id.addCommandDevice3);
        device4CustomName = (TextView) findViewById(R.id.addCommandDevice4);
        device5CustomName = (TextView) findViewById(R.id.addCommandDevice5);

        String a = "Device1";
        String b = "Device2";
        String c = "Device3";
        String d = "Device4";
        String e = "Device5";

        if (dev1Name.equals(""))
            device1CustomName.setText(a);
        else
            device1CustomName.setText(dev1Name);

        if (dev2Name.equals(""))
            device2CustomName.setText(b);
        else
            device2CustomName.setText(dev2Name);

        if (dev3Name.equals(""))
            device3CustomName.setText(c);
        else
            device3CustomName.setText(dev3Name);

        if (dev4Name.equals(""))
            device4CustomName.setText(d);
        else
            device4CustomName.setText(dev4Name);

        if (dev5Name.equals(""))
            device5CustomName.setText(e);
        else
            device5CustomName.setText(dev5Name);

        customSpinner1 = (Spinner) findViewById(R.id.spinner1);
        customSpinner2 = (Spinner) findViewById(R.id.spinner2);
        customSpinner3 = (Spinner) findViewById(R.id.spinner3);
        customSpinner4 = (Spinner) findViewById(R.id.spinner4);
        customSpinner5 = (Spinner) findViewById(R.id.spinner5);

        customInputCommandText.setText("");
        customSpinner1.setSelection(0);
        customSpinner2.setSelection(0);
        customSpinner3.setSelection(0);
        customSpinner4.setSelection(0);
        customSpinner5.setSelection(0);

        adapter = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.status_of_devices_spinner));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        customSpinner1.setAdapter(adapter);
        customSpinner2.setAdapter(adapter);
        customSpinner3.setAdapter(adapter);
        customSpinner4.setAdapter(adapter);
        customSpinner5.setAdapter(adapter);

        db = new DatabaseForSavedCommands(this);
        customMicButton = (ImageButton) findViewById(R.id.micButtonIDcommandinput);

        if (EditFlag) {
            customInputCommandText.setText(tempCommand);

            int myPos = 0;

            switch (tempDev1) {
                case "NONE":
                    myPos = 0;
                    break;
                case "ON":
                    myPos = 1;
                    break;
                case "OFF":
                    myPos = 2;
                    break;
            }

            customSpinner1.setSelection(myPos);

            switch (tempDev2) {
                case "NONE":
                    myPos = 0;
                    break;
                case "ON":
                    myPos = 1;
                    break;
                case "OFF":
                    myPos = 2;
                    break;
            }

            customSpinner2.setSelection(myPos);

            switch (tempDev3) {
                case "NONE":
                    myPos = 0;
                    break;
                case "ON":
                    myPos = 1;
                    break;
                case "OFF":
                    myPos = 2;
                    break;
            }

            customSpinner3.setSelection(myPos);

            switch (tempDev4) {
                case "NONE":
                    myPos = 0;
                    break;
                case "ON":
                    myPos = 1;
                    break;
                case "OFF":
                    myPos = 2;
                    break;
            }

            customSpinner4.setSelection(myPos);

            switch (tempDev5) {
                case "NONE":
                    myPos = 0;
                    break;
                case "ON":
                    myPos = 1;
                    break;
                case "OFF":
                    myPos = 2;
                    break;
            }
            customSpinner5.setSelection(myPos);
        }
        else {
            customInputCommandText.setText("");
            customSpinner1.setSelection(0);
            customSpinner2.setSelection(0);
            customSpinner3.setSelection(0);
            customSpinner4.setSelection(0);
            customSpinner5.setSelection(0);
        }

        customMicButton = (ImageButton) findViewById(R.id.micButtonIDcommandinput);

        micButtonOnClickListener();
    }

    public void micButtonOnClickListener() {

        customMicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeech();
            }
        });
    }

    public void promptSpeech() {

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something?");

            try {
                startActivityForResult(intent, 100);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(getApplicationContext(), "Your Device Doesn't Support This Language yo", Toast.LENGTH_SHORT).show();
            }
        }

    public void onActivityResult(int request_code, int result_code, Intent intent) {
        super.onActivityResult(request_code, result_code, intent);

        switch (request_code) {
            case 100:
                if (result_code == RESULT_OK && intent != null) {
                    ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String textToFill = (result.get(0));
                    textToFill = textToFill.toLowerCase();

                    customInputCommandText.setText(textToFill);
                }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

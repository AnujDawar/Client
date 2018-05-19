package com.example.anujdawar.client;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.anujdawar.client.AddCommandIntentClass.db;

public class SavedCommandsActivity extends AppCompatActivity {

    Handler sizeHandle1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(SavedCommandsActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    };

    Handler sizeHandle2 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(SavedCommandsActivity.this, "Not Deleted", Toast.LENGTH_SHORT).show();
        }
    };


    protected static ListView lvProduct;
    protected static ProductListAdapter adapter;
    protected static List<Product> mProductList;

    protected static boolean EditFlag = false;
    protected static String tempID, tempDev1, tempDev2, tempDev3, tempDev4, tempDev5, tempCommand;

    protected static int positionOfCommandSelected;
    protected static int EditOrDeleteSelected;
    protected static TextView noDataFoundTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_commands);

        setTitle(R.string.savedCommandTitle);

        lvProduct = (ListView) findViewById(R.id.myListID);
        mProductList = new ArrayList<>();

        noDataFoundTV = (TextView) findViewById(R.id.noDataFoundID);

        this.registerForContextMenu(lvProduct);

        ViewDataAll();
    }

    public void ViewDataAll()
    {
        Cursor res = db.viewAllData();

        if(res.getCount() == 0)
        {
            String temp = "No Data Found";
            lvProduct.setVisibility(View.INVISIBLE);
        }

        else
            lvProduct.setVisibility(View.VISIBLE);

        mProductList.clear();

        while(res.moveToNext())
        {
            mProductList.add(new Product(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6)));
        }

        adapter = new ProductListAdapter(getApplicationContext(), mProductList);
        lvProduct.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.saved_command_menu_file,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.eraseId:

                EditFlag = false;

                AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
                a_builder.setMessage("Delete All Saved Commands ?").setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    db.deleteAll();
                    ViewDataAll();
                    dialogInterface.cancel();
                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                AlertDialog alert = a_builder.create();
                alert.setTitle("ALERT !!!");
                alert.show();

                break;

            case R.id.settings_id:
                EditFlag = false;
                Intent SettingsIntent = new Intent("com.example.anujdawar.client.MainActivity");
                startActivity(SettingsIntent);
                break;

            case R.id.addCommands_id:
                EditFlag = false;
                Intent SaveCommandIntent = new Intent("com.example.anujdawar.client.AddCommandIntentClass");
                startActivity(SaveCommandIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if(v.getId() == R.id.myListID)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

            CharSequence temp;
            temp = mProductList.get(info.position).getMyCommand();

            menu.setHeaderTitle(temp);

            positionOfCommandSelected = info.position;

            String[] menuItems = {"Edit","Delete"};

            for(int i = 0; i < menuItems.length; i++)
                menu.add(Menu.NONE, i, i, menuItems[i]);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();

        Cursor res = db.viewAllData();
        res.moveToPosition(positionOfCommandSelected);

        if (menuItemIndex == 0)
        {
            EditFlag = true;

            tempID = res.getString(0);
            tempCommand = res.getString(1);
            tempDev1 = res.getString(2);
            tempDev2 = res.getString(3);
            tempDev3 = res.getString(4);
            tempDev4 = res.getString(5);
            tempDev5 = res.getString(6);

            Intent tempIntent = new Intent("com.example.anujdawar.client.AddCommandIntentClass");
            startActivity(tempIntent);

        }

        else {

            Integer MyResult = db.deleteData(String.valueOf(res.getString(0)));

            if(MyResult > 0)
                sizeHandle1.sendEmptyMessage(0);

            else
                sizeHandle2.sendEmptyMessage(0);

            ViewDataAll();
            return true;
        }

        return true;
    }
}

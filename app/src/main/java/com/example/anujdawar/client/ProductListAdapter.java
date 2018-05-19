package com.example.anujdawar.client;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import static com.example.anujdawar.client.DevicesActivity.dev1Name;
import static com.example.anujdawar.client.DevicesActivity.dev2Name;
import static com.example.anujdawar.client.DevicesActivity.dev3Name;
import static com.example.anujdawar.client.DevicesActivity.dev4Name;
import static com.example.anujdawar.client.DevicesActivity.dev5Name;
import static com.example.anujdawar.client.MainActivity.sharedPref;

public class ProductListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Product> mProductList;

    protected TextView nameDev1, nameDev2, nameDev3, nameDev4, nameDev5;

    public ProductListAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return mProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(mContext, R.layout.text_views, null);

        TextView tvCommand = (TextView) v.findViewById(R.id.commandID);

        nameDev1 = (TextView) v.findViewById(R.id.savedNameDeviceID1);
        nameDev2 = (TextView) v.findViewById(R.id.savedNameDeviceID2);
        nameDev3 = (TextView) v.findViewById(R.id.savedNameDeviceID3);
        nameDev4 = (TextView) v.findViewById(R.id.savedNameDeviceID4);
        nameDev5 = (TextView) v.findViewById(R.id.savedNameDeviceID5);

        TextView tvdev1 = (TextView) v.findViewById(R.id.tv_dev1ID);
        TextView tvdev2 = (TextView) v.findViewById(R.id.tv_dev2ID);
        TextView tvdev3 = (TextView) v.findViewById(R.id.tv_dev3ID);
        TextView tvdev4 = (TextView) v.findViewById(R.id.tv_dev4ID);
        TextView tvdev5 = (TextView) v.findViewById(R.id.tv_dev5ID);

        dev1Name = sharedPref.getString("Dev1Status", "");
        dev2Name = sharedPref.getString("Dev2Status", "");
        dev3Name = sharedPref.getString("Dev3Status", "");
        dev4Name = sharedPref.getString("Dev4Status", "");
        dev5Name = sharedPref.getString("Dev5Status", "");

        String a = "Device 1";
        String b = "Device 2";
        String c = "Device 3";
        String d = "Device 4";
        String e = "Device 5";

        if(dev1Name.equals(""))
            nameDev1.setText(a);
        else
            nameDev1.setText(dev1Name);

        if(dev2Name.equals(""))
            nameDev2.setText(b);
        else
            nameDev2.setText(dev2Name);

        if(dev3Name.equals(""))
            nameDev3.setText(c);
        else
            nameDev3.setText(dev3Name);

        if(dev4Name.equals(""))
            nameDev4.setText(d);
        else
            nameDev4.setText(dev4Name);

        if(dev5Name.equals(""))
            nameDev5.setText(e);
        else
            nameDev5.setText(dev5Name);

        tvCommand.setText(mProductList.get(i).getMyCommand());

        tvdev1.setText(String.valueOf(mProductList.get(i).getDev1()));
        tvdev2.setText(String.valueOf(mProductList.get(i).getDev2()));
        tvdev3.setText(String.valueOf(mProductList.get(i).getDev3()));
        tvdev4.setText(String.valueOf(mProductList.get(i).getDev4()));
        tvdev5.setText(String.valueOf(mProductList.get(i).getDev5()));

        v.setTag(mProductList.get(i).getID());

        return v;
    }
}

package com.example.tzadmin.nfc_reader_writer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tzadmin.nfc_reader_writer.Fonts.SingletonFonts;
import com.example.tzadmin.nfc_reader_writer.Models.Route;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;
import com.example.tzadmin.nfc_reader_writer.R;

import java.util.ArrayList;

/**
 * Created by velor on 7/2/17.
 */

public class ShopAdapterV2 extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Shop> states;

    public ShopAdapterV2(Context context, ArrayList<Shop> state){
        this.context = context;
        this.states = state;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return states.size();
    }

    @Override
    public Object getItem(int position) {
        return states.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(R.layout.route_item, null);

        TextView myrectangle = (TextView)convertView.findViewById(R.id.route_rectangle);
        TextView square = (TextView)convertView.findViewById(R.id.route_square);

        myrectangle.setTypeface(SingletonFonts.getInstanse(context).getKarlson());
        square.setTypeface(SingletonFonts.getInstanse(context).getKarlson());

        Shop route = states.get(position);

        myrectangle.setText(route.name);
        square.setText(String.valueOf(route.price));

        return convertView;
    }
}
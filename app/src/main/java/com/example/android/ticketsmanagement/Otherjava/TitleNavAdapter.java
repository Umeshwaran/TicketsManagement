package com.example.android.ticketsmanagement.Otherjava;

/**
 * Created by Android on 3/4/2018.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.ticketsmanagement.R;

public class TitleNavAdapter extends BaseAdapter {

    private TextView id;
    private TextView txtTitle;
    private ArrayList<SpinnerNav> spinnerNavItem;
    private Context context;

    public TitleNavAdapter(Context context,
                           ArrayList<SpinnerNav> spinnerNavItem) {
        this.spinnerNavItem = spinnerNavItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return spinnerNavItem.size();
    }

    @Override
    public Object getItem(int index) {
        return spinnerNavItem.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_title_nav, null);
        }

        id = (TextView) convertView.findViewById(R.id.id);
        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);

        id.setText(spinnerNavItem.get(position).getid());
        id.setVisibility(View.GONE);
        txtTitle.setText(spinnerNavItem.get(position).getTitle());
        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_title_nav, null);
        }

        id = (TextView) convertView.findViewById(R.id.id);
        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);

        id.setText(spinnerNavItem.get(position).getid());
        txtTitle.setText(spinnerNavItem.get(position).getTitle());
        return convertView;
    }

}
package com.example.android.ticketsmanagement.Otherjava;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ticketsmanagement.Admin;
import com.example.android.ticketsmanagement.R;

import java.util.List;

import static android.R.color.holo_red_light;

/**
 * Created by Android on 11/10/2017.
 */

public class AdminAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Adclass> userlist;

    public AdminAdapter(Activity activity, List<Adclass> userlist) {
        this.activity = activity;
        this.userlist = userlist;
    }

    public long getId(int position) {
        return position;
    }

    public int getCount() {
        return userlist.size();
    }


    public Object getItem(int position) {
        return userlist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
            );
        if (convertView == null)
            convertView = inflater.inflate(R.layout.layout_admin_row, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView name = (TextView) convertView.findViewById(R.id.Name);

        ImageView image = (ImageView) convertView.findViewById(R.id.thumbnail);

        Adclass adclass = userlist.get(position);
        id.setText("ID-" + adclass.getUid());
        name.setText(adclass.getName());

        String f = adclass.getflag();

        String img = adclass.getUcode();
        if (img.equals("E")) {
            image.setImageResource(R.drawable.engineer);

        } else if (img.equals("M")) {
            image.setImageResource(R.drawable.manager);

        } else if (img.equals("U")) {
            image.setImageResource(R.drawable.usericon);

        } else if (img.equals("Null"))
            image.setImageResource(R.drawable.error);
        //Toast.makeText(convertView.getContext(),img,Toast.LENGTH_LONG).show();

        return convertView;
    }
}

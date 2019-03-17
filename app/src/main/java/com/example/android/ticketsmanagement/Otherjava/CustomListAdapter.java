package com.example.android.ticketsmanagement.Otherjava;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ticketsmanagement.R;
import com.example.android.ticketsmanagement.SecondActivity;

import java.util.List;
import java.util.Locale;

/**
 * Created by Android on 10/31/2017.
 */

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Ticket> ticketList;
    private List<Ticket> filteredList;

    public CustomListAdapter(Activity activity, List<Ticket> ticketList) {
        this.activity = activity;
        this.ticketList = ticketList;
        this.filteredList = ticketList;
    }

    public long getId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return ticketList.size();
    }

    @Override
    public Object getItem(int position) {
        return ticketList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
            );
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView desc = (TextView) convertView.findViewById(R.id.description);

        ImageView image = (ImageView) convertView.findViewById(R.id.thumbnail);

        Ticket ticket = ticketList.get(position);
        id.setText("T" + ticket.getTicketid());
        desc.setText(ticket.getDesc());

        String img = ticket.getstatus();
        if (img.equals("1")) {
            image.setImageResource(R.drawable.red);

        } else if (img.equals("2")) {
            image.setImageResource(R.drawable.yellow);

        } else if (img.equals("3")) {
            image.setImageResource(R.drawable.yellow_warning);

        } else if (img.equals("4")) {
            image.setImageResource(R.drawable.green);

        } else if (img.equals("5")) {
            image.setImageResource(R.drawable.black);

        } else if (img.equals("10")) {
            image.setImageResource(R.drawable.error);

        }
//        image.setImageResource(R.drawable.red);
        //Toast.makeText(convertView.getContext(),img,Toast.LENGTH_LONG).show();

        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ticketList.clear();
        if (charText.length() == 0) {
            ticketList.addAll(filteredList);
        } else {
            for (Ticket wp : filteredList) {
                if (wp.gettitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    ticketList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

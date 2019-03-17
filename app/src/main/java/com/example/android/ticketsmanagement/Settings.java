package com.example.android.ticketsmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.ticketsmanagement.Otherjava.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 1/20/2018.
 */

public class Settings extends AppCompatActivity {
    private ListView listView;
    private List<Ticket> ticketList = new ArrayList<Ticket>();
    private Ticket tick = new Ticket();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

    }

}

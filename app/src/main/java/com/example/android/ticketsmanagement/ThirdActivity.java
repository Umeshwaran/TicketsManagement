package com.example.android.ticketsmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.ticketsmanagement.Otherjava.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {
    AlertDialog.Builder builder;
    EditText title, description, contact;
    Spinner location, userspin;
    String ptitle, pdesc, ploc, pcon, puserspin;

    String URL_FOR_CREATE = Constants.URL_FOR_CREATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        builder = new AlertDialog.Builder(ThirdActivity.this);

        Bundle third = getIntent().getExtras();
        final String id = third.getString("id");
        final String ucode = third.getString("ucode");
        String users[][] = (String[][]) third.getSerializable("users");

        final int len = third.getInt("len");
        final ArrayList<String> user = new ArrayList<String>();
        user.add("User Select");
        for (int i = 0; i < len; i++) {
            String E = users[i][2];
            if (E.equals("U")) {
                String s = users[i][0];//.toString().concat(" - ").concat(users[i][1]);
                user.add(s);
            }
        }

        title = (EditText) findViewById(R.id.Ticket_title);
        description = (EditText) findViewById(R.id.description);
        location = (Spinner) findViewById(R.id.location);
        contact = (EditText) findViewById(R.id.contact);
        userspin = (Spinner) findViewById(R.id.spinincreate);
        ArrayAdapter adap = new ArrayAdapter(this, R.layout.spinner_layout, R.id.text, user);
        userspin.setAdapter(adap);

        if (ucode.equals("U")) {
            userspin.setVisibility(View.GONE);
        }

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       ptitle = title.getText().toString();
                                       pdesc = description.getText().toString();
                                       puserspin = userspin.getSelectedItem().toString();

                                       // ploc=location.getText().toString();
                                       ploc = location.getSelectedItem().toString();
                                       pcon = contact.getText().toString();

                                       if (ptitle.equals("") || pdesc.equals("") || pcon.equals("") || ploc.equals("")) {
                                           builder.setTitle("Details Needed");
                                           displayAlert("Please fill all the details");
                                       } else {

                                           StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FOR_CREATE,
                                                   new Response.Listener<String>() {

                                                       @Override
                                                       public void onResponse(String response) {
                                                           try {
                                                               JSONObject jsonObject = new JSONObject(response);
                                                               JSONArray jsonArray = jsonObject.getJSONArray("create");
                                                               JSONObject loginobj = jsonArray.getJSONObject(0);

                                                               String code = loginobj.getString("code");
                                                               //   Toast.makeText(ThirdActivity.this,code,Toast.LENGTH_LONG).show();
                                                               if (code.equals("created")) {
                                                                   builder.setTitle("Created");
                                                                   successAlert("Ticket created successfully");
                                                               }
                                                           } catch (JSONException e) {

                                                               builder.setTitle("Error");
                                                               successAlert("Error in creating ticket");

                                                               e.printStackTrace();
                                                           }
                                                       }

                                                   }, new Response.ErrorListener() {
                                               @Override
                                               public void onErrorResponse(VolleyError error) {
                                                   Toast.makeText(ThirdActivity.this, "Error.Connect to the server", Toast.LENGTH_LONG).show();
                                                   error.printStackTrace();
                                               }
                                           }) {
                                               @Override
                                               protected Map<String, String> getParams() throws AuthFailureError {
                                                   Map<String, String> params = new HashMap<String, String>();
                                                   params.put("title", ptitle);
                                                   params.put("desc", pdesc);
                                                   params.put("locat", ploc);
                                                   params.put("contact", pcon);
                                                   if (userspin.getSelectedItem().toString().equals("User Select")) {
                                                       params.put("created", id);

                                                   } else {

                                                       params.put("created", puserspin);
                                                   }

                                                   params.put("status", "1");
                                                   if (ucode.equals("E")) {
                                                       params.put("assign", id);

                                                   } else {
                                                       params.put("assign", "0");
                                                   }
                                                   return params;

                                               }
                                           };
                                           AppSingleton.getInstance(ThirdActivity.this).addToRequestQueue(stringRequest);

                                       }

                                   }
                               }
        );

    }

    public void displayAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        ;
    }

    public void successAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(ThirdActivity.this, SecondActivity.class));

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        ;
    }
}

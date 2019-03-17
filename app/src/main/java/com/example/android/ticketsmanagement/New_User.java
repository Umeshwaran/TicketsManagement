package com.example.android.ticketsmanagement;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.ticketsmanagement.Otherjava.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Android on 4/26/2018.
 */

public class New_User extends AppCompatActivity {

    Spinner location, user;
    EditText name, contact;
    String uname, uloc, ucontact, uuser;
    String senduser, sendloc;
    Button createuser;
    Constants c;
    String URL_TO_CREATE_USER = Constants.URL_TO_CREATE_USER;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        name = (EditText) findViewById(R.id.Name_new);
        contact = (EditText) findViewById(R.id.contactu_new);
        location = (Spinner) findViewById(R.id.spin_new);
        createuser = (Button) findViewById(R.id.create_btn_new);
        user = (Spinner) findViewById(R.id.spinuser_new);
        builder = new AlertDialog.Builder(New_User.this);

        createuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = name.getText().toString();
                ucontact = contact.getText().toString();
                uloc = location.getSelectedItem().toString();
                uuser = user.getSelectedItem().toString();

                if (uloc.equals("Select a branch")) {
                    sendloc = "";
                } else {
                    sendloc = uloc;
                }

                if (uuser.equals("Select an user")) {
                    senduser = "";
                }

                if (uuser.equals("User")) {
                    senduser = "U";

                } else if (uuser.equals("Engineer")) {
                    senduser = "E";

                } else if (uuser.equals("Manager")) {
                    senduser = "M";
                }
                if (senduser.equals("") || sendloc.equals("") || uname.equals("") || ucontact.equals("")) {
                    builder.setTitle("Check the details");
                    displayAlert("Select pannu");
                } else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TO_CREATE_USER,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("create");
                                        JSONObject loginobj = jsonArray.getJSONObject(0);

                                        String code = loginobj.getString("code");

                                        // String user_code=loginobj.getString("user");
                                        if (code.equals("create")) {
                                            builder.setTitle("Success");

                                            successAlert("Successful creation");
                                        }
                                    } catch (JSONException e) {
                                        builder.setTitle("Create");
                                        displayAlert("User Created");

                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            builder.setTitle("Error");
                            displayAlert("Check credentials or Connect to server");
                            Intent in = new Intent(New_User.this, Admin.class);
                            startActivity(in);
                            //Toast.makeText(MainActivity.this,"Error.Connect to the server",Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", uname);
                            params.put("cont", ucontact);
                            params.put("usercode", senduser);
                            params.put("loc", sendloc);
                            params.put("flag", "0");
                            return params;
                        }
                    };
                    AppSingleton.getInstance(New_User.this).addToRequestQueue(stringRequest);

                }
            }

        });

    }

    public void displayAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                // startActivity(new Intent(Create_User.this,Create_User.class));
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

                // startActivity(new Intent(New_User.this,MainActivity.class));

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        ;
    }
}

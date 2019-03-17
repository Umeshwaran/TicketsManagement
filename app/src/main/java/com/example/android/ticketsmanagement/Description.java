package com.example.android.ticketsmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.android.ticketsmanagement.Otherjava.CustomListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Description extends AppCompatActivity {
    TextView tid, tck, desctxt, res;
    Button save;
    Spinner assignspin;
    Spinner statusspin;
    String assignsend = "0";
    Constants c;
    String URL_FOR_DESC = Constants.URL_FOR_DESC;
    String URL_FOR_SAVE = Constants.URL_FOR_SAVE;
    String URL_FOR_NOTIFICATION = Constants.URL_NOTIFY;
    AlertDialog.Builder builder, errbuild, savebuild;
    String created;
    String login, bassign, aassign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        builder = new AlertDialog.Builder(Description.this);

        //TextView ts=(TextView)findViewById(R.id.status_title);
        tid = (TextView) findViewById(R.id.tid);
        tck = (TextView) findViewById(R.id.tck);
        desctxt = (TextView) findViewById(R.id.desctxt);
        res = (TextView) findViewById(R.id.resolutiontxt);
        save = (Button) findViewById(R.id.savebtn);

        assignspin = (Spinner) findViewById(R.id.assignspin);
        statusspin = (Spinner) findViewById(R.id.statusspin);

        Bundle b = getIntent().getExtras();
        final String ttid = b.getString("tidfordesc");
        String ttit = b.getString("desc");
        final String ucode = b.getString("ucode");
        login = b.getString("login");
        final String users[][] = (String[][]) b.getSerializable("users");

        final int len = b.getInt("len");
        final ArrayList<String> engg = new ArrayList<String>();
        final ArrayList<String> enggid = new ArrayList<String>();
        engg.add("Unassigned");
        for (int i = 0; i < len; i++) {
            String E = users[i][2];
            if (E.equals("E")) {
                String s = users[i][0];//.toString().concat(" - ").concat(users[i][1]);
                engg.add(users[i][1]);
                enggid.add(users[i][0]);
            }
        }
        // Toast.makeText(this, "en "+engg.get(1), Toast.LENGTH_SHORT).show();

        tid.setText(ttid);
        tck.setText(ttit);
        final String jq = ttid.replace("T", "").trim();
        if ((ucode.equals("U") || (ucode.equals("E")))) {
            //KeyListener asskey = assignspin;
            assignspin.setEnabled(false);
        }
        if (ucode.equals("U")) {
            statusspin.setEnabled(false);
            res.setEnabled(false);

        }

        ArrayAdapter adap = new ArrayAdapter(this, R.layout.spinner_layout, R.id.text, engg);
        assignspin.setAdapter(adap);
        statusspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (statusspin.getSelectedItem().equals("Resolved")) {
                    res.setEnabled(true);
                } else {
                    res.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FOR_DESC,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {//Toast.makeText(MainActivity.this,"try bloack",Toast.LENGTH_LONG).show();
                                //String toast=stringRequest.toString();
//                                       JSONArray jsonArray=new JSONArray(response);
//                                       JSONObject jsonObject=jsonArray.getJSONObject();

                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("getdata");
                                JSONObject obj = jsonArray.getJSONObject(0);

                                String stat = obj.getString("status");
                                created = obj.getString("created");
                                String des = obj.getString("desc");
                                String assi = obj.getString("assigned");
                                aassign = assi;
                                //  Toast.makeText(Description.this, "assi   "+assi, Toast.LENGTH_SHORT).show();
                                String restxt = obj.getString("res");
                                if (stat.equals("1")) {
                                    //statusspin.setText("Open");
                                    statusspin.setSelection(0);
                                } else if (stat.equals("2")) {
                                    statusspin.setSelection(1);
                                } else if (stat.equals("3")) {
                                    statusspin.setSelection(2);

                                } else if (stat.equals("4")) {
                                    statusspin.setSelection(3);
                                } else if (stat.equals("5")) {
                                    statusspin.setSelection(4);
                                }

                                desctxt.setText(des);
                                res.setText(restxt);
                                if (stat.equals("4")) {
                                    res.setEnabled(true);
                                } else {
                                    res.setEnabled(false);

                                }
                                for (int i = 0; i < enggid.size(); i++) {
                                    if (enggid.get(i).equals(assi)) {
                                        String a = enggid.get(i);
                                        assignspin.setSelection(i + 1);

                                        //
                                    }

                                }

                            } catch (JSONException e) {

                                //Toast.makeText(MainActivity.this,"catch block",Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(MainActivity.this,"Error.Connect to the server",Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("tid", jq);
                    return params;
                }
            };
            AppSingleton.getInstance(Description.this).addToRequestQueue(stringRequest);
            if (stringRequest.hasHadResponseDelivered()) {
                //  Toast.makeText(Description.this, "Response delievered successfully ", Toast.LENGTH_SHORT).show();
                //stringRequest.cancel();
            }
        }

        {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String id = jq;
                    final String title = tck.getText().toString();
                    String spin = null;
                    if (statusspin.getSelectedItem().toString().equals("Open")) {
                        spin = "1";
                    } else if (statusspin.getSelectedItem().toString().equals("Inprogress")) {
                        spin = "2";
                    } else if (statusspin.getSelectedItem().toString().equals("Pending")) {
                        spin = "3";
                    } else if (statusspin.getSelectedItem().toString().equals("Resolved")) {
                        spin = "4";
                    } else if (statusspin.getSelectedItem().toString().equals("Closed")) {
                        spin = "5";
                    } else {
                        String d = null;
                    }
                    final String desc = desctxt.getText().toString();
                    final String as = assignspin.getSelectedItem().toString();
                    for (int i = 0; i < len; i++) {
                        final String E = users[i][1];
                        if (E.equals(as)) {

                            assignsend = users[i][0];
                        }
                    }
                    bassign = as;
                    //  Toast.makeText(Description.this, "AAssign   "+aassign, Toast.LENGTH_SHORT).show();

                    //Toast.makeText(Description.this, "AAssign   "+bassign, Toast.LENGTH_SHORT).show();
                    if ((!aassign.equals("0")) && (!aassign.equals(bassign))) {
                        // Toast.makeText(Description.this, "Changed engiineer", Toast.LENGTH_SHORT).show();
                        sendnotificationtoengg(assignsend);
                    }
                    // Toast.makeText(Description.this, "ass  save "+as, Toast.LENGTH_SHORT).show();
                    final String rest = res.getText().toString();
                    // Toast.makeText(MainActivity.this,name,Toast.LENGTH_LONG).show();
                    //Toast.makeT
                    // ext(MainActivity.this,password,Toast.LENGTH_LONG).show();
                    final String finalSpin = spin;
                    StringRequest saveRequest = new StringRequest(Request.Method.POST, URL_FOR_SAVE,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("savedata");
                                        JSONObject loginobj = jsonArray.getJSONObject(0);
                                        String code = loginobj.getString("code");

                                        if (code.equals("success")) {

                                            //   Toast.makeText(Description.this, "Save successful", Toast.LENGTH_SHORT).show();
                                            if (ucode.equals("E") || ucode.equals("M")) {
                                                //   Toast.makeText(Description.this, "Engg notificaion", Toast.LENGTH_SHORT).show();
                                                String s = sendnotification();
                                                // Toast.makeText(Description.this, "This is ", Toast.LENGTH_SHORT).show();
                                                if (s.equals("succes")) {
                                                    builder.setTitle("Save successful");
                                                    saveAlert("Ticket saved successfully");
                                                }
                                            }

                                        }

                                    } catch (JSONException e) {
                                        builder.setTitle("Error");
                                        displayAlert("Exception");

                                        //Toast.makeText(MainActivity.this,"catch block",Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(MainActivity.this,"Error.Conne
                            // ct to the server",Toast.LENGTH_LONG).show();
                            builder.setTitle("Error");
                            displayAlert("Response Error");
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", id);
                            params.put("title", title);
                            params.put("desc", desc);
                            params.put("status", finalSpin);
                            params.put("assign", assignsend);
                            params.put("res", rest);
                            return params;
                        }
                    };
                    AppSingleton.getInstance(Description.this).addToRequestQueue(saveRequest);

                }

                private String sendnotification() {
                    //   Toast.makeText(Description.this, "ccc  "+created, Toast.LENGTH_SHORT).show();

                    final String Title = "Ticket update";
                    final String message = "Your Ticket " + ttid + "  has been updated.Kindly login ";
                    StringRequest notificationRequest = new StringRequest(Request.Method.POST, URL_FOR_NOTIFICATION,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("fcmres");
                                        JSONObject loginobj = jsonArray.getJSONObject(0);
                                        String code = loginobj.getString("code");

                                        if (code.equals("fcmsuccess")) {/*
                                            builder.setTitle("Save Success");
                                            displayAlert("Save was successful");*/

                                        }

                                    } catch (JSONException e) {
                                        builder.setTitle("Error");
                                        displayAlert("Error sending notification to user");

                                        //Toast.makeText(MainActivity.this,"catch block",Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(MainActivity.this,"Error.Conne
                            // ct to the server",Toast.LENGTH_LONG).show();
                            //Toast.makeText(Description.this, "Error response", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", created);
                            params.put("title", Title);
                            params.put("message", message);
                            return params;
                        }
                    };
                    AppSingleton.getInstance(Description.this).addToRequestQueue(notificationRequest);
                    return "succes";

                }

                private String sendnotificationtoengg(final String id) {
                    //   Toast.makeText(Description.this, "ccc  "+created, Toast.LENGTH_SHORT).show();

                    final String Title = "Ticket Assigned";
                    final String message = "You have been assigned to Ticket " + ttid + "..Kindly login ";
                    StringRequest notificationRequest = new StringRequest(Request.Method.POST, URL_FOR_NOTIFICATION,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("fcmres");
                                        JSONObject loginobj = jsonArray.getJSONObject(0);
                                        String code = loginobj.getString("code");

                                        if (code.equals("fcmsuccess")) {/*
                                            builder.setTitle("Save Success");
                                            displayAlert("Save was successful");*/

                                        }

                                    } catch (JSONException e) {
                                        builder.setTitle("Error");
                                        errAlert("Error sending notification to Engineer");

                                        //Toast.makeText(MainActivity.this,"catch block",Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(MainActivity.this,"Error.Conne
                            // ct to the server",Toast.LENGTH_LONG).show();
                            //  Toast.makeText(Description.this, "Error response", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", id);
                            params.put("title", Title);
                            params.put("message", message);
                            return params;
                        }
                    };
                    AppSingleton.getInstance(Description.this).addToRequestQueue(notificationRequest);
                    return "succes";

                }

            });

        }

    }


    public void displayAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        ;
    }


    public void errAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        ;
    }

    public void saveAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        ;
    }

}

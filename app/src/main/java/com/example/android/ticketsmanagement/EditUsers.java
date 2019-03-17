package com.example.android.ticketsmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class EditUsers extends AppCompatActivity {
    Spinner location, user;
    EditText name, contact;
    String uname, uloc, ucontact, uuser;
    String senduser, sendloc;
    Constants c;
    String URL_TO_SAVE_USER = Constants.URL_TO_SAVE_USER;
    String URL_TO_DELETE = Constants.URL_TO_DELETE;
    AlertDialog.Builder builder;
    Button save, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);
        location = (Spinner) findViewById(R.id.spin);
        user = (Spinner) findViewById(R.id.spinuser);
        name = (EditText) findViewById(R.id.Name);
        delete = (Button) findViewById(R.id.delete);

        contact = (EditText) findViewById(R.id.contact);
        uname = name.getText().toString();
        ucontact = contact.getText().toString();
        uloc = location.getSelectedItem().toString();

        Bundle b = getIntent().getExtras();
        final String id = b.getString("uid");

        final String jq = id.replace("ID-", "").trim();
//        location.setSelection(3);
        user.setSelection(0);

        {
            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URL_TO_SAVE_USER,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("getdetails");
                                JSONObject obj = jsonArray.getJSONObject(0);

                                //String stat = obj.getString("status");
                                //Toast.makeText(EditUsers.this,stat+"is the status",Toast.LENGTH_LONG).show();
                                String jname = obj.getString("name");
                                String jcont = obj.getString("contact");
                                String jcode = obj.getString("ucode");
                                String jloc = obj.getString("loc");
                                String setloc;

                                name.setText(jname);
                                contact.setText(jcont);
                                if (jloc.equals("Capital Tower")) {
                                    location.setSelection(1);
                                } else if (jloc.equals("Asia Square Tower")) {
                                    location.setSelection(2);
                                } else if (jloc.equals("Changi Business Park")) {
                                    location.setSelection(3);
                                } else {
                                    location.setSelection(0);
                                }
//
                                if (jcode.equals("U")) {
                                    user.setSelection(1);
                                } else if (jcode.equals("E")) {
                                    user.setSelection(2);
                                } else if (jcode.equals("M")) {
                                    user.setSelection(3);
                                } else {
                                    user.setSelection(0);
                                }

                            } catch (JSONException e) {
                                Toast.makeText(EditUsers.this, "Response Error", Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EditUsers.this, "Server   Error ", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("uid", jq);
////                params.put("Name",uname);
////                params.put("contact",ucontact);
////                params.put("loc",sendloc);
//                params.put("user",senduser);

                    return params;
                }
            };
            AppSingleton.getInstance(EditUsers.this).addToRequestQueue(stringRequest2);
/*            if(stringRequest2.hasHadResponseDelivered()){
                Toast.makeText(EditUsers.this, "Response delievered successfully ", Toast.LENGTH_SHORT).show();
                //stringRequest.cancel();
            }*/
        }
        {
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String id = jq;
                    StringRequest saveRequest = new StringRequest(Request.Method.POST, URL_TO_DELETE,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("delete");
                                        JSONObject loginobj = jsonArray.getJSONObject(0);
                                        String code = loginobj.getString("code");
                                        if (code.equals("deleted")) {
                                            Toast.makeText(EditUsers.this, "Deleteion success", Toast.LENGTH_SHORT).show();
//                                            Intent go=new Intent(EditUsers.this,Admin.class);
//                                            startActivity(go);
                                        }

                                    } catch (JSONException e) {

                                        //  Toast.makeText(EditUsers.this, "Deleteion success", Toast.LENGTH_SHORT).show();
                                        Intent go = new Intent(EditUsers.this, Admin.class);
                                        startActivity(go);

                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(MainActivity.this,"Error.Conne
                            // ct to the server",Toast.LENGTH_LONG).show();
                            builder.setTitle("Error");
                            displayAlert("Error in connection");
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", id);
                            return params;
                        }
                    };
                    AppSingleton.getInstance(EditUsers.this).addToRequestQueue(saveRequest);

                }
            });

        }

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
    }

}

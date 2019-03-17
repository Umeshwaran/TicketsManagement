package com.example.android.ticketsmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateEngineer extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView head;
    EditText name, contact, pwd;
    Spinner location;
    Button create, reset;
    String sname, scontact, spwd;
    private String sloc;
    Constants c;
    String URL_TO_CREATE_ENGG = Constants.URL_TO_CREATE_ENGG;
    AlertDialog.Builder builder;
    String[] locationofbranches = new String[]{
            "Select a branch",
            "Capital Towerr",
            "Asia Square Tower",
            "Changee Business Park",

            "LENOVO"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_engineer);
        head = (TextView) findViewById(R.id.head);
        name = (EditText) findViewById(R.id.ename);
        contact = (EditText) findViewById(R.id.econtact);
        location = (Spinner) findViewById(R.id.elocation);
        pwd = (EditText) findViewById(R.id.epassword);
        create = (Button) findViewById(R.id.btncreate);
        reset = (Button) findViewById(R.id.btnreset);
        create.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          sname = name.getText().toString();
                                          scontact = contact.getText().toString();
                                          spwd = pwd.getText().toString();
                                          sloc = location.getSelectedItem().toString();


                                          if (sname.equals("") || scontact.equals("") || sloc.equals("Select a branch") || spwd.equals("")) {

                                              builder.setTitle("Valid Details Required");
                                              displayAlert("Enter all the details");
                                          } else {
                                              StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TO_CREATE_ENGG,
                                                      new Response.Listener<String>() {


                                                          @Override
                                                          public void onResponse(String response) {
                                                              try {

                                                                  JSONObject jsonObject = new JSONObject(response);
                                                                  JSONArray jsonArray = jsonObject.getJSONArray("success");
                                                                  JSONObject loginobj = jsonArray.getJSONObject(0);
                                                                  String code = loginobj.getString("code");
                                                                  if (code.equals("success")) {
                                                                      builder.setTitle("Created");
                                                                      displaySuccess("Engineer Created Successfully");
                                                                      displayAlert("Engineer Created Successfully");
                                                                  }
                                                              } catch (JSONException e) {
                                                                  e.printStackTrace();
                                                              }
                                                          }
                                                      }, new Response.ErrorListener() {
                                                  @Override
                                                  public void onErrorResponse(VolleyError error) {
                                                      error.printStackTrace();
                                                  }
                                              }) {
                                                  @Override
                                                  protected Map<String, String> getParams() throws AuthFailureError {
                                                      Map<String, String> params = new HashMap<>();
                                                      params.put("name", sname);
                                                      params.put("contact", scontact);
                                                      params.put("location", sloc);
                                                      params.put("pass", spwd);
                                                      return params;
                                                  }
                                              };
                                              AppSingleton.getInstance(CreateEngineer.this).addToRequestQueue(stringRequest);
                                          }

                                      }
                                  }
        );

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                contact.setText("");
                location.setSelection(0);
                pwd.setText("");
            }
        });
    }

    public void displaySuccess(String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //   startActivity(new Intent(CreateEngineer.this,MainActivity.class));

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sloc = location.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

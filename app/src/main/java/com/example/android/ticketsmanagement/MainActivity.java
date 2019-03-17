package com.example.android.ticketsmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    EditText Name, Password;
    Button bsubmit;
    String name, password;
    TextView newuser;
    Constants c;
    String URL_FOR_LOGIN = Constants.URL_FOR_LOGIN;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(MainActivity.this);
        bsubmit = (Button) findViewById(R.id.button);
        newuser = (TextView) findViewById(R.id.newuserclick);
        Name = (EditText) findViewById(R.id.Name);
        Password = (EditText) findViewById(R.id.Password);

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(MainActivity.this, "Clickkkk happened", Toast.LENGTH_SHORT).show();
                Intent newuser = new Intent(MainActivity.this, Create_User.class);
                newuser.putExtra("flag", "0");
                startActivity(newuser);

            }
        });

        bsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = Name.getText().toString();
                password = Password.getText().toString();
                // Toast.makeText(MainActivity.this,name,Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this,password,Toast.LENGTH_LONG).show();
                if (name.equals("") || password.equals("")) {
                    builder.setTitle("Credentials Required");
                    displayAlert("Enter Valid UserName and Password");

                } else {//Toast.makeText(MainActivity.this,"Into the elseeeeefff",Toast.LENGTH_LONG).show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FOR_LOGIN,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {//Toast.makeText(MainActivity.this,"try bloack",Toast.LENGTH_LONG).show();
                                        //String toast=stringRequest.toString();
//                                       JSONArray jsonArray=new JSONArray(response);
//                                       JSONObject jsonObject=jsonArray.getJSONObject();

                                  /*     JSONObject jsonObject=new JSONObject(response);
                                       JSONArray jsonArray=jsonObject.getJSONArray("login");
                                       JSONObject loginobj=jsonArray.getJSONObject(0);
                                       int len=jsonArray.length();
                                       Log.d("came ","gone");
                                       Toast.makeText(MainActivity.this, "len of json array is"+len, Toast.LENGTH_SHORT).show();*/

                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONObject login = jsonObject.getJSONObject("login");

                                        JSONArray user = login.getJSONArray("users");
                                        int len = user.length();
                                        String users[][] = new String[len][3];
                                        for (int i = 0; i < len; i++) {

                                            users[i][0] = user.getJSONObject(i).getString("ID");
                                            users[i][1] = user.getJSONObject(i).getString("Name");
                                            users[i][2] = user.getJSONObject(i).getString("Code");

                                        }

                                        String check = user.getString(0);
                                        JSONObject data = login.getJSONObject("0");
                                        JSONObject loginobj = login.getJSONObject("0");


                                      /* int len=jsonArray.length();
                                       JSONObject loginobj=jsonArray.getJSONObject(0);
                                      for(int i=0;i<len;i++)
                                      {JSONObject logi=jsonArray.getJSONObject(i);
                                          Toast.makeText(MainActivity.this, "created"+i, Toast.LENGTH_SHORT).show();
                                      }*/

                                        String code = loginobj.getString("code");
                                        String usrid = loginobj.getString("id");
                                        String user_code = loginobj.getString("user");
                                        String approveflag = loginobj.getString("flag");
                                        //  Toast.makeText(MainActivity.this, approveflag, Toast.LENGTH_SHORT).show();

                                        // String message=loginobj.getString("message");

                                        // Toast.makeText(MainActivity.this,code,Toast.LENGTH_LONG).show();
                                        if (code.equals("login_failed")) {
                                            builder.setTitle("Login Error");
//                                          displayAlert(jsonObject.getString("message"));
//                                           Toast.makeText(MainActivity.this,code,Toast.LENGTH_LONG).show();
                                            displayAlert("lOGIN FAILED");
                                        } else {//Toast.makeText(MainActivity.this,"else 2 block.need to start second",Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                            Bundle bun = new Bundle();
                                            bun.putString("userid", usrid);
                                            bun.putString("usercode", user_code);
                                            bun.putInt("len", len);
                                            bun.putSerializable("users", users);
                                            if (user_code.equals("A")) {
                                                Intent ad = new Intent(MainActivity.this, Admin.class);
                                                ad.putExtras(bun);
                                                startActivity(ad);
                                            } else if (approveflag.contentEquals("1")) {
                                                intent.putExtras(bun);
                                                startActivity(intent);
                                            } else {
                                                builder.setTitle("Access Denied");
                                                displayAlert("Your Approval is pending with Admin.Try again later or contact Admin");
                                                //        Toast.makeText(MainActivity.this, "Get Approval first", Toast.LENGTH_SHORT).show();
                                            }
                                        }//Toast.makeText(MainActivity.this,code,Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        builder.setTitle("Invalid Password");
                                        displayAlert("Please Try again");

                                        //Toast.makeText(MainActivity.this,"catch block",Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            builder.setTitle("Error");
                            displayAlert("Check credentials or Connect to server");
                            //Toast.makeText(MainActivity.this,"Error.Connect to the server",Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", name);
                            params.put("pass", password);
                            return params;
                        }
                    };
                    AppSingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

                }
            }
        });
    }

    public void displayAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Name.setText("");
                Password.setText("");

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void reLoad() {
    }
}




package com.example.android.ticketsmanagement;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.android.ticketsmanagement.Otherjava.Constants;
import com.example.android.ticketsmanagement.Otherjava.CustomListAdapter;
import com.example.android.ticketsmanagement.Otherjava.FcmInstanceIdService;
import com.example.android.ticketsmanagement.Otherjava.SpinnerNav;
import com.example.android.ticketsmanagement.Otherjava.Ticket;
import com.example.android.ticketsmanagement.Otherjava.TitleNavAdapter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    Constants c;
    private static final String URL = Constants.URL;
    private static final String URL_FCM = Constants.URL_FOR_FCM_SAVE;
    private ProgressDialog pDialog;
    private List<Ticket> ticketList = new ArrayList<Ticket>();
    String login;


    private ListView listView;
    private CustomListAdapter adapter, newadapter, spinadap, empadap;
    private FloatingActionButton fab, fabm;
    public String global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle bund = getIntent().getExtras();
        final String id = bund.getString("userid");
        login = id;
        // Toast.makeText(this, "userid id   "+id, Toast.LENGTH_SHORT).show();
        final String ucode = bund.getString("usercode");
        final String users[][] = (String[][]) bund.getSerializable("users");
        final int len = bund.getInt("len");

        // Hide the action bar title
        // Enabling Spinner dropdown navigation

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");
        final String fcm = null;
        // Toast.makeText(this, "token is "+token, Toast.LENGTH_SHORT).show();
        if (token == "") {
            //   Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            FcmInstanceIdService ff = new FcmInstanceIdService();
            token = ff.srrr();

        }

        final String finalToken = token;
        StringRequest saveRequest = new StringRequest(Request.Method.POST, URL_FCM,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("savefcm");
                            JSONObject loginobj = jsonArray.getJSONObject(0);
                            String code = loginobj.getString("code");
                            //  Toast.makeText(SecondActivity.this, "code got is "+code, Toast.LENGTH_SHORT).show();
                            if (code.equals("success")) {
                                //  Toast.makeText(SecondActivity.this, "successss", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(SecondActivity.this, "Error in updating FCM token in DB.You will not get notifications", Toast.LENGTH_SHORT).show();

                            //Toast.makeText(MainActivity.this,"catch block",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this,"Error.Conne
                // ct to the server",Toast.LENGTH_LONG).show();
                Toast.makeText(SecondActivity.this, "Connection Issue", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);

                params.put("fcm", finalToken);
            /*        params.put("desc", desc);
                    params.put("status", finalSpin);
                    params.put("assign", as);
                    params.put("res",rest);*/
                return params;
            }
        };
        AppSingleton.getInstance(SecondActivity.this).addToRequestQueue(saveRequest);

        //  Toast.makeText(this, "kk"+users[1][1], Toast.LENGTH_SHORT).show();
        // String status = bund.getString("status");

        // Toast.makeText(this, status + " is the status", Toast.LENGTH_SHORT).show();
        //  Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        //final String URL = "http://192.168.137.1/Ticket/sample.php";
        fabm = (FloatingActionButton) findViewById(R.id.createuser);
        fabm.setVisibility(View.INVISIBLE);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, ticketList);
        listView.setAdapter(adapter);

        if (ucode.equals("M")) {
            fab = (FloatingActionButton) findViewById(R.id.createtick);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(SecondActivity.this, ThirdActivity.class);
                    Bundle third = new Bundle();
                    third.putString("id", id);
                    third.putSerializable("users", users);
                    third.putString("ucode", ucode);
                    third.putInt("len", len);
                    in.putExtras(third);
                    startActivity(in);
                }
            });
            fabm.setVisibility(View.VISIBLE);
            fabm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inm = new Intent(SecondActivity.this, CreateEngineer.class);
                    startActivity(inm);
                }
            });

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading");
            pDialog.show();
            final JSONObject requestJsonObject = new JSONObject();
            try {
                requestJsonObject.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final JsonArrayRequest tickreq = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    hidePDialog();
                    for (int i = 0; i < response.length(); i++) {

                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Ticket ticket = new Ticket();
                            ticket.setTicketid(obj.getString("TID"));
                            ticket.setDesc(obj.getString("Title"));
                            final String check = obj.getString("created_by");
                            // Toast.makeText(SecondActivity.this, "check  "+check, Toast.LENGTH_SHORT).show();
                            String status = obj.getString("status");
                            // global=obj.getString("status");

                            //  global=obj.getString("desc");
                            ticket.setstatus(status);

                            //  if (status.equals("1")) {
                            //Toast.makeText(SecondActivity.this,statfordesc, Toast.LENGTH_SHORT).show();

                            ticketList.add(ticket);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView liid = (TextView) view.findViewById(R.id.id);
                                    String tidfordesc = liid.getText().toString();
                                    TextView desct = (TextView) view.findViewById(R.id.description);
                                    String bdesc = desct.getText().toString();

                                    Bundle bundlefordesc = new Bundle();
                                    bundlefordesc.putString("tidfordesc", tidfordesc);
                                    bundlefordesc.putString("desc", bdesc);
                                    bundlefordesc.putString("ucode", ucode);
                                    bundlefordesc.putString("login", login);

                                    bundlefordesc.putSerializable("users", users);
                                    bundlefordesc.putInt("len", len);

                                    Intent desc = new Intent(SecondActivity.this, Description.class);
                                    desc.putExtras(bundlefordesc);
                                    startActivity(desc);

                                }
                            });
                            //     }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hidePDialog();
                }
            });

            AppSingleton.getInstance(SecondActivity.this).addToRequestQueue(tickreq);

        } else {

            fab = (FloatingActionButton) findViewById(R.id.createtick);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(SecondActivity.this, ThirdActivity.class);
                    Bundle third = new Bundle();
                    third.putString("id", id);
                    third.putString("ucode", ucode);
                    third.putInt("len", len);
                    third.putSerializable("users", users);
                    in.putExtras(third);
                    startActivity(in);
                }
            });

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading");
            pDialog.show();
            final JSONObject requestJsonObject = new JSONObject();
            try {
                requestJsonObject.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final JsonArrayRequest tickreq1 = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    hidePDialog();
                    for (int i = 0; i < response.length(); i++) {

                        try {
                            final JSONObject obj = response.getJSONObject(i);
                            final Ticket ticket = new Ticket();
                            ticket.setTicketid(obj.getString("TID"));
                            ticket.setDesc(obj.getString("Title"));
                            final String check = obj.getString("created_by");
                            String status = obj.getString("status");
                            String assign = obj.getString("assigned_to");

                            ticket.setstatus(status);

                            if (check.equals(id) || assign.equals(id)) {
                                ticketList.add(ticket);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        TextView liid = (TextView) view.findViewById(R.id.id);
                                        String tidfordesc = liid.getText().toString();
                                        TextView desct = (TextView) view.findViewById(R.id.description);
                                        String bdesc = desct.getText().toString();

                                        Bundle bundlefordesc = new Bundle();
                                        bundlefordesc.putString("tidfordesc", tidfordesc);
                                        bundlefordesc.putString("desc", bdesc);
                                        bundlefordesc.putString("ucode", ucode);
                                        bundlefordesc.putString("created_by", check);
                                        bundlefordesc.putSerializable("users", users);
                                        bundlefordesc.putInt("len", len);

                                        Intent desc = new Intent(SecondActivity.this, Description.class);
                                        desc.putExtras(bundlefordesc);
                                        startActivity(desc);

                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hidePDialog();
                }
            });

            AppSingleton.getInstance(SecondActivity.this).addToRequestQueue(tickreq1);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);

        MenuItem searchitem = menu.findItem(R.id.action_search);
        MenuItem spinner = menu.findItem(R.id.spinnerfilter);
        final Spinner spin = (Spinner) spinner.getActionView();

        String[] items = {"None", "Open", "Inprogress", "Pending", "Resolved", "Closed"};
        List<String> s = new ArrayList<>();
        s.add("Open");
        s.add("Closed");
        final ArrayAdapter<String> spinadapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.text, items);
        //spinadapter.setDropDownViewResource(R.layout.spinner_layout);
        spin.setAdapter(spinadapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @
                    Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ss = spin.getSelectedItem().toString();
                String ststs = null;

                if (ss.equals("None")) {

                } else if (ss.equals("Open")) {
                    ststs = "1";
                } else if (ss.equals("Inprogress")) {
                    ststs = "2";
                } else if (ss.equals("Pending")) {
                    ststs = "3";
                } else if (ss.equals("Resolved")) {
                    ststs = "4";
                } else if (ss.equals("Closed")) {
                    ststs = "5";
                }
                if (!ss.equals("None") && !ststs.equals(null)) {

                    ArrayList<Ticket> spinfilterlist = new ArrayList<Ticket>();
                    for (Ticket temp : ticketList) {
                        if (temp.getstatus().contains(ststs)) {
                            spinfilterlist.add(temp);
                        }
                        spinfilterlist.size();
                        ststs.contains(ststs);

                    }
                    if (spinfilterlist.size() == 0) {
                        Ticket empty = new Ticket();
                        empty.setTicketid("icket      Error");
                        empty.setstatus("10");
                        empty.setDesc("No ticket Available in this status");
                        spinfilterlist.add(empty);
                        empadap = new CustomListAdapter(SecondActivity.this, spinfilterlist);
                        listView.setAdapter(empadap);

                    } else {
                        spinadap = new CustomListAdapter(SecondActivity.this, spinfilterlist);
                        listView.setAdapter(spinadap);
                    }
                } else {
                    listView.setAdapter(adapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MenuItem settings = menu.findItem(R.id.logout);

        settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                System.exit(0);

                return true;
            }
        });
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Ticket> searchlist = new ArrayList<Ticket>();
                for (Ticket temp : ticketList) {
                    if (temp.getTicketid().toLowerCase().contains(newText)) {
                        searchlist.add(temp);
                    }
                    newadapter = new CustomListAdapter(SecondActivity.this, searchlist);
                    listView.setAdapter(newadapter);

                }
                return false;
            }
        });
       /* SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
*/
        return super.onCreateOptionsMenu(menu);
    }

    private void setupSpinner(Spinner spin) {
        String[] items = {"Open", "Inprogress", "Pending", "Resolved", "Closed"};
        //wrap the items in the Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, items);
        //assign adapter to the Spinner
        spin.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }

    }
}



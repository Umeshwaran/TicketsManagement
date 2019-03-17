package com.example.android.ticketsmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.android.ticketsmanagement.Otherjava.Adclass;
import com.example.android.ticketsmanagement.Otherjava.AdminAdapter;
import com.example.android.ticketsmanagement.Otherjava.Constants;
import com.example.android.ticketsmanagement.Otherjava.CustomListAdapter;
import com.example.android.ticketsmanagement.Otherjava.Adclass;
import com.example.android.ticketsmanagement.Otherjava.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    //private static final String URL = "http://192.168.137.1/Ticket/getusers.php";
    private static final String URL = Constants.Admin_URL;

    private ProgressDialog pDialog;
    private List<Adclass> userList = new ArrayList<Adclass>();

    private List<Adclass> empList = new ArrayList<Adclass>();
    private ListView listView;
    private AdminAdapter adminAdapter, spinadap, empadap, searchadap;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("");
        fab = (FloatingActionButton) findViewById(R.id.managercreation);
        Bundle bund = getIntent().getExtras();
        final String id = bund.getString("userid");

        final String ucode = bund.getString("usercode");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Admin.this, Create_User.class);
                in.putExtra("flag", "1");
                startActivity(in);
            }
        });

        listView = (ListView) findViewById(R.id.list_admin);
        adminAdapter = new AdminAdapter(this, userList);
        listView.setAdapter(adminAdapter);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading");
        pDialog.show();
        final JSONObject requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Toast.makeText(Admin.this, "Before Json Request", Toast.LENGTH_SHORT).show();
        final JsonArrayRequest tickreq = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hidePDialog();
                for (int i = 0; i < response.length(); i++) {

                    // Toast.makeText(Admin.this, "Inside Response", Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Adclass adclass = new Adclass();
                        adclass.setUid(obj.getInt("ID"));
                        adclass.setName(obj.getString("Name"));
                        final String ucode = obj.getString("Code");
                        String flag = obj.getString("Approved");
                        // Toast.makeText(Admin.this,ucode, Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(Admin.this,ucode+" is the ucode",Toast.LENGTH_LONG).show();
                        adclass.setUcode(ucode);
                        adclass.setflag(flag);
                        if (!ucode.equals("A")) {
                            userList.add(adclass);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView uid = (TextView) view.findViewById(R.id.id);
                                    String lvid = uid.getText().toString();
                                    TextView uname = (TextView) view.findViewById(R.id.Name);
                                    String lvname = uname.getText().toString();

                                    Bundle bundlefordesc = new Bundle();
                                    bundlefordesc.putString("uid", lvid);
                                    bundlefordesc.putString("Name", lvname);
                                    bundlefordesc.putString("ucode", ucode);

                                    Intent desc = new Intent(Admin.this, EditUsers.class);
                                    desc.putExtras(bundlefordesc);
                                    startActivity(desc);

                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adminAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();
            }
        });

        AppSingleton.getInstance(Admin.this).addToRequestQueue(tickreq);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);
        MenuItem searchitem = menu.findItem(R.id.action_search);

        MenuItem spinner = menu.findItem(R.id.spinnerfilter);
        final Spinner spin = (Spinner) spinner.getActionView();

        String[] items = {"None", "User", "Enginner", "Manager"};
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

                } else if (ss.equals("User")) {
                    ststs = "U";
                } else if (ss.equals("Enginner")) {
                    ststs = "E";
                } else if (ss.equals("Manager")) {
                    ststs = "M";
                }
                if (!ss.equals("None") && !ststs.equals(null)) {

                    // ArrayList<Ticket> spinfilterlist = new ArrayList<Ticket>();
                    List<Adclass> spinList = new ArrayList<Adclass>();
                    for (Adclass temp : userList) {
                        if (temp.getUcode().equals(ststs)) {
                            spinList.add(temp);
                        }
                        spinList.size();
                        ststs.contains(ststs);

                    }
                    if (spinList.size() == 0) {
                        Adclass empty = new Adclass();
                        empty.setUcode("Null");
                        empty.setUid(100);
                        empty.setName("No users found");
                        empList.add(empty);
                        empadap = new AdminAdapter(Admin.this, empList);
                        listView.setAdapter(empadap);

                    } else {
                        spinadap = new AdminAdapter(Admin.this, spinList);
                        listView.setAdapter(spinadap);
                    }
                } else {
                    listView.setAdapter(adminAdapter);

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
        // searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Adclass> searchlist = new ArrayList<Adclass>();
                for (Adclass temp : userList) {
                    if (temp.getName().toLowerCase().contains(newText)) {
                        searchlist.add(temp);
                    }
                    searchadap = new AdminAdapter(Admin.this, searchlist);
                    listView.setAdapter(searchadap);

                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

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

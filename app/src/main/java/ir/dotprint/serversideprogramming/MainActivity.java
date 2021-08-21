package ir.dotprint.serversideprogramming;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ir.dotprint.serversideprogramming.Adapter.MassageAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    ArrayList<MassageItem> massageItems = new ArrayList<>();
    MassageAdapter massageAdapter;
    FloatingActionButton fabmassage;
    SwipeRefreshLayout swip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        swip=(SwipeRefreshLayout)findViewById(R.id.swip);
        fabmassage = (FloatingActionButton) findViewById(R.id.fabmassage);
        massageAdapter = new MassageAdapter(massageItems, MainActivity.this);
        recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recycler.setAdapter(massageAdapter);
        Bundle bundle = getIntent().getExtras();


        final String username = bundle.getString("username");
//        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
        seddata();


        fabmassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogmassage, null);
                final EditText edtmassage = (EditText) view1.findViewById(R.id.edtmassage);


                builder.setView(view1);
                builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getApplicationContext(), String.valueOf(edtmassage.getText()), Toast.LENGTH_SHORT).show();
                        if (TextUtils.isEmpty(String.valueOf(edtmassage.getText()))) {
                            Toast.makeText(getApplicationContext(), "Enter Message", Toast.LENGTH_SHORT).show();

                        } else {

                            insertmassage(String.valueOf(edtmassage.getText()), username);
                            massageItems.clear();
                            seddata();
                        }


                    }
                });
//
//                builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                builder.setCancelable(false);
                builder.create().show();


            }
        });

        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                massageItems.clear();
                seddata();
                swip.setRefreshing(false);
            }
        });
    }


    public void seddata() {

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("please wait");
        progressDialog.show();
        String url = "http://dotprint.ir/resume/serverside/selectmassage.php";


        Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = response.length(); i >=0 ; i--) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String username = jsonObject.getString("username");
                        String massage = jsonObject.getString("massage");
                        massageItems.add(new MassageItem(username, massage));
                        massageAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();

                }


            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        };


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, jsonArrayListener, errorListener);
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);


//
//
//        massageItems.add(new MassageItem("Arash" , "MassageMassageMassageMassageMassageMassageMassageMassage"));
//        massageItems.add(new MassageItem("Arash" , "MassageMassageMassageMassageMassageMassageMassageMassage"));
//        massageItems.add(new MassageItem("Arash" , "MassageMassageMassageMassageMassageMassageMassageMassage"));
//        massageItems.add(new MassageItem("Arash" , "MassageMassageMassageMassageMassageMassageMassageMassage"));
//        massageItems.add(new MassageItem("Arash" , "MassageMassageMassageMassageMassageMassageMassageMassage"));
//        massageItems.add(new MassageItem("Arash" , "MassageMassageMassageMassageMassageMassageMassageMassage"));
//        massageItems.add(new MassageItem("Arash" , "MassageMassageMassageMassageMassageMassageMassageMassage"));
//        massageAdapter.notifyDataSetChanged();


    }


    public void insertmassage(final String massage, final String user) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("please wait");
        progressDialog.show();
        String url = "http://dotprint.ir/resume/serverside/insertmassage.php";

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, stringListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("username", user);
                hashMap.put("massage", massage);

                return hashMap;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);


    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are You Sure  ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No" , null);
        builder.setCancelable(false);
        builder.create().show();

    }
}

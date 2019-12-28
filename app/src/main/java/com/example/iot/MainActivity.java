package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public AsyncHttpClient asyncHttpClient;
    public ArrayList<Container> Containers;
    public String BASE_URL = "https://2bw3s8zet7.execute-api.eu-west-1.amazonaws.com/dev/";
    ListView listView;
    Container container;

    ArrayAdapter<Container> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button clickMe = findViewById(R.id.btnClickMe);
        listView = findViewById(R.id.listView);
        Containers = new ArrayList<Container>();
        getList();
        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                final String container_id = Containers.get(position).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(container_id);
                builder.setMessage(container_id + " id'li konteynerı topladınız mı?");
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Containers.remove(position);
                        setList();
                        CollectGarbage(container_id);
                    }
                });
                builder.show();
            }
        });
    }

    public void getList() {
        Containers.clear();
        try {
            asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.get(BASE_URL + "?region=izmit", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    JSONArray jsonArr = null;
                    try {
                        jsonArr = new JSONArray(response);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            System.out.println(jsonObj);
                            container = new Container(jsonObj.getString("id"), jsonObj.getDouble("lat"), jsonObj.getDouble("lon"), jsonObj.getInt("order"), jsonObj.getString("address"), jsonObj.getBoolean("isFilled"));
                            Containers.add(container);
                        }
                        for (Container con :
                                Containers) {
                            System.out.println(con.getId());
                        }
                        setList();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setList() {
        try {
            listView.setAdapter(new CustomAdapter(this, Containers));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("setList()");
        System.out.println("Container size: " + Containers.size());
    }

    public void CollectGarbage(String id) {
        try {
            asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.get(BASE_URL + "?filled=" + id, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    JSONArray jsonArr = null;
                    try {
                        jsonArr = new JSONArray(response);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            System.out.println(jsonObj);
                            if (jsonObj.getBoolean("success") == true) {
                                Toast.makeText(getApplicationContext(), "Çöp Toplandı.", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "Çöp Toplanamadı!", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


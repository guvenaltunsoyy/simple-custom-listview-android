package com.example.iot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Container> Containers;
    boolean isEmpty;
    String BASE_URL = "https://2bw3s8zet7.execute-api.eu-west-1.amazonaws.com/dev/?filled=5e07475fe09e688c3473fdca";
    private static LayoutInflater inflater = null;

    public CustomAdapter(Context context, ArrayList<Container> Containers, boolean isEmpty) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.isEmpty = isEmpty;
        this.Containers = Containers;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Containers.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return Containers.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        TextView address, lat, lon, sum;
        Button btnSum;
        address = (TextView) vi.findViewById(R.id.address);
        lat = (TextView) vi.findViewById(R.id.lat);
        lon = (TextView) vi.findViewById(R.id.lon);
        address.setText(Containers.get(position).getAddress());
        sum = vi.findViewById(R.id.sum);
        sum.setText(isEmpty ? "Dolu" : "Toplandı");
        lat.setText(String.valueOf(Containers.get(position).getLat()));
        lon.setText(String.valueOf(Containers.get(position).getLon()));
        return vi;
    }
}
package com.example.movileappsproyect.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movileappsproyect.R;
import com.example.movileappsproyect.model.SpaceStationModel;

import java.util.LinkedList;
import java.util.List;

public class SpaceStationListAdapter extends BaseAdapter {

    private List<SpaceStationModel> datos = new LinkedList<>();
    private final Context ctx;

    public SpaceStationListAdapter(Context ctx, List<SpaceStationModel> data) {
        this.ctx = ctx;
        this.datos = data;
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int i) {
        return datos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return datos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.space_station_layout, null);
        }
        ((TextView)view.findViewById(R.id.card_name)).setText(datos.get(i).getNombre());
        ((TextView)view.findViewById(R.id.card_status)).setText(datos.get(i).getDeorbited());
        ((TextView)view.findViewById(R.id.cardFounded)).setText(datos.get(i).getFounded());
        ImageView tv = view.findViewById(R.id.card_img);
        tv.setImageBitmap(datos.get(i).getbImage());
        return view;
    }
}

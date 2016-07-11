package net.northwestvision.vsoWeatherProject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.northwestvision.vsoWeatherProject.R;
import net.northwestvision.vsoWeatherProject.datastructure.Alerts;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Acer on 02.07.2016.
 */
public class AlertAdapter extends ArrayAdapter<Alerts> {

    public AlertAdapter(Context context, Alerts[] objects) {
        super(context, R.layout.item_alert_list, R.id.time, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // View holder design pattern is not used here, instead the convert view form the android system (super class) it self
        // is used, so it initializes everything and its not necessary to implement inflate logic manually
        View rootView = super.getView(position, convertView, parent);

        //setting up related data from the data structure to corresponding views
        ((TextView) rootView.findViewById(R.id.time)).setText("Сигнал за : "+ DateFormat.getDateTimeInstance().format(new Date(Long.parseLong(getItem(position).time) * 1000)));
        ((TextView) rootView.findViewById(R.id.expires)).setText("Сигналът е валиден до : " + DateFormat.getDateTimeInstance().format(new Date(Long.parseLong(getItem(position).expires) * 1000)));
        ((TextView) rootView.findViewById(R.id.title)).setText(getItem(position).title);
        ((TextView) rootView.findViewById(R.id.description)).setText(getItem(position).description);
        ((TextView) rootView.findViewById(R.id.uri)).setText(getItem(position).uri);

        return rootView;
    }
}

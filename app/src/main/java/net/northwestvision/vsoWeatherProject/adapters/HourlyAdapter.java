package net.northwestvision.vsoWeatherProject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.northwestvision.vsoWeatherProject.R;
import net.northwestvision.vsoWeatherProject.datastructure.HourlyData;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Acer on 01.07.2016.
 */
public class HourlyAdapter extends ArrayAdapter<HourlyData> {

    public HourlyAdapter(Context context, HourlyData[] objects) {
        super(context, R.layout.item_hourly_list, R.id.time, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // View holder design pattern is not used here, instead the convert view form the android system (super class) it self
        // is used, so it initializes everything and its not necessary to implement inflate logic manually
        View rootView = super.getView(position, convertView, parent);

        //setting up related data from the data structure to corresponding views
        ((TextView) rootView.findViewById(R.id.time)).setText(DateFormat.getTimeInstance().format(new Date(getItem(position).time * 1000)));
        ((TextView) rootView.findViewById(R.id.summary)).setText(getItem(position).summary);
        ((TextView) rootView.findViewById(R.id.precipProbability)).setText("Вероятност за превалявания : " + String.valueOf(getItem(position).precipProbability)+" \u0025");
        ((TextView) rootView.findViewById(R.id.temperature)).setText(String.valueOf(getItem(position).temperature)+"\u2103");
        ((TextView) rootView.findViewById(R.id.apparentTemperature)).setText("Температура в момента : " + String.valueOf(getItem(position).apparentTemperature)+" \u2103");
        ((TextView) rootView.findViewById(R.id.humidity)).setText("Влажност : " + String.valueOf(getItem(position).humidity)+" \u0025");
        ((TextView) rootView.findViewById(R.id.windSpeed)).setText("Скорост на вятъра : " + String.valueOf(getItem(position).windSpeed)+" m/s");
        ((TextView) rootView.findViewById(R.id.windBearing)).setText("Посокоа на вятъра : " + String.valueOf(getItem(position).windBearing));
        ((TextView) rootView.findViewById(R.id.cloudCover)).setText("Облъчна покривка : " + String.valueOf(getItem(position).cloudCover));

        if(getItem(position).icon.equalsIgnoreCase("clear-day")){
            ((ImageView) rootView.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.sunny);
        }else if(getItem(position).icon.equalsIgnoreCase("clear-night")){
            ((ImageView) rootView.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.moon);
        }else if(getItem(position).icon.equalsIgnoreCase("rain")){
            ((ImageView) rootView.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.drizzle);
        }else if(getItem(position).icon.equalsIgnoreCase("snow")){
            ((ImageView) rootView.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.snow);
        }else if(getItem(position).icon.equalsIgnoreCase("sleet")){
            ((ImageView) rootView.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.drizzle_snow);
        }else if(getItem(position).icon.equalsIgnoreCase("wind")){
            ((ImageView) rootView.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.haze);
        }else if(getItem(position).icon.equalsIgnoreCase("fog")){
            ((ImageView) rootView.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.haze);
        }else if(getItem(position).icon.equalsIgnoreCase("cloudy")){
            ((ImageView) rootView.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.cloudy);
        }else if(getItem(position).icon.equalsIgnoreCase("partly-cloudy-day")){
            ((ImageView) rootView.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.mostly_cloudy);
        }else if(getItem(position).icon.equalsIgnoreCase("partly-cloudy-night")){
            ((ImageView) rootView.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.cloudy_night);
        }else{
            ((ImageView) rootView.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.slight_drizzle);
        }

        return rootView;
    }
}

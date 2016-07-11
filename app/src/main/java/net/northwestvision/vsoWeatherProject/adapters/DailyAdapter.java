package net.northwestvision.vsoWeatherProject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.northwestvision.vsoWeatherProject.datastructure.DailyData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Acer on 02.07.2016.
 */
public class DailyAdapter extends ArrayAdapter<DailyData> {

    public DailyAdapter(Context context, DailyData[] objects) {
        super(context, net.northwestvision.vsoWeatherProject.R.layout.item_daily_list, net.northwestvision.vsoWeatherProject.R.id.time, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // View holder  design pattern is not used here, instead the convert view form the android system (super class) it self
        // is used, so it initializes everything and its not necessary to implement inflate logic manually
        View rootView = super.getView(position, convertView, parent);

        //setting up related data from the data structure to corresponding views
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.time)).setText(DateFormat.getDateInstance().format(new Date(getItem(position).time * 1000)));
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.summary)).setText(getItem(position).summary);
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.icon)).setText("Icon : " + getItem(position).icon);
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.sunriseTime)).setText("Изгрев : " + SimpleDateFormat.getInstance().format(new Date(getItem(position).sunriseTime * 1000)));
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.sunsetTime)).setText("Залез : " + SimpleDateFormat.getInstance().format(new Date(getItem(position).sunsetTime * 1000)));
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.moonPhase)).setText("Фаза на луната : " + String.valueOf(getItem(position).moonPhase));
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.precipType)).setText("Вероятност за : " + String.valueOf(getItem(position).precipType));
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.precipProbability)).setText("Вероятност в % : " + String.valueOf(getItem(position).precipProbability) + " \u0025");
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.temperatureMin)).setText("Минимална температура : " + String.valueOf(getItem(position).temperatureMin)+"\u2103");
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.temperatureMax)).setText("Максимална температура : " + String.valueOf(getItem(position).temperatureMax)+"\u2103");
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.humidity)).setText("Влажност : " + String.valueOf(getItem(position).humidity) + " \u0025");
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.windSpeed)).setText("Скорост на вятъра : " + String.valueOf(getItem(position).windSpeed)+" m/s");
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.windBearing)).setText("Посока на вятъра : " + String.valueOf(getItem(position).windBearing));
        ((TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.cloudCover)).setText("Облъчна покривмка : " + String.valueOf(getItem(position).cloudCover));
        return rootView;
    }
}
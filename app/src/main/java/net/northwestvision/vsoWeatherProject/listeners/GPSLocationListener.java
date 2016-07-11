package net.northwestvision.vsoWeatherProject.listeners;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import net.northwestvision.vsoWeatherProject.service.DataService;

/**
 * Created by Acer on 30.06.2016.
 */
public class GPSLocationListener implements LocationListener {

    private Context mContext;
    private String mUrl;
    private String mKey;

    public GPSLocationListener(Context context, String url, String key) {
        this.mContext = context;
        this.mUrl = url;
        this.mKey = key;
    }

    @Override
    public void onLocationChanged(Location location) {
        //setup intent to invoke DataService on change of location
        Intent intent = new Intent(mContext.getApplicationContext(), DataService.class);
        intent.putExtra(DataService.BASE_URL, mUrl);
        intent.putExtra(DataService.KEY, mKey);
        intent.putExtra(DataService.LATITUDE, location.getLatitude());
        intent.putExtra(DataService.LONGITUDE, location.getLongitude());

        //start weather forecast service on location change
        mContext.startService(intent);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(mContext.getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
    }

    @Override

    public void onProviderEnabled(String provider) {
        Toast.makeText(mContext.getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
    }
}

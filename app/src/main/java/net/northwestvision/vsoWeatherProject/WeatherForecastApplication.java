package net.northwestvision.vsoWeatherProject;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.widget.Toast;

import net.northwestvision.vsoWeatherProject.listeners.GPSLocationListener;
import net.northwestvision.vsoWeatherProject.service.DataService;

/**
 * Created by Acer on 28.06.2016.
 */
public class WeatherForecastApplication extends Application {

    public static final String W_FORECAST_API_KEY="7c54e22fb2c5bde138f134f32ec4bd9f";
    public static final String W_FORECAST_URL="https://api.forecast.io/forecast/";

    @Override
    public void onCreate() {
        super.onCreate();
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener mlocListener = new GPSLocationListener(this, W_FORECAST_URL, W_FORECAST_API_KEY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(WeatherForecastApplication.this, "No User Permission.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //Application main invocation point
        //invokes onLocationChanged callback in GPSLocationListener and eventually it will invoke the Intent Service every
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1000, mlocListener);
    }
}

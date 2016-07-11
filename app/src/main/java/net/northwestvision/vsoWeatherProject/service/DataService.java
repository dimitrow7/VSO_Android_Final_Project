package net.northwestvision.vsoWeatherProject.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import net.northwestvision.vsoWeatherProject.utils.DataDownloader;
import net.northwestvision.vsoWeatherProject.utils.DataResolver;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Acer on 02.07.2016.
 */
public class DataService extends IntentService {

    public final static String BASE_URL = "baseUrl";
    public final static String KEY = "key";
    public final static String LATITUDE = "latitude";
    public final static String LONGITUDE = "longitude";
    public final static String DOWNLOADED = "data_downloaded";

    public DataService() {
        super("DataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra(BASE_URL);

        // setting up the intended URL with API key, latitude and longitude to be able to download data from the
        // web service
        url = (url.lastIndexOf("/") == url.length() - 1 ? url : url + "/") + intent.getStringExtra(KEY) + "/" + intent.getDoubleExtra(LATITUDE, 0) + "," + intent.getDoubleExtra(LONGITUDE, 0) + "/" + "?units=si";
        try {
            DataDownloader dataDownloader = new DataDownloader(url);
            //get JSON Data from the web service as a String object
            String data = dataDownloader.getData();

            //pack JSON data taken as String object to respective Data structures
            DataResolver.getInstance().initialize(data);
            DataResolver.getInstance().getmForecast();

            //receiver registered with intent filter 'data_downloaded' will get affected
            Intent intent1 = new Intent(DOWNLOADED);
            //Inform the Fragment, as receiver registered accordingly that data download completed and ready for use.
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

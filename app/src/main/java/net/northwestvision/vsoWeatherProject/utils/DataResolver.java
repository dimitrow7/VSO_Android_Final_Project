package net.northwestvision.vsoWeatherProject.utils;

import net.northwestvision.vsoWeatherProject.datastructure.Alerts;
import net.northwestvision.vsoWeatherProject.datastructure.DailyData;
import net.northwestvision.vsoWeatherProject.datastructure.DailyStruct;
import net.northwestvision.vsoWeatherProject.datastructure.Flags;
import net.northwestvision.vsoWeatherProject.datastructure.Forecast;
import net.northwestvision.vsoWeatherProject.datastructure.HourlyData;
import net.northwestvision.vsoWeatherProject.datastructure.HourlyStruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Acer on 01.07.2016.
 */
public class DataResolver {

    private Forecast mForecast;
    private static volatile DataResolver instance = null;

    private static final String W_FORECAST_NOT_INIT = "Forecast Not Initialized";

    private DataResolver() {
    }

    // implemented with singleton design pattern as its not necessary to
    // create more than one DataResolver object to coordinate actions
    public static synchronized DataResolver getInstance() {
        if (instance == null) {
            instance = new DataResolver();
        }
        return instance;
    }

    /**
     * High level data initializing to custom data structures by extracting the JSON passed.
     *
     * @param json JSON String object
     */
    public void initialize(String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
        mForecast = new Forecast();
        //extract JSON objects
        mForecast.latitude = jsonObj.getLong("latitude");
        mForecast.longitude = jsonObj.getLong("longitude");
        mForecast.timezone = jsonObj.getString("timezone");
        mForecast.offset = jsonObj.getString("offset");

        //extract JSON objects and create respective weather data structures
        mForecast.currently = createHourlyData(jsonObj.getJSONObject("currently"));
        mForecast.hourly = createHourlyStruct(jsonObj.getJSONObject("hourly"));
        mForecast.daily = createDailyStruct(jsonObj.getJSONObject("daily"));
        mForecast.flags = createFlags(jsonObj.getJSONObject("flags"));

        if(jsonObj.has("alerts")) {
            mForecast.alerts = createAlerts(jsonObj.getJSONArray("alerts"));
        }
    }

    /**
     * Extract Hourly weather data from the JSON object
     *
     * @param jsonObject hourly weather data JSON object
     * @return returns HourlyStruct object with summery,icon and data (Json object array)
     */
    public HourlyStruct createHourlyStruct(JSONObject jsonObject) throws JSONException {
        HourlyStruct hourlyStruct = new HourlyStruct();
        hourlyStruct.summary = jsonObject.getString("summary");
        hourlyStruct.icon = jsonObject.getString("icon");
        hourlyStruct.data = createHourlyDataArray(jsonObject.getJSONArray("data"));
        return hourlyStruct;
    }

    /**
     * Extract Daily weather data from the JSON object
     *
     * @param jsonObject daily weather data JSON object
     * @return returns DailyStruct object with summery,icon and data (Json object array)
     */
    public DailyStruct createDailyStruct(JSONObject jsonObject) throws JSONException {
        DailyStruct dailyStruct = new DailyStruct();
        dailyStruct.summary = jsonObject.getString("summary");
        dailyStruct.icon = jsonObject.getString("icon");
        dailyStruct.data = createDailyDataArray(jsonObject.getJSONArray("data"));
        return dailyStruct;
    }

    /**
     * Extract Daily weather details from JSON array object
     *
     * @param jsonArray hourly weather details JSON array object
     * @return returns an array of HourlyData
     */

    private HourlyData[] createHourlyDataArray(JSONArray jsonArray) throws JSONException {
        ArrayList<HourlyData> datas = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            datas.add(createHourlyData(jsonArray.getJSONObject(i)));
        }
        return datas.toArray(new HourlyData[datas.size()]);
    }

    /**
     * Extract Daily weather details from JSON array object
     *
     * @param jsonArray daily weather details JSON array object
     * @return returns an array of DailyData
     */

    private DailyData[] createDailyDataArray(JSONArray jsonArray) throws JSONException {
        ArrayList<DailyData> datas = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            datas.add(createDailyData(jsonArray.getJSONObject(i)));
        }
        return datas.toArray(new DailyData[datas.size()]);
    }

    //helper method to extract data from JSON Object and pack to a HourlyData object
    private HourlyData createHourlyData(JSONObject jsonObject) throws JSONException {
        HourlyData hourlyData = new HourlyData();
        hourlyData.time = jsonObject.getLong("time");
        hourlyData.summary = jsonObject.getString("summary");
        hourlyData.icon = jsonObject.getString("icon");
        hourlyData.precipIntensity = jsonObject.getInt("precipIntensity");
        hourlyData.precipProbability = jsonObject.getInt("precipProbability");
        hourlyData.temperature = jsonObject.getInt("temperature");
        hourlyData.apparentTemperature = jsonObject.getDouble("apparentTemperature");
        hourlyData.dewPoint = jsonObject.getDouble("dewPoint");
        hourlyData.humidity = jsonObject.getDouble("humidity");
        hourlyData.windSpeed = jsonObject.getDouble("windSpeed");
        hourlyData.windBearing = jsonObject.getInt("windBearing");
        hourlyData.cloudCover = jsonObject.getDouble("cloudCover");
        hourlyData.pressure = jsonObject.getDouble("pressure");
        hourlyData.ozone = jsonObject.getDouble("ozone");
        return hourlyData;
    }

    //helper method to extract data from JSON Object and pack to a DailyData object
    private DailyData createDailyData(JSONObject jsonObject) throws JSONException {
        DailyData dailyData = new DailyData();
        dailyData.time = jsonObject.getLong("time");
        dailyData.summary = jsonObject.getString("summary");
        dailyData.icon = jsonObject.getString("icon");
        dailyData.sunriseTime = jsonObject.getLong("sunriseTime");
        dailyData.sunsetTime = jsonObject.getLong("sunsetTime");
        dailyData.moonPhase = jsonObject.getDouble("moonPhase");
        dailyData.precipIntensity = jsonObject.getDouble("precipIntensity");
        dailyData.precipIntensityMax = jsonObject.getDouble("precipIntensityMax");

        if(jsonObject.has("precipIntensityMaxTime")) {
            dailyData.precipIntensityMaxTime = jsonObject.getLong("precipIntensityMaxTime"); //Service is inconsistent
        }

        dailyData.precipProbability = jsonObject.getDouble("precipProbability");

        if(jsonObject.has("precipType")) {
            dailyData.precipType = jsonObject.getString("precipType"); //Service is inconsistent
        }

        dailyData.temperatureMin = jsonObject.getDouble("temperatureMin");
        dailyData.temperatureMinTime = jsonObject.getLong("temperatureMinTime");
        dailyData.temperatureMax = jsonObject.getDouble("temperatureMax");
        dailyData.temperatureMaxTime = jsonObject.getLong("temperatureMaxTime");
        dailyData.apparentTemperatureMin = jsonObject.getDouble("apparentTemperatureMin");
        dailyData.apparentTemperatureMinTime = jsonObject.getLong("apparentTemperatureMinTime");
        dailyData.apparentTemperatureMax = jsonObject.getDouble("apparentTemperatureMax");
        dailyData.apparentTemperatureMaxTime = jsonObject.getLong("apparentTemperatureMaxTime");
        dailyData.dewPoint = jsonObject.getDouble("dewPoint");
        dailyData.humidity = jsonObject.getDouble("humidity");
        dailyData.windSpeed = jsonObject.getDouble("windSpeed");
        dailyData.windBearing = jsonObject.getInt("windBearing");
        dailyData.cloudCover = jsonObject.getDouble("cloudCover");
        dailyData.pressure = jsonObject.getDouble("pressure");
        dailyData.ozone = jsonObject.getDouble("ozone");
        return dailyData;
    }



    /**
     * Extract weather Alert details from JSON array object
     *
     * @param jsonArray weather alert details JSON array object
     * @return returns an array of Alerts
     */
    private Alerts[] createAlerts(JSONArray jsonArray) throws JSONException {
        ArrayList<Alerts> dataArr = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            dataArr.add(createAlert(jsonArray.getJSONObject(i)));
        }
        return dataArr.toArray(new Alerts[dataArr.size()]);
    }

    /**
     * Extract related Alert data from the JSON object
     *
     * @param jsonObject Alerts JSON object
     * @return returns Alerts object
     */
    private Alerts createAlert(JSONObject jsonObject) throws JSONException {
        Alerts alerts = new Alerts();
        alerts.title = jsonObject.getString("title");
        alerts.time = jsonObject.getString("time");
        alerts.expires = jsonObject.getString("expires");
        alerts.description = jsonObject.getString("description");
        alerts.uri = jsonObject.getString("uri");

        return alerts;
    }

    /**
     * Extract related Flags data from the JSON object
     *
     * @param jsonObject flags JSON object
     * @return returns Flags object
     */
    private Flags createFlags(JSONObject jsonObject) throws JSONException {
        Flags flags = new Flags();
        flags.sources = createStringArray(jsonObject.getJSONArray("sources"));
        flags.isd_stations = createStringArray(jsonObject.getJSONArray("isd-stations"));
        flags.madis_stations = createStringArray(jsonObject.getJSONArray("madis-stations"));

        if(jsonObject.has("darksky-stations")) {
            flags.darksky_stations = createStringArray(jsonObject.getJSONArray("darksky-stations"));
        }
        if(jsonObject.has("datapoint-stations")) {
            flags.datapoint_stations = createStringArray(jsonObject.getJSONArray("datapoint-stations"));
        }
        if(jsonObject.has("lamp-stations")) {
            flags.lamp_stations = createStringArray(jsonObject.getJSONArray("lamp-stations"));
        }
        if(jsonObject.has("metar-stations")) {
            flags.metar_stations = createStringArray(jsonObject.getJSONArray("metar-stations"));
        }
        if(jsonObject.has("darksky-unavailable")) {
            flags.darksky_unavailable = jsonObject.getString("darksky-unavailable");
        }
        if(jsonObject.has("metno-license")) {
            flags.metno_license = jsonObject.getString("metno-license");
        }
        flags.units = jsonObject.getString("units");

        return flags;
    }

    /**
     * Extract Flags details from JSON array object
     *
     * @param jsonArray Flags details JSON array object
     * @return returns a String array of flags data
     */
    private String[] createStringArray(JSONArray jsonArray) throws JSONException {
        ArrayList<String> dataArr = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            dataArr.add(jsonArray.getString(i));
        }
        return dataArr.toArray(new String[dataArr.size()]);
    }


    //getters
    public Forecast getmForecast() {
        if (mForecast == null) {
            throw new IllegalStateException(W_FORECAST_NOT_INIT);
        }
        return mForecast;
    }

    public HourlyData getCurrent() {
        if (mForecast == null) {
            throw new IllegalStateException(W_FORECAST_NOT_INIT);
        }
        return mForecast.currently;
    }

    public HourlyStruct getHourly() {
        if (mForecast == null) {
            throw new IllegalStateException(W_FORECAST_NOT_INIT);
        }
        return mForecast.hourly;
    }

    public DailyStruct getDaily() {
        if (mForecast == null) {
            throw new IllegalStateException(W_FORECAST_NOT_INIT);
        }
        return mForecast.daily;
    }

    public Alerts[] getAlerts() {
        if (mForecast == null) {
            throw new IllegalStateException(W_FORECAST_NOT_INIT);
        }
        return mForecast.alerts;
    }

    public boolean isDataLoaded() {
        return mForecast != null;
    }

}

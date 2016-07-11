package net.northwestvision.vsoWeatherProject.datastructure;

/**
 * Created by Acer on 03.07.2016.
 */
public class Forecast {
    public double latitude;
    public double longitude;
    public String timezone;
    public String offset;
    public HourlyData currently;
    public HourlyStruct hourly;
    public DailyStruct daily;
    public Flags flags;
    public Alerts alerts[];
}

package net.northwestvision.vsoWeatherProject.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Acer on 30.07.2016.
 */
public class DataDownloader {

    public URL mUrl;

    public DataDownloader(String url) throws MalformedURLException {
        this.mUrl = new URL(url);
    }

    /**
     * Returns weather data JSON as a String via opening up a HTTP connection from the URL passed to the constructor
     *
     * @return JSON String
     */
    public String getData() throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) mUrl.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        } finally {
            // this will close the underlying streams if they are still open, but it additionally
            // releases the connection's resources back to the connection pool
            urlConnection.disconnect();
        }
    }

    /**
     * Converts JSON wrapped InputStream object to String object and returns.
     *
     * @param in InputStream returned from HttpURLConnection in getData()
     * @return JSON String
     */
    private String readStream(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String read = br.readLine();

        while(read !=null){
            sb.append(read);
            read = br.readLine();
        }

        return sb.toString();
    }
}


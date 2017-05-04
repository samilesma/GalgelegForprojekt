package com.u_09.galgeleg.Model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ahmad on 1/4/2017.
 */

public class Web extends AsyncTask<String, String, String> {

    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            Log.d("XXXXX TEST XXXXX", url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            Log.d("XXXXX TEST XXXXX", "RESPONSE CODE: " + connection.getResponseCode());
            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line="",text="";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                text=text+line+"\n";
            }
            return text;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
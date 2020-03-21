package com.example.rssfeed.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Parser {

    public String getXmlFromUrl(String url1) {
        String xml = null;

        try {

            URL url = new URL(url1);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);

            try {
                InputStream in = connection.getInputStream();
                int responseCode = connection.getResponseCode();
                String redirectionLocation = connection.getHeaderField("Location");
                // Handling redirection
                if(responseCode > 300 && responseCode < 400) {
                    String redirectUrl = connection.getHeaderField("Location");
                    if(TextUtils.isEmpty(redirectUrl)) {
                        return "Failed to redirect";
                    }
                    connection.disconnect();
                    url = new URL(redirectUrl);
                    connection = (HttpsURLConnection) url.openConnection();
                    in = connection.getInputStream();
                }
                xml = readStream(in);
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                connection.disconnect();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

    private static String readStream(InputStream inputStream) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            return buffer.toString();
        } catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }
}

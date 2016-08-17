package ru.marten.friendsfoto.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by marten on 09.08.16.
 */
    public class ConnectionUnit {



    public static final JSONObject getCommonPacket(String path) throws IOException, JSONException {
        JSONObject result;
        BufferedReader reader = null;

        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            result = new JSONObject(buf.toString());
            return result;

        } finally {
            if(reader != null) {
                reader.close();
            }
        }
    }

    public static final Bitmap getImage(String path) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream)new URL(path).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }


}

package com.dotplays.myapplication;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class PostTask extends AsyncTask<String, Integer, String> {
    private TextView tvkq;

    public PostTask(TextView tvkq) {
        this.tvkq = tvkq;
    }

    String link = "https://jsonplaceholder.typicode.com/posts";
    //  title: 'foo',
    //    body: 'bar',
    //    userId: 1,

    //    {
//  id: 101,
//  title: 'foo',
//  body: 'bar',
//  userId: 1
//}
    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(link);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)
                    url.openConnection();
            httpsURLConnection.setRequestMethod("POST");
            //Content-Type: application/x-www-form-urlencoded
            httpsURLConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            String title = "Huy Nguyen";
            String body = "ABC";
            String id = "1234";
            // khoi tao param
            StringBuilder params = new StringBuilder();
            params.append("title");
            params.append("=");
            params.append(title);

            params.append("&");
            params.append("body");
            params.append("=");
            params.append(body);

            params.append("&");
            params.append("userId");
            params.append("=");
            params.append(id);

            OutputStream os = httpsURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter
                    (new OutputStreamWriter(os, "UTF-8"));

            // dua param vao body cua request
            writer.append(params);

            // giai phong bo nho
            writer.flush();
            // ket thuc truyen du lieu vao output
            writer.close();
            os.close();
            Scanner scanner = new Scanner(httpsURLConnection.getInputStream());
            String data = "";
            while (scanner.hasNext()) {
                data += scanner.nextLine();
            }
            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);

            String title = jsonObject.getString("title");
            String body = jsonObject.getString("body");
            int userId = jsonObject.getInt("userId");

            tvkq.setText(title + " : " + userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

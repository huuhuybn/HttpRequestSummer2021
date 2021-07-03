package com.dotplays.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class GetTask extends AsyncTask<String, Integer, String> {

    private TextView tvkq;
    private ListView listView;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public GetTask(TextView tvkq) {
        this.tvkq = tvkq;
    }

    String link = "https://jsonplaceholder.typicode.com/posts";

    public GetTask(TextView tvkq, ListView listView) {
        this.tvkq = tvkq;
        this.listView = listView;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(link);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)
                    url.openConnection();
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
            JSONArray jsonArray = new JSONArray(s);
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String title = jsonObject.getString("title");
                String body = jsonObject.getString("body");
                int userId = jsonObject.getInt("userId");
                strings.add(title);
            }
            ArrayAdapter<String> simpleAdapter = new ArrayAdapter(context,
                    android.R.layout.simple_list_item_1, strings);
            listView.setAdapter(simpleAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //tvkq.setText(s);
    }
}

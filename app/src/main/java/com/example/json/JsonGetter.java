package com.example.json;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class JsonGetter extends AsyncTask<String, Void, String> {
    private ArrayList<Word> wordList = new ArrayList<Word>();
    private String name;
    private String frequency;
    private String date;


    public ArrayList<Word> JsonParsing(String data, String date_) {
        try {
            JSONObject jsonObject = new JSONObject(data);

            JSONArray peoples = jsonObject.getJSONArray("result");

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                name = c.getString("data1");
                frequency = c.getString("data2");
                date = c.getString("data3");

                if (name == "null" | frequency == "null" | date == "null")
                    continue;

                if (date.substring(0, 7).matches(date_.substring(0, 7))) { //String을 잘라서 년도와 월 비교
                    Word word = new Word(name, Integer.parseInt(frequency), date); //word 객체 생성.
                    wordList.add(word);//리스트에 입력.
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return wordList;//리턴
    }


    @Override
    protected String doInBackground(String... params) {
        String address = params[0];

        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
            }

            return sb.toString().trim();

        } catch (Exception e) {
            return null;
        }

    }
}

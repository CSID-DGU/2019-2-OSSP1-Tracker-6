package com.example.json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Word> wordList;
        String data="";
        String date="2019-99-97";
        JsonGetter jsonGetter = new JsonGetter();
        try {
            data = jsonGetter.execute("http://10.0.2.2/PHP_connection.php").get();
        } catch (ExecutionException e) {

        } catch (InterruptedException e) {e.printStackTrace();
            e.printStackTrace();
        }


        wordList = jsonGetter.JsonParsing(data,date);
        for(int i=0;i<wordList.size();i++){
            System.out.println(wordList.get(i).date + " " + wordList.get(i).freq + " "+wordList.get(i).name);
        }

    }
}

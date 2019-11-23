package com.example.mytest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String titleCollection;
    Tokenizer tokenizer = new Tokenizer();
    Analyzer analyzer = new Analyzer();
    List<String> nounData = new ArrayList<String>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //divide and conquer


        String contents="";

        Crawler crawler = new Crawler();
        try {
            titleCollection = crawler.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        tokenizer.setElements(titleCollection);
        tokenizer.tokenize();


        nounData = tokenizer.getStringList();
        analyzer.DataLoad(nounData);

        for(int i=0;i<analyzer.getSize();i++){
            contents+=analyzer.getWord(i)+" "+analyzer.getFrequency(i)+" "+crawler.getTime()+" ";
        }

        WordCloud wc= new WordCloud(3);
        wc.makeWord(contents);

       showWordCloud(wc);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater(); //xml 메뉴 가져오기
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //메뉴 눌렀을 때 처리 : swtich 사용
        switch (item.getItemId()){
            case R.id.past:
                Intent intent=new Intent(this,pastWordCloud.class);
                startActivity(intent);
                return true;

            case R.id.refresh: //코드 재활용
                //수정 필요함...

        }
        return super.onOptionsItemSelected(item);
    }


    // erase
    // 지원이 코드로 대체
    void showWordCloud(WordCloud wc){
        setContentView(R.layout.activity_main);
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl);

        int ts=0;
        int wordNum=wc.length();

        if(wordNum >24) wordNum = 23;

        Display display = getWindowManager().getDefaultDisplay();
        Point scsize = new Point();
        display.getSize(scsize);

        int sum=0;
        for(int i=0;i<wordNum;++i) {
            sum+=wc.words.get(i).getFreq();
        }


        if(wc.words.get(0).getFreq()>75) ts = 1;
        else if(wc.words.get(0).getFreq()>50) ts = 2;
        else if(wc.words.get(0).getFreq()>25) ts = 3;
        else ts = 6;

        String str = wc.words.get(0).getName();
        float textsize = (float)(wc.words.get(0).getFreq())/sum*100;

        TextView centertv = (TextView)findViewById(R.id.center);
        centertv.setTextSize(textsize*ts);
        centertv.setText(str);
        centertv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), discussion.class);
                startActivity(intent);
            }
        });


        TextView[] buttons= new TextView[wordNum];



        for(int i=1;i<wordNum;++i) {
            TextView button = new TextView(this);

            buttons[i] = button;
            str = wc.words.get(i).getName();
            textsize = (float)(wc.words.get(i).getFreq())/sum*100;
            buttons[i].setText(str);
            buttons[i].setId(i);
            buttons[i].setTextSize(textsize*ts);

            RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            buttonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

/*
            switch (i % 4) {
                case 0:
                    buttonLayoutParams.addRule(RelativeLayout.BELOW, centertv.getId());
                    break;
                case 1:
                    buttonLayoutParams.addRule(RelativeLayout.RIGHT_OF, centertv.getId());
                    break;
                case 2:
                    buttonLayoutParams.addRule(RelativeLayout.LEFT_OF, centertv.getId());
                    break;
                case 3:
                    buttonLayoutParams.addRule(RelativeLayout.ABOVE, centertv.getId());
                    break;
            }
            */
            if(i==1) buttonLayoutParams.addRule(RelativeLayout.LEFT_OF, centertv.getId());
            else if(i==2) buttonLayoutParams.addRule(RelativeLayout.RIGHT_OF, centertv.getId());
            else if(i==3) buttonLayoutParams.addRule(RelativeLayout.ABOVE, centertv.getId());
            else if(i==4) buttonLayoutParams.addRule(RelativeLayout.BELOW, centertv.getId());
            else if(i<11&&i%2==1) buttonLayoutParams.addRule(RelativeLayout.ABOVE, i-2);
            else if(i<11&&i%2==0) buttonLayoutParams.addRule(RelativeLayout.BELOW, i-2);
            else if(i<15){
                switch (i % 4) {
                    case 0:
                        buttonLayoutParams.addRule(RelativeLayout.BELOW, 1);
                        buttonLayoutParams.addRule(RelativeLayout.LEFT_OF, 4);
                        break;
                    case 1:
                        buttonLayoutParams.addRule(RelativeLayout.BELOW, 2);
                        buttonLayoutParams.addRule(RelativeLayout.RIGHT_OF, 4);
                        break;
                    case 2:
                        buttonLayoutParams.addRule(RelativeLayout.ABOVE, 1);
                        buttonLayoutParams.addRule(RelativeLayout.LEFT_OF, 3);
                        break;
                    case 3:
                        buttonLayoutParams.addRule(RelativeLayout.ABOVE, 2);
                        buttonLayoutParams.addRule(RelativeLayout.RIGHT_OF, 3);
                        break;
                }
            }
            else if(i<23){
                switch (i % 8) {
                    case 0:
                        buttonLayoutParams.addRule(RelativeLayout.ABOVE, 14);
                        buttonLayoutParams.addRule(RelativeLayout.LEFT_OF, 3);
                        break;
                    case 1:
                        buttonLayoutParams.addRule(RelativeLayout.ABOVE, 11);
                        buttonLayoutParams.addRule(RelativeLayout.RIGHT_OF, 3);
                        break;
                    case 2:
                        buttonLayoutParams.addRule(RelativeLayout.ABOVE, 2);
                        buttonLayoutParams.addRule(RelativeLayout.RIGHT_OF, 11);
                        break;
                    case 3:
                        buttonLayoutParams.addRule(RelativeLayout.BELOW, 2);
                        buttonLayoutParams.addRule(RelativeLayout.RIGHT_OF, 13);
                        break;
                    case 4:
                        buttonLayoutParams.addRule(RelativeLayout.BELOW, 13);
                        buttonLayoutParams.addRule(RelativeLayout.RIGHT_OF, 4);
                        break;
                    case 5:
                        buttonLayoutParams.addRule(RelativeLayout.BELOW, 12);
                        buttonLayoutParams.addRule(RelativeLayout.LEFT_OF, 4);
                        break;
                    case 6:
                        buttonLayoutParams.addRule(RelativeLayout.BELOW, 1);
                        buttonLayoutParams.addRule(RelativeLayout.LEFT_OF, 12);
                        break;
                    case 7:
                        buttonLayoutParams.addRule(RelativeLayout.ABOVE, 1);
                        buttonLayoutParams.addRule(RelativeLayout.LEFT_OF, 14);
                        break;
                }
            }


            button.setLayoutParams(buttonLayoutParams);



            buttons[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), discussion.class);
                    startActivity(intent);
                }
            });

            rl.addView(button);
        }
}


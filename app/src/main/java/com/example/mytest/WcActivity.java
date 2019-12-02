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
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class WcActivity extends AppCompatActivity {

    String titleCollection;
    Tokenizer tokenizer = new Tokenizer();
    Analyzer analyzer = new Analyzer();
    List<String> nounData = new ArrayList<String>();

    WordCloud wc;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wc = new WordCloud();
        showWordCloud(wc);

        AutoSave autoSave= new AutoSave(wc.analyzer);
        autoSave.run();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //xml 메뉴 가져오기
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //메뉴 눌렀을 때 처리 : swtich 사용
        switch (item.getItemId()) {
            case R.id.past:
                Intent intent = new Intent(this, pastWordCloud.class);
                startActivity(intent);
                return true;

            case R.id.refresh: //코드 재활용
               // showRandomCloud(wc);

        }
        return super.onOptionsItemSelected(item);
    }


    void showRandomCloud(WordCloud wc){
        Random random= new Random();
        int select=random.nextInt()%2;
        switch(select){
            case 0:
                showWordCloud(wc);
                break;
            case 1:
               // showWordCloud2(wc);
                break;
        }
    }


    void showWordCloud(WordCloud wc) {
        setContentView(R.layout.wclayout);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);

        double ts = 0;
        int wordNum = wc.length();

        if (wordNum > 23) wordNum = 23;

        Display display = getWindowManager().getDefaultDisplay();
        Point scsize = new Point();
        display.getSize(scsize);

        int sum = 0;
        for (int i = 0; i < wordNum; ++i) {
            sum += wc.words.get(i).getFreq();
        }

        //textsize
        double textsize = (double) (wc.words.get(0).getFreq()) / sum * 100;

        if (textsize > 75) ts = 0.5;
        else if (textsize > 50) ts = 1;
        else if (textsize > 20) ts = 2;
        else ts = 5;

        //get word name
        String str = wc.words.get(0).getName();


        //center word
        TextView centertv = (TextView) findViewById(R.id.center);
        centertv.setTextSize((float)(textsize * ts));
        centertv.setText(str);
        centertv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), discussion.class);
                startActivity(intent);
            }
        });

        // center word 완성
        // center을 중심으로 단어들 배치
        TextView[] buttons = new TextView[wordNum];


        for (int i = 1; i < wordNum; ++i) {
            TextView button = new TextView(this);

            // for문 돌면서 버튼 특징 하나씩 형성하기
            buttons[i] = button;
            str = wc.words.get(i).getName();
            textsize = (double) (wc.words.get(i).getFreq()) / sum * 100;
            buttons[i].setText(str);
            buttons[i].setId(i);
            buttons[i].setTextSize((float)(textsize * ts));

            RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            buttonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

            //위치 정하기
            if (i == 1) buttonLayoutParams.addRule(RelativeLayout.LEFT_OF, centertv.getId());
            else if (i == 2) buttonLayoutParams.addRule(RelativeLayout.RIGHT_OF, centertv.getId());
            else if (i == 3) buttonLayoutParams.addRule(RelativeLayout.ABOVE, centertv.getId());
            else if (i == 4) buttonLayoutParams.addRule(RelativeLayout.BELOW, centertv.getId());
            else if (i < 11 && i % 2 == 1) buttonLayoutParams.addRule(RelativeLayout.ABOVE, i - 2);
            else if (i < 11 && i % 2 == 0) buttonLayoutParams.addRule(RelativeLayout.BELOW, i - 2);
            else if (i < 15) {
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
            } else if (i < 23) {
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

            // 온클릭 설정해주기
            button.setLayoutParams(buttonLayoutParams);

            buttons[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), discussion.class);
                    startActivity(intent);
                }
            });

            //뷰에 추가하기
            rl.addView(button);
        }
    }

   /* void showWordCloud2(WordCloud wc){

        int sum = 0;
        for (int i = 0; i < wc.length(); ++i) {
            sum += wc.words.get(i).getFreq();
        }

        String keywordColorGreen[] = new String[10];
        keywordColorGreen[0] = "#ACFA58";
        keywordColorGreen[1] = "#82FA58";
        keywordColorGreen[2] = "#81F79F";
        keywordColorGreen[3] = "#31B404";
        keywordColorGreen[4] = "#01DF3A";
        keywordColorGreen[5] = "#00FF00";
        keywordColorGreen[6] = "#2EFE2E";
        keywordColorGreen[7] = "#BCF5A9";
        keywordColorGreen[8] = "#D8F781";
        keywordColorGreen[9] = "#01710F"; // 57 ~ 67줄 버튼의 색상 배열(초록색 테마)

        final RelativeLayout lm1 = (RelativeLayout) findViewById(R.id.fl1);
        final RelativeLayout lm2 = (RelativeLayout) findViewById(R.id.fl2);
        final RelativeLayout lm3 = (RelativeLayout) findViewById(R.id.fl3);
        final RelativeLayout lm4 = (RelativeLayout) findViewById(R.id.fl4);

        Random random = new Random(); // 77줄 버튼의 위치를 결정해주는 난수 설정
        Random randomColor = new Random(); // 78줄 버튼의 색상을 결정해주는 난수 설정

        Button center1 = (Button) findViewById(R.id.button1);
        Button center2 = (Button) findViewById(R.id.button2);
        Button center3 = (Button) findViewById(R.id.button3);
        Button center4 = (Button) findViewById(R.id.button4);

        for(int i = 0; i < 5; i++){
            int wordColorNumber = randomColor.nextInt(100) % 10; // 89줄 버튼 색상 난수 설정
            final Button btn = new Button(this);

            btn.setId(i);
            // 수정
            btn.setText(wc.words.get(i).name);
            btn.setTextColor(Color.parseColor(keywordColorGreen[wordColorNumber]));
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setWidth(500);
            btn.setHeight(300);
            // 수정
            btn.setTextSize((float) (wc.words.get(0).getFreq()) / sum * 100);

            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            params1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

            if(i < 4) {
                switch (i % 4) {
                    case 0:
                        params1.addRule(RelativeLayout.BELOW, center1.getId());
                        break;
                    case 1:
                        params1.addRule(RelativeLayout.RIGHT_OF, center1.getId());
                        break;
                    case 2:
                        params1.addRule(RelativeLayout.LEFT_OF, center1.getId());
                        break;
                    case 3:
                        params1.addRule(RelativeLayout.ABOVE, center1.getId());
                        break;
                }
            }
            else if (i == 4) {
                int randomNumber = random.nextInt(3);
                switch (randomNumber) {
                    case 0:
                        params1.addRule(RelativeLayout.BELOW, 3);
                        break;
                    case 1:
                        params1.addRule(RelativeLayout.BELOW, 2);
                        break;
                    case 2:
                        params1.addRule(RelativeLayout.BELOW, 1);
                        break;
                }
            }

            btn.setLayoutParams(params1);

            btn.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), discussion.class);
                    startActivity(intent);
                }
            }); // 123 ~ 128줄 버튼 클릭 시 다음 화면으로 넘어가는 기능 부여

            lm1.addView(btn);
        }

        for(int i = 5; i < 10; i++){
            int wordColorNumber = randomColor.nextInt(100) % 10; // 89줄 버튼 색상 난수 설정
            final Button btn = new Button(this);

            btn.setId(i);
            btn.setText(wc.words.get(i).name);
            btn.setTextColor(Color.parseColor(keywordColorGreen[wordColorNumber]));
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setWidth(500);
            btn.setHeight(300);
            btn.setTextSize((float) (wc.words.get(0).getFreq()) / sum * 100);

            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            params2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

            if(i < 9) {
                switch (i % 4) {
                    case 0:
                        params2.addRule(RelativeLayout.BELOW, center2.getId());
                        break;
                    case 1:
                        params2.addRule(RelativeLayout.RIGHT_OF, center2.getId());
                        break;
                    case 2:
                        params2.addRule(RelativeLayout.LEFT_OF, center2.getId());
                        break;
                    case 3:
                        params2.addRule(RelativeLayout.ABOVE, center2.getId());
                        break;
                }
            }
            else if (i == 9) {
                int randomNumber = random.nextInt(3);
                switch (randomNumber) {
                    case 0:
                        params2.addRule(RelativeLayout.BELOW, 8);
                        break;
                    case 1:
                        params2.addRule(RelativeLayout.BELOW, 7);
                        break;
                    case 2:
                        params2.addRule(RelativeLayout.BELOW, 6);
                        break;
                }
            }

            btn.setLayoutParams(params2);

            btn.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), discussion.class);
                    startActivity(intent);
                }
            }); // 123 ~ 128줄 버튼 클릭 시 다음 화면으로 넘어가는 기능 부여

            lm2.addView(btn);
        }

        for(int i = 10; i < 15; i++){
            int wordColorNumber = randomColor.nextInt(100) % 10; // 89줄 버튼 색상 난수 설정
            final Button btn = new Button(this);

            btn.setId(i);
            btn.setText(wc.words.get(i).name);
            btn.setTextColor(Color.parseColor(keywordColorGreen[wordColorNumber]));
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setWidth(500);
            btn.setHeight(300);
            btn.setTextSize((float) (wc.words.get(0).getFreq()) / sum * 100);

            RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params3.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            params3.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

            if(i < 14) {
                switch (i % 4) {
                    case 0:
                        params3.addRule(RelativeLayout.BELOW, center3.getId());
                        break;
                    case 1:
                        params3.addRule(RelativeLayout.RIGHT_OF, center3.getId());
                        break;
                    case 2:
                        params3.addRule(RelativeLayout.LEFT_OF, center3.getId());
                        break;
                    case 3:
                        params3.addRule(RelativeLayout.ABOVE, center3.getId());
                        break;
                }
            }
            else if (i == 14) {
                int randomNumber = random.nextInt(3);
                switch (randomNumber) {
                    case 0:
                        params3.addRule(RelativeLayout.BELOW, 13);
                        break;
                    case 1:
                        params3.addRule(RelativeLayout.BELOW, 12);
                        break;
                    case 2:
                        params3.addRule(RelativeLayout.BELOW, 11);
                        break;
                }
            }

            btn.setLayoutParams(params3);

            btn.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), discussion.class);
                    startActivity(intent);
                }
            }); // 123 ~ 128줄 버튼 클릭 시 다음 화면으로 넘어가는 기능 부여

            lm3.addView(btn);
        }


        for(int i = 15; i < 20; i++){
            int wordColorNumber = randomColor.nextInt(100) % 10; // 89줄 버튼 색상 난수 설정
            final Button btn = new Button(this);

            btn.setId(i);
            btn.setText(wc.words.get(i).name);
            btn.setTextColor(Color.parseColor(keywordColorGreen[wordColorNumber]));
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setWidth(500);
            btn.setHeight(300);
            btn.setTextSize((float) (wc.words.get(0).getFreq()) / sum * 100);

            RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params4.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            params4.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

            if(i < 19) {
                switch (i % 4) {
                    case 0:
                        params4.addRule(RelativeLayout.BELOW, center4.getId());
                        break;
                    case 1:
                        params4.addRule(RelativeLayout.RIGHT_OF, center4.getId());
                        break;
                    case 2:
                        params4.addRule(RelativeLayout.LEFT_OF, center4.getId());
                        break;
                    case 3:
                        params4.addRule(RelativeLayout.ABOVE, center4.getId());
                        break;
                }
            }
            else if (i == 19) {
                int randomNumber = random.nextInt(3);
                switch (randomNumber) {
                    case 0:
                        params4.addRule(RelativeLayout.BELOW, 18);
                        break;
                    case 1:
                        params4.addRule(RelativeLayout.BELOW, 17);
                        break;
                    case 2:
                        params4.addRule(RelativeLayout.BELOW, 16);
                        break;
                }
            }

            btn.setLayoutParams(params4);

            btn.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), discussion.class);
                    startActivity(intent);
                }
            }); // 123 ~ 128줄 버튼 클릭 시 다음 화면으로 넘어가는 기능 부여

            lm4.addView(btn);
        }

        *//*Button previousKeyword = (Button) findViewById(R.id.previousKeyword);

        previousKeyword.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), PreviousWC.class);
                    startActivity(intent);
                }
        });*//*
    }
*/
}




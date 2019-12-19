package com.example.mytest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class pastWordCloud extends AppCompatActivity {
    Vector<Word> wordList;
    String data;
    String date;
    JsonGetter jsonGetter = new JsonGetter();
    WordCloud wordCloud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_word_cloud);

        data="";
        jsonGetter=new JsonGetter();
            try {
                data = jsonGetter.execute("http://10.0.2.2/wordsdata.php").get();
            } catch (ExecutionException e) {

            } catch (InterruptedException e) {e.printStackTrace();
                e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //xml 메뉴 가져오기
        inflater.inflate(R.menu.manu_past, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //메뉴 눌렀을 때 처리 : swtich 사용
        //메뉴에 스크롤 넣는 방법 생각해보기
        switch (item.getItemId()){
            case R.id.Jan:
                date="2019-12-18";
                wordList = jsonGetter.JsonParsing(data,date);
                int size= wordList.size();
                wordCloud=new WordCloud(wordList);
                showWordCloud(wordCloud);
                return true;

            case R.id.Feb: //코드 재활용
                date="2019-12-19";

                wordList = jsonGetter.JsonParsing(data,date);
                wordCloud=new WordCloud(wordList);
                this.showWordCloud(wordCloud);
                return true;

            case R.id.Mar:
                date="2019-03-11";

                wordList = jsonGetter.JsonParsing(data,date);
                wordCloud=new WordCloud(wordList);
                this.showWordCloud(wordCloud);
                return true;

            case R.id.Apr: //코드 재활용
                date="2019-04-11";

                wordList = jsonGetter.JsonParsing(data,date);
                wordCloud=new WordCloud(wordList);
                this.showWordCloud(wordCloud);
                return true;

            case R.id.May:
                date="2019-05-11";

                wordList = jsonGetter.JsonParsing(data,date);
                wordCloud=new WordCloud(wordList);
                this.showWordCloud(wordCloud);
                return true;

            case R.id.Jun: //코드 재활용
                date="2019-06-11";

                wordList = jsonGetter.JsonParsing(data,date);
                wordCloud=new WordCloud(wordList);
                this.showWordCloud(wordCloud);
                return true;

            case R.id.Jly: //코드 재활용
                date="2019-07-11";

                wordList = jsonGetter.JsonParsing(data,date);
                wordCloud=new WordCloud(wordList);
                this.showWordCloud(wordCloud);
                return true;

            case R.id.Aug: //코드 재활용
                date="2019-08-11";

                wordList = jsonGetter.JsonParsing(data,date);
                wordCloud=new WordCloud(wordList);
                this.showWordCloud(wordCloud);
                return true;

            case R.id.Sep: //코드 재활용
                date="2019-09-11";

                wordList = jsonGetter.JsonParsing(data,date);
                wordCloud=new WordCloud(wordList);
                this.showWordCloud(wordCloud);
                return true;

            case R.id.Oct: //코드 재활용
                date="2019-10-11";

                wordList = jsonGetter.JsonParsing(data,date);
                wordCloud=new WordCloud(wordList);
                this.showWordCloud(wordCloud);
                return true;

            case R.id.Nov: //코드 재활용
                date="2019-11-11";

                wordList = jsonGetter.JsonParsing(data,date);
                wordCloud=new WordCloud(wordList);
                this.showWordCloud(wordCloud);
                return true;

            case R.id.Dec: //코드 재활용
                date="2019-12-11";

                wordList = jsonGetter.JsonParsing(data,date);
                wordCloud=new WordCloud(wordList);
                this.showWordCloud(wordCloud);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void showWordCloud(WordCloud wc) {
        setContentView(R.layout.wclayout);
        setContentView(R.layout.activity_past_word_cloud);
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
        double textsize =  (wc.words.get(0).getFreq()) / (double)sum * 100;
        //double textsize = (double) (wc.words.get(0).getFreq()) / sum * 100;

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
}
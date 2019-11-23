package com.example.mytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

public class pastWordCloud extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_word_cloud);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //메뉴 눌렀을 때 처리 : swtich 사용
        //메뉴에 스크롤 넣는 방법 생각해보기
        switch (item.getItemId()){
            case R.id.Jan:

                return true;

            case R.id.Feb: //코드 재활용

                return true;

            case R.id.Mar:

                return true;

            case R.id.Apr: //코드 재활용

                return true;

            case R.id.May:

                return true;

            case R.id.Jun: //코드 재활용

                return true;

            case R.id.Jly: //코드 재활용

                return true;

            case R.id.Aug: //코드 재활용

                return true;

            case R.id.Sep: //코드 재활용

                return true;

            case R.id.Oct: //코드 재활용

                return true;

            case R.id.Nov: //코드 재활용

                return true;

            case R.id.Dec: //코드 재활용

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

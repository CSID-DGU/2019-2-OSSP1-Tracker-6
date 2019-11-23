package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class discussion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        /*
        구현1 : 이미 저장된 자료 읽어서 보여주기
        DB에 연결해서 자료 가져와야 함
        DB에서 내용 가져와서
        list에 넣고 이걸 어댑터 뷰를 활용하여 꽂으면 한번에 될듯
         */

        /*
        구현 2 : 글 써서 추가된거 보여주고 db에 저장하기
        */

    }
}

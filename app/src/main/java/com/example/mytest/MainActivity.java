package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytest.R;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button  btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( password.getWindowToken(), 0);    //hide keyboard
                    return true;
                }
                return false;
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Check = "";
                User u = new User();
                u.ID = username.getText().toString();
                u.PW = password.getText().toString();

                Crawler crawler = new Crawler();

                try {
                    Check = crawler.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(Check != "" )
                {
                    //实现跳转
                    Toast.makeText(MainActivity.this,"welcome",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,WcActivity.class);
                    MainActivity.this.startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"please enter the correct username and password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

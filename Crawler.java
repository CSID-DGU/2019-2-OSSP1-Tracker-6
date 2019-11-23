package com.example.mytest;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Crawler extends AsyncTask<Void, Void, String> {
    private String SELECT_TAG;
    private Document doc;
    private String titleCollection = "";
    private String time;
    private Elements elements;

    //현재시간 time 필드에 저장.
    public void setTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd");
        String str = dayTime.format(new Date(time));
        this.time = str;
    }

    //time getter.
    public String getTime() {
        return time;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        setTime();
    }



    @Override
    protected String doInBackground(Void... voids) {
        SELECT_TAG = "article[created_at^=" + time + "]";
        //아이디 및 비밀번호
        String ID = "516rudgus";
        String PW = "tkatka33";

        // 로그인 전송할 폼 데이터
        Map<String, String> data = new HashMap();
        data.put("userid", ID);
        data.put("password", PW);

        // 로그인(POST)
        Connection.Response response = null;
        try {
            response = Jsoup.connect("https://everytime.kr/user/login")
                    .data(data)
                    .method(Connection.Method.POST)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 로그인 성공 후 얻은 쿠키.
        Map<String, String> loginCookie = response.cookies();

        int i = 1;
        do {
            // 게시판 번호와 페이지번호
            int pageNum = i;
            String BN = "377389";
            String page = Integer.toString(pageNum * 20 - 20);

            // 페이지 전송할 폼 데이터
            Map<String, String> pagedata = new HashMap();
            pagedata.put("id", BN);
            pagedata.put("start_num", page);

            // 페이지 크롤링
            Connection.Response PageResponse = null;
            try {
                PageResponse = Jsoup.connect("https://everytime.kr/find/board/article/list")
                        .cookies(loginCookie)
                        .data(pagedata)
                        .method(Connection.Method.POST)
                        .execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //크롤링 된 게시글 데이터.

            try {
                doc = PageResponse.parse();
            } catch (IOException e) {
                e.printStackTrace();
            }


            //현재 날짜 게시글만 select
            elements = doc.select(SELECT_TAG);
            //titleCollection에 제목만 모두 이어붙임.
            for (Element k : elements) {
                titleCollection = titleCollection + k.attr("title") + " ";
            }

            i++;
        } while (elements.hasAttr("id"));// elements에 값이 들어가지 않으면 종료.

        return titleCollection;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

}


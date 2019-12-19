package com.example.mytest;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class MysqlInputer extends AsyncTask<String, Void, String> {
    Analyzer analyzer;

    public void inputData(Analyzer analyzer){
        this.analyzer=analyzer;//입력받은 annalyzer로 doinbackground 구현 해보기
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String serverURL = params[0];
        String postParameters;
        for(int i=0;i<analyzer.getSize();i++){
            postParameters = "data1=" + analyzer.getWord(i) + "&data2=" + analyzer.getFrequency(i) + "&data3=" + params[1];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("phpTest", "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                //return sb.toString();


            } catch (Exception e) {

                Log.d("phpTest", "InsertData: Error ", e);

                //return new String("Error: " + e.getMessage());
            }
        }

        return serverURL;

    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d("phpTest", "POST response  - " + result);
    }
}

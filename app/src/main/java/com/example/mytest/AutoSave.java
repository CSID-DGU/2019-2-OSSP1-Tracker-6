package com.example.mytest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AutoSave extends Thread{
    private Date currentTime;
    private Date nextDate;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private Analyzer myAnalyzer;
    private long totalDelay;

    AutoSave(Analyzer analyzer){
        myAnalyzer=analyzer;
        currentTime=new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentTime);
        c.add(Calendar.DATE, 1);
        nextDate=c.getTime();

        long delayH=23-currentTime.getHours();
        long delayM=60-currentTime.getMinutes();
        totalDelay= delayH*3600+delayM*60;
    }

    public void run() {
        String StringNextDate=df.format(nextDate);
        while(true){
            try {
                sleep(totalDelay);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            MysqlInputer mysqlInputer = new MysqlInputer();
            mysqlInputer.inputData(myAnalyzer);
            mysqlInputer.execute("http://10.0.2.2/insert.php",StringNextDate);
        }
    }

}
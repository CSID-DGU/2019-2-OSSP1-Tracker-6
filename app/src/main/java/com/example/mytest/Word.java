package com.example.mytest;

import android.widget.Button;

public class Word {
    String name;
    int freq;
    String date;
    Button button;

    public Word(){}

    public Word(String name, int freq, String date) {
        this.name = name;
        this.freq = freq;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

package com.example.mytest;

import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;

import java.io.IOException;
import java.util.List;

public class Analyzer {
    private FrequencyAnalyzer frequencyAnalyzer;
    private List<WordFrequency> wordFrequencies;
    private List<String> nounData;


    public Analyzer(){
        frequencyAnalyzer = new FrequencyAnalyzer();
    }

    //파일 데이터 로딩.
    public void DataLoad(List list){
        nounData=list;
        wordFrequencies =  frequencyAnalyzer.load(nounData);
    }

    //명사 뽑아오기.
    public String getWord(int i) {
        return wordFrequencies.get(i).getWord();
    }

    //빈도수 뽑아오기.
    public int getFrequency(int i){
        return wordFrequencies.get(i).getFrequency();
    }

    //데이터 갯수.
    public int getSize(){
        return wordFrequencies.size();
    }

}
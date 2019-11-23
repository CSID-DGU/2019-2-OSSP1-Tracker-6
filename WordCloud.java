package com.example.mytest;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;


public class WordCloud  {

    private int numberOfWordsToSelect = 10;

    Vector<Word> words= new Vector<>();
    String myContents;


    //current wc 만들때 사용되는 생성자 (수정필요)
    public WordCloud(){
        String myContents=crawler();
        makeWord(myContents);
    }


    // 과거 wc 만들때 사용되는 생성자
    public WordCloud(int dummy) {
        //DB접속
    }

    public String crawler(){
        Crawler crawler=new Crawler();
        String titleCollection="";
        Tokenizer tokenizer = new Tokenizer();
        Analyzer analyzer = new Analyzer();
        List<String> nounData = new ArrayList<String>();

        String contents="";
        try {
            titleCollection = crawler.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tokenizer.setElements(titleCollection);
        tokenizer.tokenize();

        nounData = tokenizer.getStringList();
        analyzer.DataLoad(nounData);

        for(int i=0;i<numberOfWordsToSelect;i++){
            contents+=analyzer.getWord(i)+" " +analyzer.getFrequency(i)+" "+crawler.getTime()+" ";
        }
        return contents;
    }

    public void makeWord(String contents){
        String[] array= contents.split(" ");
        int length= array.length;
        String name="";
        int freq=0;
        String date="";

        for(int i=0;i<length;++i){
            if(i%3==0){
                name=array[i];
            }
            else if(i%3==1){
                freq=Integer.parseInt(array[i]);
            }
            else{
                date=array[i];
                //워드 객체화 후 vector push_back
                words.add(new Word(name,freq,date));
            }
        }
    }

    public int length(){
        return words.size();
    }
}

package com.example.mytest;

import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;
import com.twitter.penguin.korean.util.KoreanPos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import scala.collection.Iterator;
import scala.collection.Seq;

public class Tokenizer {
    private String crawlingData;
    private List<String> stringList;

    public Tokenizer() {
        stringList = new ArrayList<String>();
    }

    //데이터 저장.
    public void setElements(String crawlingData) {
        this.crawlingData = crawlingData;
    }

    //
    public void tokenize() {
        CharSequence normalized = TwitterKoreanProcessorJava.normalize(this.crawlingData);
        Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);

        Iterator<KoreanTokenizer.KoreanToken> tokenized = tokens.iterator();
        KoreanTokenizer.KoreanToken token;
        int endFlag = 0;

        //Noun(명사) 뽑아내는 과정.
        while (true) {
            do {
                if (!tokenized.hasNext()) {
                    endFlag = 1;
                    break;
                }
                token = tokenized.next();
                if (token.pos().toString() == "Noun") {
                    //추출한 파일을 리스트에 저장.
                    stringList.add(token.text());
                    //System.out.println(token.text());
                }
            } while (!false && token.pos() == KoreanPos.Space());
            if (endFlag == 1)
                break;
        }


    }

    public List getStringList() {
        return stringList;
    }
}

package com.stone.moviechannel.utils;


import com.stone.moviechannel.data.Series;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AppUtil {
    public static List<Series> getList(String str){
        List<Series> list=new ArrayList<>();
        StringTokenizer tokenizer=new StringTokenizer(str,",");
        list.add(new Series("Select Episode",""));
        for (int i=1;tokenizer.hasMoreTokens();i++){
            list.add(new Series("Episode  "+i,tokenizer.nextToken()));
        }
        return list;
    }
}

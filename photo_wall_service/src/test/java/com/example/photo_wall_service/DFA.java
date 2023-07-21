package com.example.photo_wall_service;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import static com.example.photo_wall_service.service.impl.CommentServiceImpl.SENSITIVE;
import static com.example.photo_wall_service.util.SensitiveWordUtil.*;

public class DFA {
    public static void main(String[] args) throws IOException {
        Long t1, t2;
        t1 = System.nanoTime();
        String path = Thread.currentThread().getContextClassLoader().getResource(SENSITIVE).getPath();
        FileInputStream fis = new FileInputStream(path);
        //设置字符编码格式
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        //读取文件
        BufferedReader br = new BufferedReader(isr);
        //采用set   对text 文件内容进行去重
        Set set = new HashSet();
        String tempStr;
        //一行行读取文件
        while ((tempStr = br.readLine()) != null) {
            //把每一行读取出来的数据放到set里面    我用来读取 分词需要的词
            set.add(tempStr);
        }
        //初始化敏感词库
        initSensitiveWordMap(set);
        String string = "毛泽东建立新中国，全中国人民站起来了！回民吃猪肉了，中央政治局说大家晚上也可以去夜总会happy";
        System.out.println(string);
        //替换语句中的敏感词
        String filterStr = replaceSensitiveWord(string, MinMatchTYpe);
        System.out.println(filterStr);
        t2 = System.nanoTime();
        System.out.println((t2 - t1) / 1000000 + "毫秒");
    }
}

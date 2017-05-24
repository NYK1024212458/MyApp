package com.itheima.xfyuntest01.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class ResultInfo {

    /**
     * moreResults : [{"rc":0,"answer":{"type":"T","text":"拼音[nǐ hǎo] 打招呼的敬语，作为一般对话的开场白。这也是个最基本的英语单词。"},"service":"baike","text":"你好。","operation":"ANSWER"}]
     * rc : 0
     * operation : ANSWER
     * service : openQA
     * answer : {"type":"T","text":"百拓益通科技有限公司"}
     * text : 你好。
     */

    public int rc;
    public String operation;
    public String service;
    public AnswerBean answer;
    public String text;
    public List<MoreResultsBean> moreResults;

    public static class AnswerBean {
        /**
         * type : T
         * text : 百拓益通科技有限公司
         */

        public String type;
        public String text;
    }

    public static class MoreResultsBean {
        /**
         * rc : 0
         * answer : {"type":"T","text":"拼音[nǐ hǎo] 打招呼的敬语，作为一般对话的开场白。这也是个最基本的英语单词。"}
         * service : baike
         * text : 你好。
         * operation : ANSWER
         */

        public int rc;
        public AnswerBeanX answer;
        public String service;
        public String text;
        public String operation;

        public static class AnswerBeanX {
            /**
             * type : T
             * text : 拼音[nǐ hǎo] 打招呼的敬语，作为一般对话的开场白。这也是个最基本的英语单词。
             */

            public String type;
            public String text;
        }
    }
}

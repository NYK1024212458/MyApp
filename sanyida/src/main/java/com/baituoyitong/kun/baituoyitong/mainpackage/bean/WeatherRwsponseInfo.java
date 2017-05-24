package com.baituoyitong.kun.baituoyitong.mainpackage.bean;

import java.io.Serializable;
import java.util.List;

/**
 * com.baituoyitong.kun.sanyida.bean
 * <p>
 * Created by ${kun} on 2017/4/12.
 */

public class WeatherRwsponseInfo implements Serializable{

    /**
     * webPage : {"header":"","url":"http://kcbj.openspeech.cn/service/iss?wuuid=0c80a987f908669033de94c01b67b766&ver=2.0&method=webPage&uuid=bfbf0d58bd47d89717f24d0deb89c6d7query"}
     * semantic : {"slots":{"location":{"cityAddr":"天津","city":"天津市","type":"LOC_BASIC"},"datetime":{"date":"CURRENT_DAY","type":"DT_BASIC"}}}
     * rc : 0
     * operation : QUERY
     * service : weather
     * data : {"result":[{"airQuality":"轻微污染","sourceName":"中国天气网","date":"2017-04-12","lastUpdateTime":"2017-04-12 07:23:18","dateLong":1491926400,"pm25":"85","city":"天津","humidity":"53%","windLevel":0,"weather":"晴转多云","tempRange":"12℃~24℃","wind":"西南风微风"},{"dateLong":1492012800,"sourceName":"中国天气网","date":"2017-04-13","lastUpdateTime":"2017-04-12 07:23:18","city":"天津","windLevel":0,"weather":"小雨转多云","tempRange":"12℃~22℃","wind":"西南风微风"},{"dateLong":1492099200,"sourceName":"中国天气网","date":"2017-04-14","lastUpdateTime":"2017-04-12 07:23:18","city":"天津","windLevel":0,"weather":"多云","tempRange":"14℃~27℃","wind":"西南风微风"},{"dateLong":1492185600,"sourceName":"中国天气网","date":"2017-04-15","lastUpdateTime":"2017-04-12 07:23:18","city":"天津","windLevel":0,"weather":"晴","tempRange":"13℃~27℃","wind":"北风微风"},{"dateLong":1492272000,"sourceName":"中国天气网","date":"2017-04-16","lastUpdateTime":"2017-04-12 07:23:18","city":"天津","windLevel":0,"weather":"多云","tempRange":"14℃~25℃","wind":"东南风微风"},{"dateLong":1492358400,"sourceName":"中国天气网","date":"2017-04-17","lastUpdateTime":"2017-04-12 07:23:18","city":"天津","windLevel":0,"weather":"阴","tempRange":"12℃~22℃","wind":"南风微风"},{"dateLong":1492444800,"sourceName":"中国天气网","date":"2017-04-18","lastUpdateTime":"2017-04-12 07:23:18","city":"天津","windLevel":0,"weather":"多云转阴","tempRange":"13℃~24℃","wind":"西南风微风"}]}
     * text : 天津天气怎么样?
     */

    /**
     * 上下文信息，客户端需要将该字段结果传给下一次请求的history字段
     *
     */
    private String history;

    //该字段提供了结果内容的HTML5页面，客户端可以无需解析处理直接展现
    public WebPageBean webPage;

    /**
     * 语义结构化表示，各服务自定义
     */
    public SemanticBean semantic;

    //应答码   0  标识成功
    public int rc;

    //哪一个服务   天气和是旅游
    public String operation;

    //服务的全局唯一名称
    public String service;

    //数据结构化表示
    public DataBean data;


    //回答问题的答案
    public AnswerBean answer;

    //这个是用户的输入的信息
    public String text;

    //更多的问题回答
    public List<MoreResultsBean> moreResults;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public WebPageBean getWebPage() {
        return webPage;
    }

    public void setWebPage(WebPageBean webPage) {
        this.webPage = webPage;
    }

    public SemanticBean getSemantic() {
        return semantic;
    }

    public void setSemantic(SemanticBean semantic) {
        this.semantic = semantic;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public AnswerBean getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerBean answer) {
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MoreResultsBean> getMoreResults() {
        return moreResults;
    }

    public void setMoreResults(List<MoreResultsBean> moreResults) {
        this.moreResults = moreResults;
    }

    public static class WebPageBean {
        /**
         * header :
         * url : http://kcbj.openspeech.cn/service/iss?wuuid=0c80a987f908669033de94c01b67b766&ver=2.0&method=webPage&uuid=bfbf0d58bd47d89717f24d0deb89c6d7query
         */

        public String header;
        public String url;
    }

    public static class SemanticBean {
        /**
         * slots : {"location":{"cityAddr":"天津","city":"天津市","type":"LOC_BASIC"},"datetime":{"date":"CURRENT_DAY","type":"DT_BASIC"}}
         */

        public SlotsBean slots;

        public static class SlotsBean {
            /**
             * location : {"cityAddr":"天津","city":"天津市","type":"LOC_BASIC"}
             * datetime : {"date":"CURRENT_DAY","type":"DT_BASIC"}
             */

            public LocationBean location;
            public DatetimeBean datetime;

            /**
             * code : 10086
             * name : 中国移动通信客服
             */

            public String code;
            public String name;

            /**
             * content : 我明天去找你
             * name : 张三
             * messageType : voice_message
             */

            public String content;
            public String messageType;

            /**
             * artist : 刘德华
             * song : 忘情水
             */

            public String artist;
            public String song;

            public static class LocationBean {
                /**
                 * cityAddr : 天津
                 * city : 天津市
                 * type : LOC_BASIC
                 */

                public String cityAddr;
                public String city;
                public String type;
            }

            public static class DatetimeBean {
                /**
                 * date : CURRENT_DAY
                 * type : DT_BASIC
                 */

                public String date;
                public String type;
            }
        }
    }

    public static class DataBean {
        public List<ResultBean> result;

        public static class ResultBean {
            /**
             * airQuality : 轻微污染
             * sourceName : 中国天气网
             * date : 2017-04-12
             * lastUpdateTime : 2017-04-12 07:23:18
             * dateLong : 1491926400
             * pm25 : 85
             * city : 天津
             * humidity : 53%
             * windLevel : 0
             * weather : 晴转多云
             * tempRange : 12℃~24℃
             * wind : 西南风微风
             */

            public String airQuality;

            public String date;
            public String lastUpdateTime;
            public int dateLong;

            public String city;
            public String humidity;
            public int windLevel;
            public String weather;
            public String tempRange;
            public String wind;


            /**
             * aqi : 103
             * area : 天津
             * pm25 : 72
             * positionName : 全局监测
             * publishDateTime : 2017-04-12 12:00:00
             * publishDateTimeLong : 1491969600
             * quality : 轻度污染
             * sourceName : PM2.5监测网
             * subArea :
             */
            public int aqi;
            public String area;
            public int pm25;
            public String positionName;
            public String publishDateTime;
            public int publishDateTimeLong;
            public String quality;
            public String sourceName;
            public String subArea;


            /**
             * accessory : 姜片:适量;老抽:适量; 蒜瓣:适量;八角:适量;花椒:适量;冰糖:适量; 黄酒:适量;盐:适量
             * cuisine :
             * imgUrl : http://imgs.douguo.com/upload/caiku/1/5/4/15d696f56067274eef4faf9b3fe35c64.jpg
             * ingredient : 五花肉:适量
             * source : 豆果网
             * url : http://m.douguo.com/cookbook/89576.html?f=xunfei
             */

            public String accessory;
            public String cuisine;
            public String imgUrl;
            public String ingredient;
            public String source;
            public String url;




            /**
             * singer : 刘德华
             * sourceName : 自产音乐
             * name : 忘情水
             * downloadUrl : http://file.kuyinyun.com/group1/M00/52/54/rBBGdFO10gGAV2YSABeMNNnn7Sc042.mp3
             */

            public String singer;

            public String name;
            public String downloadUrl;


        }
    }
    public static class AnswerBean {
        /**
         * type : T
         * text : 大家好,才是真的好!
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

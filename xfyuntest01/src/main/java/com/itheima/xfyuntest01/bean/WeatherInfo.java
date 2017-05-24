package com.itheima.xfyuntest01.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class WeatherInfo {

    /**
     * webPage : {"header":"","url":"http://kcgz.openspeech.cn/service/iss?wuuid=a4f1482280d0f3efc4d2940e23cc14b6&ver=2.0&method=webPage&uuid=c8cca40075e5aac42cdce2bd27ff1659query"}
     * semantic : {"slots":{"location":{"cityAddr":"天津","city":"天津市","type":"LOC_BASIC"},"datetime":{"date":"CURRENT_DAY","type":"DT_BASIC"}}}
     * rc : 0
     * operation : QUERY
     * service : weather
     * data : {"result":[{"airQuality":"良","sourceName":"中国天气网","date":"2017-04-05","lastUpdateTime":"2017-04-05 16:03:24","dateLong":1491321600,"pm25":"61","city":"天津","humidity":"63%","windLevel":0,"weather":"多云","tempRange":"11℃~18℃","wind":"东南风微风"},{"dateLong":1491408000,"sourceName":"中国天气网","date":"2017-04-06","lastUpdateTime":"2017-04-05 16:03:24","city":"天津","windLevel":0,"weather":"多云","tempRange":"15℃~24℃","wind":"南风微风"},{"dateLong":1491494400,"sourceName":"中国天气网","date":"2017-04-07","lastUpdateTime":"2017-04-05 16:03:24","city":"天津","windLevel":0,"weather":"阴","tempRange":"13℃~25℃","wind":"南风微风"},{"dateLong":1491580800,"sourceName":"中国天气网","date":"2017-04-08","lastUpdateTime":"2017-04-05 16:03:24","city":"天津","windLevel":2,"weather":"多云转晴","tempRange":"9℃~17℃","wind":"东北风4-5级"},{"dateLong":1491667200,"sourceName":"中国天气网","date":"2017-04-09","lastUpdateTime":"2017-04-05 16:03:24","city":"天津","windLevel":0,"weather":"晴转多云","tempRange":"11℃~19℃","wind":"西南风微风"},{"dateLong":1491753600,"sourceName":"中国天气网","date":"2017-04-10","lastUpdateTime":"2017-04-05 16:03:24","city":"天津","windLevel":0,"weather":"阴","tempRange":"13℃~22℃","wind":"东南风微风"},{"dateLong":1491840000,"sourceName":"中国天气网","date":"2017-04-11","lastUpdateTime":"2017-04-05 16:03:24","city":"天津","windLevel":0,"weather":"晴转小雨","tempRange":"9℃~18℃","wind":"西南风微风"}]}
     * text : 天津天气怎么样?
     */

    public WebPageBean webPage;
    public SemanticBean semantic;
    public int rc;
    public String operation;
    public String service;
    public DataBean data;
    public String text;

    public static class WebPageBean {
        /**
         * header :
         * url : http://kcgz.openspeech.cn/service/iss?wuuid=a4f1482280d0f3efc4d2940e23cc14b6&ver=2.0&method=webPage&uuid=c8cca40075e5aac42cdce2bd27ff1659query
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
             * airQuality : 良
             * sourceName : 中国天气网
             * date : 2017-04-05
             * lastUpdateTime : 2017-04-05 16:03:24
             * dateLong : 1491321600
             * pm25 : 61
             * city : 天津
             * humidity : 63%
             * windLevel : 0
             * weather : 多云
             * tempRange : 11℃~18℃
             * wind : 东南风微风
             */

            public String airQuality;
            public String sourceName;
            public String date;
            public String lastUpdateTime;
            public int dateLong;
            public String pm25;
            public String city;
            public String humidity;
            public int windLevel;
            public String weather;
            public String tempRange;
            public String wind;
        }
    }
}

package com.kunstudy.myap;

import java.util.List;

/**
 * com.kunstudy.myap
 * <p>
 * Created by ${kun} on 2017/4/27.
 */

public class Call {

    /**
     * webPage : {"header":"","url":"http://kcbj.openspeech.cn/service/iss?wuuid=e96a12958dd40c20eb9c35ac87f22129&ver=2.0&method=webPage&uuid=4ed0eba20e204aaed519bb2c9ba72c9cquery"}
     * semantic : {"slots":{"artist":"刘德华","song":"忘情水"}}
     * rc : 0
     * operation : PLAY
     * service : music
     * data : {"result":[{"singer":"刘德华","sourceName":"自产音乐","name":"忘情水","downloadUrl":"http://file.kuyinyun.com/group1/M00/52/54/rBBGdFO10gGAV2YSABeMNNnn7Sc042.mp3"},{"singer":"刘德华","sourceName":"自产音乐","name":"忘情水","downloadUrl":"http://file.diyring.cc/UserRingWorksFile/0/50286090.mp3"},{"singer":"刘德华","sourceName":"自产音乐","name":"忘情水","downloadUrl":"http://file.diyring.cc/UserRingWorksFile/0/50288292.mp3"},{"singer":"刘德华","sourceName":"自产音乐","name":"忘情水","downloadUrl":"http://file.kuyinyun.com/group1/M00/93/7F/rBBGdFPX2iqACqkDABePVqFGeuc579.mp3"}]}
     * text : 播放刘德华的忘情水
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
         * url : http://kcbj.openspeech.cn/service/iss?wuuid=e96a12958dd40c20eb9c35ac87f22129&ver=2.0&method=webPage&uuid=4ed0eba20e204aaed519bb2c9ba72c9cquery
         */

        public String header;
        public String url;
    }

    public static class SemanticBean {
        /**
         * slots : {"artist":"刘德华","song":"忘情水"}
         */

        public SlotsBean slots;

        public static class SlotsBean {
            /**
             * artist : 刘德华
             * song : 忘情水
             */

            public String artist;
            public String song;
        }
    }

    public static class DataBean {
        public List<ResultBean> result;

        public static class ResultBean {
            /**
             * singer : 刘德华
             * sourceName : 自产音乐
             * name : 忘情水
             * downloadUrl : http://file.kuyinyun.com/group1/M00/52/54/rBBGdFO10gGAV2YSABeMNNnn7Sc042.mp3
             */

            public String singer;
            public String sourceName;
            public String name;
            public String downloadUrl;
        }
    }
}

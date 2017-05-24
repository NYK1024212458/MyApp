package com.baituoyitong.kun.baituoyitong.mainpackage.bean;

import java.util.List;

/**
 *
 */
public class VoiceInfo {
    public int bg;
    public int ed;
    public boolean ls;
    public int sn;
    public List<WS> ws;

    public class WS{
        public int bg;
        public List<CW> cw;
    }

    public class CW{
        public String sc;
        public String w;
    }
}

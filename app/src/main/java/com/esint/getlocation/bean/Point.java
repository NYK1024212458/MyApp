package com.esint.getlocation.bean;

/**
 * Created by esint12 on 2017/6/30.
 */

public class Point {
    private double lat,lng;
    private String tm;

    public Point() {

    }

    public Point(double lat, double lng, String tm) {
        this.lat = lat;
        this.lng = lng;
        this.tm = tm;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }
}

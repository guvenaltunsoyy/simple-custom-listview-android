package com.example.iot;

public class Container {
    private String id;
    private double lat;
    private double lon;
    private int order;
    private String address;
    private boolean isFilled;

    public Container(String id, double lat, double lon, int order, String address, boolean isFilled) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.order = order;
        this.address = address;
        this.isFilled = isFilled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }
}

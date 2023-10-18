package com.example.trainticketing.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainModel {
    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("arrivedTime")
    @Expose
    private String arrivedTime;

    @SerializedName("departureTime")
    @Expose
    private String departureTime;

    @SerializedName("priceForOneSeat")
    @Expose
    private int priceForOneSeat;

    @SerializedName("from")
    @Expose
    private String from;

    @SerializedName("to")
    @Expose
    private String to;

    @SerializedName("numberOfSeat ")
    @Expose
    private int numberOfSeat ;

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("date")
    @Expose
    private String date;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getPriceForOneSeat() {
        return priceForOneSeat;
    }

    public void setPriceForOneSeat(int priceForOneSeat) {
        this.priceForOneSeat = priceForOneSeat;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TrainModel{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", arrivedTime='" + arrivedTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", priceForOneSeat=" + priceForOneSeat +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", numberOfSeat=" + numberOfSeat +
                ", status=" + status +
                ", date='" + date + '\'' +
                '}';
    }
}

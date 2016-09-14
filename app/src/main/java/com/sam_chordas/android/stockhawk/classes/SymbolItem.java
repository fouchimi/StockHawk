package com.sam_chordas.android.stockhawk.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ousmane on 9/12/16.
 */
public class SymbolItem implements Parcelable, Comparable<SymbolItem> {

    private String symbol;
    private String date;
    private String open;
    private String high;
    private String  low;
    private String close;
    private String volume;
    private String adj_close;

    public SymbolItem(){

    }

    protected SymbolItem(Parcel in) {
        symbol = in.readString();
        date = in.readString();
        open = in.readString();
        high = in.readString();
        low = in.readString();
        close = in.readString();
        volume = in.readString();
        adj_close = in.readString();
    }

    public static final Creator<SymbolItem> CREATOR = new Creator<SymbolItem>() {
        @Override
        public SymbolItem createFromParcel(Parcel in) {
            return new SymbolItem(in);
        }

        @Override
        public SymbolItem[] newArray(int size) {
            return new SymbolItem[size];
        }
    };

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAdj_close() {
        return adj_close;
    }

    public void setAdj_close(String adj_close) {
        this.adj_close = adj_close;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(symbol);
        dest.writeString(date);
        dest.writeString(open);
        dest.writeString(high);
        dest.writeString(low);
        dest.writeString(close);
        dest.writeString(volume);
        dest.writeString(adj_close);
    }

    @Override
    public boolean equals(Object other){
        if(other == null) return false;
        if(this == other) return true;
        if(!(other instanceof SymbolItem)) return false;
        SymbolItem item = (SymbolItem) other;
        return convertDate(item.getDate()) == convertDate(this.getDate());
    }

    public long convertDate(String date){
        long milliseconds = 0L;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  milliseconds;
    }

    @Override
    public int compareTo(SymbolItem another) {
        if(convertDate(this.getDate()) < convertDate(another.getDate())) return -1;
        else if(convertDate(this.getDate()) >  convertDate(another.getDate())) return 1;
        else return 0;
    }
}

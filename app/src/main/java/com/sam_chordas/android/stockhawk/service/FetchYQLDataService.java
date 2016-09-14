package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.sam_chordas.android.stockhawk.classes.SymbolItem;
import com.sam_chordas.android.stockhawk.network.HttpManager;
import com.sam_chordas.android.stockhawk.parsers.SymbolParser;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ousmane on 9/12/16.
 */
public class FetchYQLDataService extends IntentService {

    private final static String TAG = FetchYQLDataService.class.getSimpleName();

    public final static String SYMBOL="symbol";
    public final static String START_DATE="startDate";
    public final static String END_DATE="endDate";
    public final static String SYMBOLS="symbols";
    public static final String EXTRAS_BUNDLE = "bundle";
    public ArrayList<SymbolItem> symbolItems = null;
    public static SymbolItem[] aux;

    public FetchYQLDataService(){
       super(FetchYQLDataService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String symbol = intent.getStringExtra(FetchYQLDataService.SYMBOL);
        String startDate = intent.getStringExtra(FetchYQLDataService.START_DATE);
        String endDate = intent.getStringExtra(FetchYQLDataService.END_DATE);
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22" + symbol.toUpperCase() + "%22%20and%20startDate%20%3D%20%22"+ startDate +"%22%20and%20endDate%20%3D%20%22"+ endDate +"%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&format=json";
        HttpManager httpManager = new HttpManager();
        String data = httpManager.getData(url);

        symbolItems = new SymbolParser().parseData(data);
        SymbolItem[] items = new SymbolItem[symbolItems.size()];
        for(int i=0; i < items.length; i++){
            items[i] = symbolItems.get(i);
        }
        sort(items);
        symbolItems = new ArrayList<>();
        for(int i=0; i < items.length; i++){
            symbolItems.add(items[i]);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(FetchYQLDataService.SYMBOLS, symbolItems);

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MyStocksActivity.ResultReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(FetchYQLDataService.EXTRAS_BUNDLE, bundle);
        sendBroadcast(broadcastIntent);
    }

    public static void sort(SymbolItem[] list){
        aux = new SymbolItem[list.length];
        sort(list, 0, list.length-1);
    }

    private static void sort(SymbolItem[] list, int lo, int hi){
        if(hi <= lo) return;;
        int mid = lo + (hi-lo)/2;
        sort(list, lo, mid);
        sort(list, mid+1, hi);
        merge(list, lo, mid, hi);
    }

    public static void merge(SymbolItem[] a, int lo, int mid, int hi){
        int i=lo, j = mid+1;
        for(int k=lo; k <= hi; k++)
            aux[k] = a[k];

        for(int k=lo; k <= hi; k++){
            if(i > mid) a[k] = aux[j++];
            else if(j > hi) a[k] = aux[i++];
            else if(less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    public static boolean less(SymbolItem p, SymbolItem q){
        return p.compareTo(q)  < 0;
    }
}

package com.sam_chordas.android.stockhawk.parsers;

import com.sam_chordas.android.stockhawk.classes.SymbolItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ousmane on 9/12/16.
 */
public class SymbolParser {
    private ArrayList<SymbolItem> list = new ArrayList<>();

    public ArrayList<SymbolItem> parseData(String json) {
        try {
            JSONObject rootObject = new JSONObject(json);
            JSONObject queryObject = rootObject.getJSONObject("query");
            JSONObject resultsObject = queryObject.getJSONObject("results");
            JSONArray quotes = resultsObject.getJSONArray("quote");
            for(int i=0; i < quotes.length(); i++) {
                JSONObject quote = quotes.getJSONObject(i);
                SymbolItem item = new SymbolItem();
                item.setSymbol(quote.getString("Symbol"));
                item.setDate(quote.getString("Date"));
                item.setOpen(quote.getString("Open"));
                item.setHigh(quote.getString("High"));
                item.setLow(quote.getString("Low"));
                item.setClose(quote.getString("Close"));
                item.setVolume(quote.getString("Volume"));
                item.setAdj_close(quote.getString("Adj_Close"));

                list.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}

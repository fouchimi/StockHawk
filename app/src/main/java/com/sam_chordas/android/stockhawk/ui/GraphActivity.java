package com.sam_chordas.android.stockhawk.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.classes.MyMarkerView;
import com.sam_chordas.android.stockhawk.classes.SymbolItem;
import com.sam_chordas.android.stockhawk.service.FetchYQLDataService;

import java.util.ArrayList;

import java.util.List;

public class GraphActivity extends Activity {
    private static final String TAG = GraphActivity.class.getSimpleName();
    List<Entry> entries = new ArrayList<Entry>();
    private LineChart chart;

    private ArrayList<SymbolItem> symbolItems = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        chart = (LineChart) findViewById(R.id.chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.YELLOW);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        YAxis left = chart.getAxisLeft();
        left.setDrawLabels(true);
        left.setDrawAxisLine(true);
        left.setDrawGridLines(false);
        left.setDrawZeroLine(true);
        left.setTextColor(Color.YELLOW);
        chart.getAxisRight().setEnabled(false);

        Bundle bundle = getIntent().getBundleExtra(FetchYQLDataService.EXTRAS_BUNDLE);
        if(bundle != null && bundle.containsKey(FetchYQLDataService.SYMBOLS)){
            symbolItems = bundle.getParcelableArrayList(FetchYQLDataService.SYMBOLS);
            if(symbolItems != null){
                long referenceValue = (symbolItems.get(0).convertDate(symbolItems.get(0).getDate()));
                for(SymbolItem data : symbolItems){
                    Entry newEntry = new Entry();
                    float value = (float)(data.convertDate(data.getDate())-referenceValue);
                    String floatValue = String.format("%.2f", value);
                    newEntry.setX(Float.parseFloat(floatValue)/1000000);
                    newEntry.setY(Float.parseFloat(data.getClose()));
                    entries.add(newEntry);
                    MyMarkerView myMarkerView= new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view, referenceValue);
                    chart.setMarkerView(myMarkerView);
                }
            }
            if(entries.size() > 0){
                LineDataSet dataSet = new LineDataSet(entries, symbolItems.get(0).getSymbol());
                LineData lineData = new LineData(dataSet);
                chart.setData(lineData);
                chart.invalidate();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Flushing GraphActivity", Toast.LENGTH_LONG).show();
        if(chart != null){
            chart.clearValues();
            chart.clear();
            chart = null;
            Toast.makeText(this, "Flushing GraphActivity" + chart, Toast.LENGTH_LONG).show();
        }
    }
}

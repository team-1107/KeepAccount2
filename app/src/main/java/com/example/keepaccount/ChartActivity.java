package com.example.keepaccount;

import static java.lang.Float.parseFloat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.slice.Slice;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ChartActivity extends AppCompatActivity {
    private PieChartView pieChart;
    private PieChartData pieData;
    private static final String TAG = "ChartActivity";
    private DBManager dbManager;
    private List<Item> listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        //返回
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initPie();
        initData();
    }
    private void initPie(){
        pieChart = (PieChartView)findViewById(R.id.pieChart);
    }
    private void initData(){
        getData();
    }
    private void getData(){
        dbManager = new DBManager(getApplicationContext());
        List<SliceValue> values = new ArrayList<SliceValue>();
        listItem = dbManager.listByTag();
        for(Item item : listItem){
            SliceValue slice = new SliceValue(parseFloat(item.getMoney()));
            Log.i(TAG,item.getTag()+item.getMoney());
            values.add(slice);
        }

        pieData = new PieChartData(values);
        pieChart.setPieChartData(pieData);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        super.onOptionsItemSelected(item);
        return true;
    }

}
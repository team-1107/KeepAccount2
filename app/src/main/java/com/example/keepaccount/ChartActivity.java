package com.example.keepaccount;

import static java.lang.Float.parseFloat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ChartActivity extends AppCompatActivity {
    private PieChartView Chart;
    private static final String TAG = "ChartActivity";
    private DBManager dbManager;
    private ArrayList<Item> listItem;
    private TextView noData;
    private ListView chartList;
    private chartAdapter chartAdapter;
    private  ListView overviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        //返回
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Log.i(TAG,"show chart");
        listItem = getData();
        initPie();
        initChartList();
        initOverviewList();
    }
    private void initPie(){
        Chart = (PieChartView)findViewById(R.id.Chart);
        if(listItem.isEmpty()){
            Chart.setVisibility(View.GONE);
        }else{
            Chart.setVisibility(View.VISIBLE);
            setChart(listItem,Chart);
        }

    }
    private void setPie(PieChartData data,boolean label,boolean center){
        data.setHasLabels(label);
        data.setHasCenterCircle(center);
        data.setCenterText1FontSize(20);
        data.setHasLabelsOnlyForSelected(true);
    }

    private List<Integer> setColor(){
        final List<Integer> colorData = new ArrayList<>();
        colorData.add(Color.parseColor("#FFCC99"));
        colorData.add(Color.parseColor("#FF9999"));
        colorData.add(Color.parseColor("#FFCCCC"));
        colorData.add(Color.parseColor("#99CCCC"));
        colorData.add(Color.parseColor("#CCFFFF"));
        colorData.add(Color.parseColor("#CCCCFF"));
        colorData.add(Color.parseColor("#99CCFF"));
        colorData.add(Color.parseColor("#99CC66"));
        return colorData;
    }

    private ArrayList<Item> getData(){
        ArrayList<Item> list;
        dbManager = new DBManager(getApplicationContext());
        float sum=0;
        list = dbManager.listByType();
        List<Integer> Color = setColor();
        int i=0;
        for(Item item:list){
            sum+=parseFloat(item.getMoney());
        }
        for(Item item:list){
            double percent = parseFloat(item.getMoney())/sum;
            item.setPercent(String.format("%.2f",percent*100));
            item.setColor(Color.get(i));
            i++;
        }
        Log.i(TAG,"Get data"+list.size());
        return list;
    }
    private void setChart(ArrayList<Item> itemList, PieChartView pie){
        List<SliceValue> Values = new ArrayList<SliceValue>();
        List<Integer> Color = setColor();
        int i=0;
        for(Item item : itemList){
                SliceValue slice = new SliceValue(parseFloat(item.getMoney()),Color.get(i));
                i=i+1;
                slice.setLabel(item.getTag()+":"+item.getMoney());
                Log.i(TAG,item.getTag()+item.getMoney());
                Values.add(slice);
        }

        PieChartData Data = new PieChartData(Values);
        setPie(Data,true,true);
        pie.setPieChartData(Data);
    }

    private void initOverviewList(){
        overviewList=(ListView) findViewById(R.id.overview);
        ArrayAdapter overviewAdapter = new overviewAdapter(this,R.layout.overview_item,listItem);
        overviewList.setAdapter(overviewAdapter);
    }
    private void initChartList(){
        chartList = (ListView)findViewById(R.id.list_chart);
        noData = (TextView)findViewById(R.id.nodata_chart);
        setChartList(chartList,noData,chartAdapter,listItem);
    }
    private void setChartList(ListView list, TextView noData,chartAdapter adapter,ArrayList<Item> listItem){
        adapter = new chartAdapter(this,R.layout.chart_item,listItem);

        list.setAdapter(adapter);
        list.setEmptyView(noData);
        Log.i(TAG,"chart list size"+listItem.size());
        ViewGroup.LayoutParams params = chartList.getLayoutParams();
        params.height = 2000;
        chartList.setLayoutParams(params);
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
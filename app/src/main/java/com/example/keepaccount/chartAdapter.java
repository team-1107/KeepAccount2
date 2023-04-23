package com.example.keepaccount;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class chartAdapter extends ArrayAdapter {
    private static final String TAG = "MyChartAdapter";

    public chartAdapter(@NonNull Context context, int resource, ArrayList<Item> list) {
        super(context, resource,list);
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.chart_item, parent, false);
        }

        Item item = (Item) getItem(position);

        ImageView img = (ImageView) itemView.findViewById(R.id.tag_img_chart);
        TextView percent = (TextView) itemView.findViewById(R.id.percent_chart);
        TextView tag = (TextView) itemView.findViewById(R.id.tag_chart);
        ProgressBar Progress = (ProgressBar) itemView.findViewById(R.id.progressBar_chart);
        TextView total = (TextView) itemView.findViewById(R.id.total_chart);
        TextView bills  = (TextView) itemView.findViewById(R.id.bills_chart);

        percent.setText(item.getPercent()+"%");
        total.setText(item.getMoney());
        bills.setText(item.getCount()+" bills");
        String Tag = item.getTag();
        Progress.setProgress(Math.round(parseFloat(item.getPercent())) );
        img.setColorFilter(item.getColor());
        Progress.getIndeterminateDrawable().setColorFilter(item.getColor(),android.graphics.PorterDuff.Mode.MULTIPLY);
        switch (Tag){
            case "消费":{
                img.setImageResource(R.drawable.consume);

                break;
            }
            case "餐饮": {
                img.setImageResource(R.drawable.meal);
                break;
            }
            case "购物":{
                img.setImageResource(R.drawable.shopping);
                break;
            }
            case "交通":{
                img.setImageResource(R.drawable.transport);
                break;
            }
            case "工资":{
                img.setImageResource(R.drawable.salary);
                break;
            }
            case "兼职": {
                img.setImageResource(R.drawable.parttime);
                break;
            }
            case "理财":{
                img.setImageResource(R.drawable.financing);
                break;
            }
            case "红包":{
                img.setImageResource(R.drawable.hongbao);
                break;
            }
        }

        Log.i(TAG, "getView: tag:"+item.getTag());
        if(item.getTag() == null || item.getTag().length() == 0){
            tag.setVisibility(View.GONE);
        }else{
            String tagTemp = " " + item.getTag() + " ";
            tag.setText(tagTemp);
        }

        return itemView;
    }
}

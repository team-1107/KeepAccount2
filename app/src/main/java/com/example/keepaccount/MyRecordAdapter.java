package com.example.keepaccount;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyRecordAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdapter";
    private int mSelect;   //选中项
    public MyRecordAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> data) {
        super(context, resource,data);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.record_item_list,
                    parent,
                    false);
        }

        String recordType =(String) getItem(position);
        ImageView record_icon = (ImageView) itemView.findViewById(R.id.record_icon);
        TextView record_describe = (TextView) itemView.findViewById(R.id.record_describe);
        record_describe.setText(recordType);
        switch (recordType){
            case "消费":{
                record_icon.setImageResource(R.drawable.consume);
                break;
            }
            case "餐饮": {
                record_icon.setImageResource(R.drawable.meal);
                break;
            }
            case "购物":{
                record_icon.setImageResource(R.drawable.shopping);
                break;
            }
            case "交通":{
                record_icon.setImageResource(R.drawable.transport);
                break;
            }
            case "工资":{
                record_icon.setImageResource(R.drawable.salary);
                break;
            }
            case "兼职": {
                record_icon.setImageResource(R.drawable.parttime);
                break;
            }
            case "理财":{
                record_icon.setImageResource(R.drawable.financing);
                break;
            }
            case "红包":{
                record_icon.setImageResource(R.drawable.hongbao);
                break;
            }
        }

        //被点击
        if(mSelect == position) {
            Log.i(TAG, "getView-position: 第:   " + position + " 项被点击了");
            Log.i(TAG, "getView-position: 点击项:   " + recordType);
            record_icon.setBackgroundResource(R.color.tagSelected);
        }
        //未被点击的item
        else{
            record_icon.setBackgroundResource(R.color.tagUnSelected);
        }

        return  itemView;
    }
    //刷新方法
    public void changeSelected(int position) {
        if (position != mSelect) {
            mSelect = position;
            notifyDataSetChanged();
        }
    }

    //设置默认显示颜色
    public void defaultTagColor(){
        mSelect = -1;
    }
}


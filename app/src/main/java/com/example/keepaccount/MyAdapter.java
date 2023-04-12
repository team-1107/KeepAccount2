package com.example.keepaccount;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {

    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context, int resource, ArrayList<Item> list){
        super(context, resource, list);
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }


        Item item = (Item) getItem(position);

        TextView time = (TextView) itemView.findViewById(R.id.itemTime);
        TextView money = (TextView) itemView.findViewById(R.id.itemMoney);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);
        TextView tag = (TextView) itemView.findViewById(R.id.itemTag);

        time.setText(item.getTime());
        money.setText(item.getMoney());

        if(item.getDetail() == null || item.getDetail().length() == 0){
            detail.setVisibility(View.GONE);
        }else{
            detail.setText(item.getDetail());
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

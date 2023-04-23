package com.example.keepaccount;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class overviewAdapter extends ArrayAdapter {
    private static final String TAG = "overviewAdapter";

    public overviewAdapter(@NonNull Context context, int resource, ArrayList<Item> list) {
        super(context, resource,list);
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.overview_item, parent, false);
        }

        Item item = (Item) getItem(position);

        Button button = (Button)itemView.findViewById(R.id.overview_button);
        TextView text = (TextView)itemView.findViewById(R.id.overview_type);

        button.setBackgroundColor(item.getColor());
        text.setText(item.getTag()+" "+item.getPercent()+"%");

        Log.i(TAG, "getView: tag:"+item.getTag());
        if(item.getTag() == null || item.getTag().length() == 0){
            button.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
        }

        return itemView;
    }
}

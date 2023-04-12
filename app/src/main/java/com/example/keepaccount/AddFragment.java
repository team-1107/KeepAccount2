package com.example.keepaccount;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddFragment extends Fragment implements AdapterView.OnItemClickListener{

    EditText money_input, note;
    TextView income,outcome,cancel,finish,record_time;
    GridView GridList_outcome,GridList_income;
    ImageView timeEditImage;
    Button saveBtn;
    private static MyRecordAdapter adapter_income,adapter_outcome;
    String selectTag,time_set,record_money;

    private AddViewModel mViewModel;

    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_fragment, container, false);

        GridList_outcome = view.findViewById(R.id.GridList_outcome);
        GridList_income = view.findViewById(R.id.GridList_income);
        outcome = view.findViewById(R.id.out_come);
        income = view.findViewById(R.id.in_come);
        timeEditImage = view.findViewById(R.id.time_edit);
        saveBtn = view.findViewById(R.id.saveBtn);
        note = view.findViewById(R.id.note);

        record_time = view.findViewById(R.id.record_time);
        money_input = view.findViewById(R.id.input_money);
        money_input.setText("");
        note.setText("");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddViewModel.class);
        // TODO: Use the ViewModel
        GridList_income.setChoiceMode (ListView.CHOICE_MODE_SINGLE);
        GridList_outcome.setChoiceMode (ListView.CHOICE_MODE_SINGLE);
        selectTag = "";

        //初始化界面
        outcome.setTextColor(getResources().getColor(R.color.typeSelected));
        outcome.setBackgroundResource(R.color.white);
        GridList_income.setVisibility(View.GONE);
        GridList_outcome.setVisibility(View.VISIBLE);
        GridList_outcome.setContentDescription("show");
        GridList_income.setContentDescription("hide");

        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        Date date= c.getTime();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        time_set = formatter.format(date);
        record_time.setText(time_set);

        //生成GridView
        ArrayList<String> income_record = new ArrayList<String>();
        income_record.addAll(Arrays.asList("工资","兼职","理财","红包"));
        ArrayList<String> outcome_record = new ArrayList<String>();
        outcome_record.addAll(Arrays.asList("消费","餐饮","购物","交通"));

        adapter_income = new MyRecordAdapter(getActivity(),
                R.layout.record_item_list,
                income_record);
        adapter_income.defaultTagColor();
        GridList_income.setAdapter(adapter_income);
        adapter_outcome = new MyRecordAdapter(getActivity(),
                R.layout.record_item_list,
                outcome_record);
        adapter_outcome.defaultTagColor();
        GridList_outcome.setAdapter(adapter_outcome);

        //绑定监听
        GridList_income.setOnItemClickListener(this);
        GridList_outcome.setOnItemClickListener(this);

        outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GridList_outcome.setContentDescription("show");
                GridList_income.setContentDescription("hide");
                outcome.setBackgroundResource(R.color.white);
                income.setBackgroundResource(R.color.typeLine);
                income.setTextColor(getResources().getColor(R.color.typeUnSelected));
                outcome.setTextColor(getResources().getColor(R.color.typeSelected));
                GridList_income.setVisibility(View.GONE);
                GridList_outcome.setVisibility(View.VISIBLE);
            }
        });

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GridList_outcome.setContentDescription("hide");
                GridList_income.setContentDescription("show");
                income.setBackgroundResource(R.color.white);
                outcome.setBackgroundResource(R.color.typeLine);
                outcome.setTextColor(getResources().getColor(R.color.typeUnSelected));
                income.setTextColor(getResources().getColor(R.color.typeSelected));
                GridList_outcome.setVisibility(View.GONE);
                GridList_income.setVisibility(View.VISIBLE);
            }
        });

        //跳转到修改时间界面
        timeEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent time_set = new Intent(getActivity(), TimeSet.class);
                startActivityForResult(time_set, 1);
            }
        });

        //点击 完成
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money_input.getText().toString().equals("")){
                    Toast.makeText(getActivity(),R.string.no_money_tip, Toast.LENGTH_SHORT).show();
                }else if (selectTag.equals("")){
                    Toast.makeText(getActivity(),R.string.no_tag_tip, Toast.LENGTH_SHORT).show();
                }else{
                    //获得数据，存入数据库
                    String type;
                    String[] timeStrArray = time_set.split(" ");
                    String dateStr = timeStrArray[0];
                    String timeStr = timeStrArray[1];
                    if (GridList_outcome.getContentDescription().equals("show")){
                        type = "outcome";
                    }
                    else {
                        type = "income";
                    }

                    Item item = new Item(dateStr, timeStr, money_input.getText().toString(), note.getText().toString(), selectTag, type);

                    DBManager dbManager = new DBManager(getActivity().getApplicationContext());
                    dbManager.add(item);
                    Log.i("db","新增记录");

                    //判断连续打卡
                    SharedPreferences sp = getContext().getSharedPreferences("KeepAccount", Activity.MODE_PRIVATE);
                    PreferenceManager.getDefaultSharedPreferences(getContext());
                    //无数据——当前日期
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
                    Date date = new Date(System.currentTimeMillis());
                    String todayStr = simpleDateFormat.format(date);
                    String lastBilling = sp.getString("lastBilling",todayStr);
                    Log.i(TAG, "onClick: sp中上次记账日期:"+lastBilling);

                    SharedPreferences.Editor editor= sp.edit();
                    try {
                        Date lastBilling_recorded = simpleDateFormat.parse(lastBilling);
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(lastBilling_recorded);
                        calendar.add(calendar.DATE,1); //把日期往后增加一天,整数往后推,负数往前移动
                        String yesterday = simpleDateFormat.format(calendar.getTime()); //获取昨天日期

                        int continuousCount = sp.getInt("continuousDays",0); //连续记账天数
                        if(lastBilling.equals(todayStr)){
                            if(continuousCount == 0){
                                Log.i(TAG, "onClick: 日期为今天，连续次数为0，首次记账");
                                editor.putInt("continuousDays",1);
                                editor.putString("lastBilling",todayStr);
                                editor.apply();
                            }else{
                                Log.i(TAG, "onClick: 今天已经记过账");
                            }
                        }else if(!(yesterday.equals(todayStr))){
                            Log.i(TAG, "onClick: 昨天没记账");
                            editor.putInt("continuousDays",1);
                            editor.putString("lastBilling",todayStr);
                            editor.apply();

                        }else{
                            Log.i(TAG, "onClick: 昨天记账了");
                            editor.putInt("continuousDays",++continuousCount);
                            editor.putString("lastBilling",todayStr);
                            editor.apply();
                            Log.i(TAG, "onClick: +1后连续打卡天数="+sp.getInt("continuousDays",0));
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    Log.i(TAG, "onClick: 新增记录，跳转首页");
                    getActivity().finish();
                }

            }
        });

    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        adapter_income.changeSelected(position);
        adapter_outcome.changeSelected(position);
        String selectTag_income,selectTag_outcome;
        selectTag_outcome = (String)adapter_outcome.getItem(position);
        selectTag_income = (String)adapter_income.getItem(position);
        if (GridList_outcome.getContentDescription().equals("show")){
            selectTag = selectTag_outcome;
        }
        else if (GridList_income.getContentDescription().equals("show")){
            selectTag = selectTag_income;
        }
        Log.i("Page", "setOnItemClickListener: tagStr:" + selectTag);

    }

//    //跳转到修改时间界面
//    public void time_editor(View btn){
//        Intent time_set = new Intent(getActivity(), TimeSet.class);
//        startActivityForResult(time_set, 1);
//    }

    //处理回传的时间
    public void onActivityResult(int requestcode, int resultCode, Intent data) {
        if(requestcode == 1 && resultCode == 2){
            time_set = data.getStringExtra("time_set");
            record_time.setText(time_set);
        }
        super.onActivityResult(requestcode, resultCode, data);
    }

}
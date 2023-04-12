package com.example.keepaccount;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeSet extends AppCompatActivity {

    public final String TAG = "ChangeBirthPage";

    // 定义5个记录当前时间的变量
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_set);

        DatePicker datePicker = (DatePicker)
                findViewById(R.id.datepicker);
        TimePicker timePicker = (TimePicker)
                findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);

        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);

        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener()
        {

            @Override
            public void onDateChanged(DatePicker arg0, int year, int month, int day)
            {
                TimeSet.this.year = year;
                TimeSet.this.month = month;
                TimeSet.this.day = day;
            }
        });
        // 为TimePicker指定监听器
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
        {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                TimeSet.this.hour = hourOfDay;
                TimeSet.this.minute = minute;
            }
        });

    }

    public void Change_Birth(View btn) throws ParseException {
        Log.i(TAG, "Change_time: ");
        String time_set = year+"-"+(month+1)+"-"+day+" "+hour+":"+minute;
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date=formatter.parse(time_set);
        time_set = formatter.format(date);
        Log.i(TAG, "Change_Birth: "+time_set);

        if (btn.getId() == R.id.Changed){
            Toast.makeText(TimeSet.this,"您选择的日期："+time_set, Toast.LENGTH_SHORT).show();
            Intent first = new Intent(this, MainActivity.class);

            //返回的日期格式
            first.putExtra("time_set",time_set);
            setResult(2,first);
            finish();
        }
        else if (btn.getId() == R.id.UnChanged){
            finish();
        }
    }


}
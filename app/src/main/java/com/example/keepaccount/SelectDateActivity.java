package com.example.keepaccount;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class SelectDateActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SELECT_DATE = 6;
    private static final String TAG = "SelectDateActivity";
    DatePicker selectDate;
    Button submitTimeBtn;

    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        selectDate = findViewById(R.id.selectDate);
        submitTimeBtn = findViewById(R.id.submitTimeBtn);

        //返回
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //使用intent接收当前选择日期时间
        Intent intent = getIntent();
        String currentDate = intent.getStringExtra("current_date");
        String[] date = currentDate.split("-");

        year = Integer.valueOf(date[0]);
        month = Integer.valueOf(date[1]);
        day = Integer.valueOf(date[2]);

        //对datepicker初始化
        selectDate.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        selectDate.init(year, month-1, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
            }
        });

        //点击保存
        submitTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前选择日期
                year = selectDate.getYear();
                month = selectDate.getMonth()+1;
                day = selectDate.getDayOfMonth();
                String sdate = year + "-" + formatDateDouble(month) + "-" + formatDateDouble(day);

                //intent——将time从activity传到mainActivity的fragment中
                Intent submitDate = new Intent(SelectDateActivity.this, MainActivity.class);
                submitDate.putExtra("select_date", sdate);
                Log.i(TAG, "onClick: select_date:" + sdate);
                setResult(RESULT_CODE_SELECT_DATE, submitDate);
                finish();
            }
        });
    }

    //当日期中某位为10以内，将其显示为0x
    public String formatDateDouble(int i){
        String str = "";
        if(i < 10)
            str = "0" + i;
        else
            str = Integer.toString(i);
        return str;
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

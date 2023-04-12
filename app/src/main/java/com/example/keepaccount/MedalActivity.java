package com.example.keepaccount;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.widget.TextView;

public class MedalActivity extends AppCompatActivity {

    public static final int level0 = 0;
    public static final int level1 = 1;
    public static final int level2 = 7;
    public static final int level3 = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medal);

        //获取4个判断徽章的值
        SharedPreferences sp = getSharedPreferences("KeepAccount", Activity.MODE_PRIVATE);

//        //test
//        SharedPreferences.Editor editor1 = sp.edit();
//        editor1.putInt("continuousDays",7);
//        editor1.apply();

//        int saveGoalAccount = sp.getInt("saveGoalAccount",0);
//        int spendLimitAccount = sp.getInt("spendLimitAccount",0);
//        int revenueGoalAccount = sp.getInt("revenueGoalAccount",0);
        int continuousDays = sp.getInt("continuousDays",0);

        int saveGoalAccount =1;
        int spendLimitAccount = 7;
        int revenueGoalAccount = 30;

        //返回
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //灰色滤镜
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);//饱和度 0灰色 100过度彩色，50正常
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);//image.setColorFilter(filter);

        //1
        TextView badge1_1 = (TextView) findViewById(R.id.badge1_1);
        Drawable drawable1_1=getResources().getDrawable(R.drawable.save1);
        drawable1_1.setBounds(0,0,200,200);

        TextView badge1_2 = (TextView) findViewById(R.id.badge1_2);
        Drawable drawable1_2=getResources().getDrawable(R.drawable.save2);
        drawable1_2.setBounds(0,0,200,200);

        TextView badge1_3 = (TextView) findViewById(R.id.badge1_3);
        Drawable drawable1_3=getResources().getDrawable(R.drawable.save3);
        drawable1_3.setBounds(0,0,200,200);

        if(saveGoalAccount == level0) {
            drawable1_1.setColorFilter(filter);
            badge1_1.setBackground(getResources().getDrawable(R.drawable.round));

            drawable1_2.setColorFilter(filter);
            badge1_2.setBackground(getResources().getDrawable(R.drawable.round));

            drawable1_3.setColorFilter(filter);
            badge1_3.setBackground(getResources().getDrawable(R.drawable.round));
        }else if(saveGoalAccount >=level1 && saveGoalAccount < level2){
            drawable1_2.setColorFilter(filter);
            badge1_2.setBackground(getResources().getDrawable(R.drawable.round));

            drawable1_3.setColorFilter(filter);
            badge1_3.setBackground(getResources().getDrawable(R.drawable.round));
        }else if(saveGoalAccount >=level2 && saveGoalAccount < level3){
            drawable1_3.setColorFilter(filter);
            badge1_3.setBackground(getResources().getDrawable(R.drawable.round));
        }
        badge1_1.setText(level1+"/"+level1);
        badge1_2.setText(saveGoalAccount+"/"+level2);
        badge1_3.setText(saveGoalAccount+"/"+level3);
        badge1_1.setCompoundDrawables(null,drawable1_1,null,null);
        badge1_2.setCompoundDrawables(null,drawable1_2,null,null);
        badge1_3.setCompoundDrawables(null,drawable1_3,null,null);

        //2
        TextView badge2_1 = (TextView) findViewById(R.id.badge2_1);
        Drawable drawable2_1=getResources().getDrawable(R.drawable.spend1);
        drawable2_1.setBounds(0,0,200,200);

        TextView badge2_2 = (TextView) findViewById(R.id.badge2_2);
        Drawable drawable2_2=getResources().getDrawable(R.drawable.spend2);
        drawable2_2.setBounds(0,0,200,200);

        TextView badge2_3 = (TextView) findViewById(R.id.badge2_3);
        Drawable drawable2_3=getResources().getDrawable(R.drawable.spend3);
        drawable2_3.setBounds(0,0,200,200);

        if(spendLimitAccount == level0) {
            drawable2_1.setColorFilter(filter);
            badge2_1.setBackground(getResources().getDrawable(R.drawable.round));

            drawable2_2.setColorFilter(filter);
            badge2_2.setBackground(getResources().getDrawable(R.drawable.round));

            drawable2_3.setColorFilter(filter);
            badge2_3.setBackground(getResources().getDrawable(R.drawable.round));
        }else if(spendLimitAccount >=level1 && spendLimitAccount < level2){
            drawable2_2.setColorFilter(filter);
            badge2_2.setBackground(getResources().getDrawable(R.drawable.round));

            drawable2_3.setColorFilter(filter);
            badge2_3.setBackground(getResources().getDrawable(R.drawable.round));
        }else if(spendLimitAccount >=level2 && spendLimitAccount < level3){
            drawable2_3.setColorFilter(filter);
            badge2_3.setBackground(getResources().getDrawable(R.drawable.round));
        }
        badge2_1.setText(level1+"/"+level1);
        badge2_2.setText(level2+"/"+level2);
        badge2_3.setText(spendLimitAccount+"/"+level3);
        badge2_1.setCompoundDrawables(null,drawable2_1,null,null);
        badge2_2.setCompoundDrawables(null,drawable2_2,null,null);
        badge2_3.setCompoundDrawables(null,drawable2_3,null,null);

        //3
        TextView badge3_1 = (TextView) findViewById(R.id.badge3_1);
        Drawable drawable3_1=getResources().getDrawable(R.drawable.revenue1);
        drawable3_1.setBounds(0,0,200,200);

        TextView badge3_2 = (TextView) findViewById(R.id.badge3_2);
        Drawable drawable3_2=getResources().getDrawable(R.drawable.revenue2);
        drawable3_2.setBounds(0,0,200,200);

        TextView badge3_3 = (TextView) findViewById(R.id.badge3_3);
        Drawable drawable3_3=getResources().getDrawable(R.drawable.revenue3);
        drawable3_3.setBounds(0,0,200,200);

        if(revenueGoalAccount == level0) {
            drawable3_1.setColorFilter(filter);
            badge3_1.setBackground(getResources().getDrawable(R.drawable.round));

            drawable3_2.setColorFilter(filter);
            badge3_2.setBackground(getResources().getDrawable(R.drawable.round));

            drawable3_3.setColorFilter(filter);
            badge3_3.setBackground(getResources().getDrawable(R.drawable.round));
        }else if(revenueGoalAccount >=level1 && revenueGoalAccount < level2){
            drawable3_2.setColorFilter(filter);
            badge3_2.setBackground(getResources().getDrawable(R.drawable.round));

            drawable3_3.setColorFilter(filter);
            badge3_3.setBackground(getResources().getDrawable(R.drawable.round));
        }else if(revenueGoalAccount >=level2 && revenueGoalAccount < level3){
            drawable3_3.setColorFilter(filter);
            badge3_3.setBackground(getResources().getDrawable(R.drawable.round));
        }
        badge3_1.setText(level1+"/"+level1);
        badge3_2.setText(level2+"/"+level2);
        badge3_3.setText(level3+"/"+level3);
        badge3_1.setCompoundDrawables(null,drawable3_1,null,null);
        badge3_2.setCompoundDrawables(null,drawable3_2,null,null);
        badge3_3.setCompoundDrawables(null,drawable3_3,null,null);

        //4
        TextView badge4_1 = (TextView) findViewById(R.id.badge4_1);
        Drawable drawable4_1=getResources().getDrawable(R.drawable.sign1);
        drawable4_1.setBounds(0,0,200,200);

        TextView badge4_2 = (TextView) findViewById(R.id.badge4_2);
        Drawable drawable4_2=getResources().getDrawable(R.drawable.sign2);
        drawable4_2.setBounds(0,0,200,200);

        TextView badge4_3 = (TextView) findViewById(R.id.badge4_3);
        Drawable drawable4_3=getResources().getDrawable(R.drawable.sign3);
        drawable4_3.setBounds(0,0,200,200);

        SharedPreferences.Editor editor = sp.edit();
        int signmedal = sp.getInt("medalAccount",0);
        switch (signmedal) {
            case level0 : {
                if (continuousDays == level0) {
                    drawable4_1.setColorFilter(filter);
                    badge4_1.setBackground(getResources().getDrawable(R.drawable.round));
                    badge4_1.setText(continuousDays+"/"+level1);
                    drawable4_2.setColorFilter(filter);
                    badge4_2.setBackground(getResources().getDrawable(R.drawable.round));
                    badge4_2.setText(continuousDays+"/"+level2);
                    drawable4_3.setColorFilter(filter);
                    badge4_3.setBackground(getResources().getDrawable(R.drawable.round));
                    badge4_3.setText(continuousDays+"/"+level3);
                } else if (continuousDays >= level1 && continuousDays < level2) {
                    if(continuousDays == level1) {
                        badge4_1.setText(level1 + "/" + level1);
                    }
                    drawable4_2.setColorFilter(filter);
                    badge4_2.setBackground(getResources().getDrawable(R.drawable.round));
                    badge4_2.setText(continuousDays+"/"+level2);
                    drawable4_3.setColorFilter(filter);
                    badge4_3.setBackground(getResources().getDrawable(R.drawable.round));
                    badge4_3.setText(continuousDays+"/"+level3);
                    editor.putInt("medalAccount", level1);
                }
                break;
            }
            case level1:{
                if (continuousDays < level2) {
                    badge4_1.setText(level1+"/"+level1);
                    drawable4_2.setColorFilter(filter);
                    badge4_2.setBackground(getResources().getDrawable(R.drawable.round));
                    badge4_2.setText(continuousDays+"/"+level2);
                    drawable4_3.setColorFilter(filter);
                    badge4_3.setBackground(getResources().getDrawable(R.drawable.round));
                    badge4_3.setText(continuousDays+"/"+level3);
                } else if (continuousDays >= level2 && continuousDays < level3) {
                    if(continuousDays == level2){
                        badge4_1.setText(level1+"/"+level1);
                        badge4_2.setText(level2+"/"+level2);
                    }
                    drawable4_3.setColorFilter(filter);
                    badge4_3.setBackground(getResources().getDrawable(R.drawable.round));
                    badge4_3.setText(continuousDays+"/"+level3);
                    editor.putInt("medalAccount", level2);
                }
                break;
            }
            case level2:{
                if (continuousDays < level3) {
                    badge4_1.setText(level1+"/"+level1);
                    badge4_2.setText(level2+"/"+level2);
                    drawable4_3.setColorFilter(filter);
                    badge4_3.setBackground(getResources().getDrawable(R.drawable.round));
                    badge4_3.setText(continuousDays+"/"+level3);
                } else if (continuousDays >= level3) {
                    badge4_1.setText(level1+"/"+level1);
                    badge4_2.setText(level2+"/"+level2);
                    badge4_3.setText(level3+"/"+level3);
                    editor.putInt("medalAccount", level3);
                }
                break;
            }
            case level3:{
                badge4_1.setText(level1+"/"+level1);
                badge4_2.setText(level2+"/"+level2);
                badge4_3.setText(level3+"/"+level3);
                break;
            }

        }
        badge4_1.setCompoundDrawables(null,drawable4_1,null,null);
        badge4_2.setCompoundDrawables(null,drawable4_2,null,null);
        badge4_3.setCompoundDrawables(null,drawable4_3,null,null);
        editor.apply();
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
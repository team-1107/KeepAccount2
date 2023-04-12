package com.example.keepaccount;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserFragment extends Fragment implements View.OnClickListener {

    private UserViewModel mViewModel;
    private static final String TAG = "UserFragment";
    private TextView tvContinuousDays,tvLastBilling,tvMyBadges,tvMyGraphs;
    private EditText etInputRevenue,etInputSaving,etSpendingLimit;


    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_fragment,container,false);
        tvContinuousDays = view.findViewById(R.id.continuous_day);
        tvLastBilling = view.findViewById(R.id.last_billing_date);
        etInputRevenue = view.findViewById(R.id.input_revenue_goal);
        etInputSaving = view.findViewById(R.id.input_saving_goal);
        etSpendingLimit = view.findViewById(R.id.input_spending_limit);
        tvMyBadges = view.findViewById(R.id.badges_title);
        tvMyGraphs = view.findViewById(R.id.graph_title);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // TODO: Use the ViewModel

        tvMyBadges.setOnClickListener(this);
        tvMyGraphs.setOnClickListener(this);

        //读取SharedPreferences中记录的连续记账天数&上次记账日期
        SharedPreferences sp = getContext().getSharedPreferences("KeepAccount", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(getContext());
        int continuousDays = sp.getInt("continuousDays",0);
        tvContinuousDays.setText(String.valueOf(continuousDays));

        //无数据——当前日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String selectDateStr = simpleDateFormat.format(date);
        String lastBilling = sp.getString("lastBilling", selectDateStr);
        tvLastBilling.setText(lastBilling);


        SharedPreferences.Editor editor= sp.edit();
        //更新SharedPreferences中的三个目标

        etInputRevenue.setText(sp.getString("revenueGoal","0"));
        etSpendingLimit.setText(sp.getString("spendingLimit","0"));
        etInputSaving.setText(sp.getString("savingGoal","0"));
        //添加收入目标
        etInputRevenue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String revenueGoalStr = editable.toString();
                if(revenueGoalStr.length()>0) {
                    Log.i(TAG, "onActivityCreated: 添加收入目标");
                    editor.putString("revenueGoal",revenueGoalStr);
                    editor.apply();

                }

            }
        });

        //添加支出限制
        etSpendingLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String spendingLimitStr = editable.toString();
                if(spendingLimitStr.length()>0) {
                    Log.i(TAG, "onActivityCreated: 添加支出限制");

                    editor.putString("spendingLimit",spendingLimitStr);
                    editor.apply();
                }
            }
        });

        //添加攒钱目标
        etInputSaving.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String savingGoalStr = editable.toString();
                if(savingGoalStr.length()>0) {
                    Log.i(TAG, "onActivityCreated: 添加攒钱目标");
                    editor.putString("savingGoal",savingGoalStr);
                    editor.apply();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
//        Date date = new Date(System.currentTimeMillis());
//        String selectDateStr = simpleDateFormat.format(date);
        switch(view.getId()){
            case R.id.badges_title:
                intent = new Intent(getActivity(), MedalActivity.class);
                startActivity(intent);
                break;
            case R.id.graph_title:
                intent = new Intent(getContext(), ChartActivity.class);
                startActivity(intent);
                break;
        }
    }
}
package com.example.keepaccount;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IndexFragment extends Fragment implements AdapterView.OnItemLongClickListener{

    private IndexViewModel mViewModel;

    public static final int REQUEST_CODE_SELECT_DATE = 5;
    public static final int RESULT_CODE_SELECT_DATE = 6;
    private static final String TAG = "IndexFragment";
    private TextView todayDateDay, todayDateMonth, todayDateYear;
    private ListView todayList;
    private TextView noDataText;
    private LinearLayout dateLayout;
    private ArrayList<Item> listItems;
    private MyAdapter myAdapter;
    public String selectDateStr;
    DBManager dbManager;

    public static IndexFragment newInstance() {
        return new IndexFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fragment,container,false);
        todayDateDay = view.findViewById(R.id.todayDateDay);
        todayDateMonth = view.findViewById(R.id.todayDateMonth);
        todayDateYear = view.findViewById(R.id.todayDateYear);
        todayList = view.findViewById(R.id.todayList);
        dateLayout = view.findViewById(R.id.dateLayout);
        noDataText = view.findViewById(R.id.nodataText);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(IndexViewModel.class);
        // TODO: Use the ViewModel

        listItems = new ArrayList<>();
        for(int i=0; i<10; i++){
            Item item = new Item("2021-10-25", "12:00", "-10.00", "淮南牛肉汤", "餐饮", "支出");
            listItems.add(item);
        }


        //获取今日时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        selectDateStr = simpleDateFormat.format(date);


        searchAndAdapter(selectDateStr);

        //点击日期控件
        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开新窗口
                Intent selectDate = new Intent(getActivity(), SelectDateActivity.class);
                selectDate.putExtra("current_date", selectDateStr);
                startActivityForResult(selectDate, REQUEST_CODE_SELECT_DATE);

            }
        });

    }

    //将数字月份改为英文简写
    public String getMonthEn(String num){
        int index = Integer.parseInt(num);
        String monthEnStr = "Jan._Feb._Mar._Apr._May._Jun._Jul._Aug._Sep._Oct._Nov._Dec.";
        String[] monthEnList = monthEnStr.split("_");
        String monthEn = monthEnList[index - 1];
        return monthEn;
    }

    //设定textview，查找记录，定义适配器
    public void searchAndAdapter(String date){
        String[] dateStr = date.split("-");
        todayDateDay.setText(dateStr[2]);
        todayDateMonth.setText(getMonthEn(dateStr[1]));
        todayDateYear.setText(dateStr[0]);

        //在数据库中查找“今日”所有记录
        dbManager = new DBManager(getActivity().getApplicationContext());
        listItems = dbManager.findByDate(date);
        Log.i(TAG, "onActivityCreated: 查找“某日”记录，date="+date);

        //使用自定义适配器
        myAdapter = new MyAdapter(
                this.getActivity(),
                R.layout.list_item,
                listItems
        );
        todayList.setAdapter(myAdapter);

        //没有数据时显示
        todayList.setEmptyView(noDataText);
        //设置长按删除
        todayList.setOnItemLongClickListener(this);
    }

    //接收传过来的时间和tag
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //requestCode是哪个窗口带回来的, resultCode表明窗口带回来的哪个数据
        if(requestCode == REQUEST_CODE_SELECT_DATE && resultCode == RESULT_CODE_SELECT_DATE){
            //接收修改后时间
            MainActivity mainActivity = (MainActivity) getActivity();
            FragmentManager fragmentManager = mainActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.commit();

            selectDateStr = data.getStringExtra("select_date");
            searchAndAdapter(selectDateStr);
            Log.i(TAG, "onActivityResult: selectDateStr:"+selectDateStr);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    //长按删除
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id){
        Log.i(TAG, "onItemLongClick: 长按事件触发");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title)
                .setMessage(R.string.delete_dialog_message)
                .setPositiveButton(R.string.yes_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i(TAG, "onClick: 删除对话框事件处理");
                        Item deleteItem = (Item) todayList.getItemAtPosition(position);
                        myAdapter.remove(deleteItem);
                        //从数据库中删除该记录
                        dbManager.delete(deleteItem.getId());
                        Log.i(TAG, "onActivityCreated: DB删除记录");
                    }
                }).setNegativeButton(R.string.no_text, null);
        builder.create().show();
        return true;

    }



}
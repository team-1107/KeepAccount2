package com.example.keepaccount;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;
import android.util.Log;

public class DBManager {

    private DBHelper dbHelper;
    private String TBNAME;
    private static final String TAG = "DBManager";

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    //插入记录
    public void add(Item item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", item.getDate());
        values.put("time", item.getTime());
        values.put("money", item.getMoney());
        values.put("detail", item.getDetail());
        values.put("tag", item.getTag());
        values.put("type", item.getType());

        db.insert(TBNAME, null, values);
        db.close();
    }

    //根据id删除记录
    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    //根据id修改记录
    public void update(Item item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", item.getDate());
        values.put("time", item.getTime());
        values.put("money", item.getMoney());
        values.put("detail", item.getDetail());
        values.put("tag", item.getTag());
        values.put("type", item.getType());
        db.update(TBNAME, values, "id=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    //查询所有记录
    @SuppressLint("Range")
    public ArrayList<Item> listAll(){
        ArrayList<Item> recordList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            recordList = new ArrayList<Item>();
            while(cursor.moveToNext()){
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex("id")));
                item.setDate(cursor.getString(cursor.getColumnIndex("date")));
                item.setTime(cursor.getString(cursor.getColumnIndex("time")));
                item.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                item.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                item.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                item.setType(cursor.getString(cursor.getColumnIndex("type")));
                recordList.add(item);
            }
            cursor.close();
        }
        db.close();
        sortItemList(recordList);
        return recordList;

    }

    @SuppressLint("Range")
    public ArrayList<Item> listByTag(){
        ArrayList<Item> recordList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select sum(money) as total, tag from "+TBNAME+" GROUP BY tag";
        Cursor cursor = db.rawQuery(sql,null);
        //TODO: edit db
        if(cursor!=null){
            recordList = new ArrayList<Item>();
            while(cursor.moveToNext()){
                Item item = new Item();
                Log.i(TAG,cursor.getString(cursor.getColumnIndex("total")));
                item.setMoney(cursor.getString(cursor.getColumnIndex("total")));
                item.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                recordList.add(item);
            }
            cursor.close();
        }
        db.close();
        sortItemList(recordList);
        return recordList;

    }

    //根据ID查找记录
    @SuppressLint("Range")
    public Item findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        Item item = null;
        if(cursor!=null && cursor.moveToFirst()){
            item = new Item();
            item.setId(cursor.getInt(cursor.getColumnIndex("id")));
            item.setDate(cursor.getString(cursor.getColumnIndex("date")));
            item.setTime(cursor.getString(cursor.getColumnIndex("time")));
            item.setMoney(cursor.getString(cursor.getColumnIndex("money")));
            item.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
            item.setTag(cursor.getString(cursor.getColumnIndex("tag")));
            item.setType(cursor.getString(cursor.getColumnIndex("type")));
            cursor.close();
        }
        db.close();
        return item;
    }

    //查找所有Tag——不重复
    @SuppressLint("Range")
    public ArrayList<String> listAllTag(){
        ArrayList<String> tagList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] selectColumns = new  String[]{ "tag" };
        Cursor cursor = db.query(TBNAME, selectColumns, null, null, null, null, null);
        if(cursor!=null){
            tagList = new ArrayList<String>();
            while(cursor.moveToNext()){
                String tag = cursor.getString(cursor.getColumnIndex("tag"));
                if (!tagList.contains(tag) && tag != null) {
                    tagList.add(tag);
                }
            }
            cursor.close();
        }
        db.close();
        return tagList;
    }

    //根据date查找记录——多条记录
    @SuppressLint("Range")
    public ArrayList<Item> findByDate(String date){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "date=?", new String[]{date}, null, null, null);
        ArrayList<Item> recordList = null;
        if(cursor!=null){
            recordList = new ArrayList<Item>();
            while(cursor.moveToNext()){
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex("id")));
                item.setDate(cursor.getString(cursor.getColumnIndex("date")));
                item.setTime(cursor.getString(cursor.getColumnIndex("time")));

                item.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                item.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                item.setType(cursor.getString(cursor.getColumnIndex("type")));

                String money = cursor.getString(cursor.getColumnIndex("money"));
                if(cursor.getString(cursor.getColumnIndex("type")).equals("income")){
                    money = "+" + money;
                }else{
                    money = "-" + money;
                }
                item.setMoney(money);
                recordList.add(item);
            }
            cursor.close();
        }
        db.close();
        sortItemList(recordList);
        return recordList;
    }

    //根据输入内容查找记录
    @SuppressLint("Range")
    public ArrayList<Item> findRecord(String str) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
        Cursor cursor = db.query(TBNAME, null, "detail like '%" + str + "%' or tag like '%" + str + "%'",
                null, null, null, null);
        ArrayList<Item> recordList = null;
        if (cursor != null) {
            recordList = new ArrayList<Item>();
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex("id")));
                item.setDate(cursor.getString(cursor.getColumnIndex("date")));
                item.setTime(cursor.getString(cursor.getColumnIndex("time")));
                item.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                item.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                item.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                item.setType(cursor.getString(cursor.getColumnIndex("type")));

                recordList.add(item);

            }
            cursor.close();
        }
        db.close();
        sortItemList(recordList);
        return recordList;
    }

    //将记录按时间先后顺序排序
    public void sortItemList(ArrayList<Item> list){

        Collections.sort(list, new Comparator<Item>(){
            public int compare(Item o1, Item o2) {
                if(o1.getDate().compareTo(o2.getDate()) > 0){
                    //说明o1的日期大
                    return 1;
                }else if(o1.getDate().compareTo(o2.getDate()) < 0){
                    //说明o2的日期大
                    return -1;
                }else{
                    //o1和o2日期相等，比较时间
                    if(o1.getTime().compareTo(o2.getTime()) > 0){
                        //说明o1的时间大
                        return 1;
                    }else if(o1.getTime().compareTo(o2.getTime()) < 0){
                        //说明o2的时间大
                        return -1;
                    }else{
                        //o1、o2时间相同
                        return 0;
                    }
                }
            }
        });
    }
}

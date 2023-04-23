package com.example.keepaccount;

public class Item {
    private int id;
    private String date;
    private String time;
    private String money;
    private String detail;
    private String tag;
    private String type;
    private Integer color;
    private String count;
    private String percent;
    public Item() {
        super();
        date = "";
        time = "";
        money = "";
        detail = "";
        tag = "";
        type = "";
        percent = "";
        color = 0;
        count = "";
    }

    public Item(String date, String time, String money, String detail, String tag, String type) {
        super();
        this.date = date;
        this.time = time;
        this.money = money;
        this.detail = detail;
        this.tag = tag;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPercent(String percent){this.percent = percent;}

    public String getPercent(){ return percent;}

    public void setColor(Integer Color){this.color = Color;}
    public Integer getColor(){return color;}
    public void setCount(String Count){this.count = Count;}
    public String getCount(){return count;}

}

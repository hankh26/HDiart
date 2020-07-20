package com.hh1995.life;

public class DiaryItem {

    int no;
    String date;
    String impressive;
    String title;
    String msg;
    String file;

    public DiaryItem(int no,String date, String impressive ,String title, String msg, String file) {
        this.no = no;
        this.date = date;
        this.impressive = impressive;
        this.title = title;
        this.msg = msg;
        this.file = file;
    }

    public DiaryItem() {
    }
}

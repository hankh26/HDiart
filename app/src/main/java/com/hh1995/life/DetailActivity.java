package com.hh1995.life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    TextView tvDate;
    TextView tvTitle;
    TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvDate=findViewById(R.id.DTdate);
        tvTitle=findViewById(R.id.DTtitle);
        tvMsg=findViewById(R.id.DTmsg);


        Intent intent=getIntent();
        String date=intent.getStringExtra("Date");
        String title=intent.getStringExtra("Title");
        String msg=intent.getStringExtra("Msg");

        tvDate.setText(date);
        tvTitle.setText(title);
        tvMsg.setText(msg);

    }
}

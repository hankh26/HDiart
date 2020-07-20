package com.hh1995.life;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<DiaryItem> diaryItems=new ArrayList<>();
    DiaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView=findViewById(R.id.recycler);
        adapter=new DiaryAdapter(this,diaryItems);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    void loadData(){
        Retrofit retrofit=RetrofitHelper.getInstance();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<ArrayList<DiaryItem>> call=retrofitService.loadDataFromDiary();
        call.enqueue(new Callback<ArrayList<DiaryItem>>() {
            @Override
            public void onResponse(Call<ArrayList<DiaryItem>> call, Response<ArrayList<DiaryItem>> response) {
                if(response.isSuccessful()){
                    ArrayList<DiaryItem> items=response.body();

                    diaryItems.clear();
                    adapter.notifyDataSetChanged();

                    for (DiaryItem item:items){
                        diaryItems.add(0,item);
                        adapter.notifyItemInserted(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DiaryItem>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t+"", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void addDiary(View view) {
        Intent intent=new Intent(this,DiaryActivity.class);
        startActivity(intent);
    }
}

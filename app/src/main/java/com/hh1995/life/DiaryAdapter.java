package com.hh1995.life;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.http.GET;

public class DiaryAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<DiaryItem> items;

    public DiaryAdapter() {
    }

    public DiaryAdapter(Context context, ArrayList<DiaryItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.one_day_list,parent,false);
        VH holder=new VH(view);

        return holder;
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VH vh=(VH)holder;
            DiaryItem diaryItem=items.get(position);
            vh.date.setText(diaryItem.date);
            vh.title.setText(diaryItem.title);
            vh.impressive.setText(diaryItem.impressive);

            String imgUri="http://hkh26.dothome.co.kr/Diary/"+ diaryItem.file;
            Glide.with(context).load(imgUri).into(vh.iv);
        }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener{

        TextView date;
        TextView title;
        TextView impressive;
        ImageView iv;

        public VH(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            title=itemView.findViewById(R.id.title);
            impressive=itemView.findViewById(R.id.impressive);
            iv=itemView.findViewById(R.id.iv);

            itemView.setOnCreateContextMenuListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiaryItem item=items.get(getLayoutPosition());
                    Intent intent=new Intent(context,DetailActivity.class);
                    intent.putExtra("Title",item.title);
                    intent.putExtra("Date",item.date);
                    intent.putExtra("Msg",item.msg);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem delete=menu.add(Menu.NONE,1,1,"삭제");
            delete.setOnMenuItemClickListener(onMenuItemClickListener);
        }
        MenuItem.OnMenuItemClickListener onMenuItemClickListener=new MenuItem.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case 1:
                        items.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemChanged(getAdapterPosition(),items.size());
                        break;
                }

                return true;
            }
        };

    }
}

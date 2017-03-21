package com.kaka.gg;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by stre6 on 2017-02-20.
 */

public class Cadapter extends RecyclerView.Adapter<Cadapter.ViewHolder> {
    ArrayList<MyData> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv,ivi;
        ImageButton iv3;
        TextView tv_title, tv_date, tv_content, tv_count, tv_writer,iv2;

        public ViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.imageView);
            iv2 = (TextView) v.findViewById(R.id.imageView2);
            ivi = (ImageView) v.findViewById(R.id.iv_image);
            iv3 = (ImageButton) v.findViewById(R.id.imageView3);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            tv_content = (TextView) v.findViewById(R.id.tv_content);
            tv_count = (TextView) v.findViewById(R.id.tv_count);
            tv_writer = (TextView) v.findViewById(R.id.tv_writer);


        }
    }

    public Cadapter(ArrayList<MyData> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public Cadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemnotice, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_title.setText(mDataset.get(position).title);
        holder.tv_date.setText(mDataset.get(position).date);
        holder.tv_content.setText(mDataset.get(position).content);
        holder.tv_writer.setText(mDataset.get(position).writer);
        holder.tv_count.setText(mDataset.get(position).count);
        holder.iv.setImageResource(mDataset.get(position).imv);
        holder.iv2.setText(mDataset.get(position).imv2);
        holder.iv3.setImageResource(mDataset.get(position).imv3);
        holder.ivi.setImageResource(mDataset.get(position).imvi);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


class MyData {
    String title, date, content, count, writer,imv2;
    int imv,imv3, imvi;

    public MyData(String title, String date, String content, String count, String writer, int imv, String imv2, int imv3, int ivi) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.count = count;
        this.writer = writer;
        this.imv = imv;
        this.imv2 = imv2;
        this.imv3 = imv3;
        this.imvi = ivi;
    }
}


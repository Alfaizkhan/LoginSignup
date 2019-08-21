package com.example.loginsignup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loginsignup.Model.Data;
import com.example.loginsignup.R;

import java.util.ArrayList;
import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private List<Data> item;
    Context mContext;


    public DataAdapter(Context ctx, List<Data> item) {
        this.mContext = ctx;
        this.item = item;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return item != null ? item.size() : 0;
    }

    public void updateDataList(List<Data> data) {
        if (item == null) {
            item = new ArrayList<>();
        }
        item.clear();
        item.addAll(data);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView email;
        TextView firstName;
        TextView lastName;
        ImageView imageAvatar;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            email = itemView.findViewById(R.id.email);
            firstName = itemView.findViewById(R.id.firstname);
            lastName = itemView.findViewById(R.id.lastname);
            imageAvatar = itemView.findViewById(R.id.avtar);
        }

        void bindData(int position) {
            Data data = item.get(position);
            id.setText(item.get(position).getId());
            email.setText(item.get(position).getEmail());
            firstName.setText(item.get(position).getFirstName());
            lastName.setText(item.get(position).getLastName());
            Glide.with(mContext).load(item.get(position).getAvatar()).into(imageAvatar);
        }
    }
}
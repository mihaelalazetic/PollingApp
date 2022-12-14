package com.lazetic.pollingapp.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lazetic.pollingapp.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Task> polls;
    private RecyclerviewOnClickListener listener;

    public MyAdapter(List<Task> polls, Context context , RecyclerviewOnClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.polls = polls;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.task_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.pollName.setText(polls.get(position).getName());
        holder.start.setText(polls.get(position).getStartTime());
        holder.end.setText(polls.get(position).getEndTime());

        holder.itemView.setOnClickListener(view -> listener.recyclerviewClick(polls.get(position)));
    }

    @Override
    public int getItemCount() {
        return polls.size();
    }

}
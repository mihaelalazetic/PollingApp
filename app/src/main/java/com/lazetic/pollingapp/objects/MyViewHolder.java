package com.lazetic.pollingapp.objects;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lazetic.pollingapp.R;

public class MyViewHolder extends RecyclerView.ViewHolder  {
    TextView pollName,start,end;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        pollName = itemView.findViewById(R.id.pollName);
        start = itemView.findViewById(R.id.sTime);
        end = itemView.findViewById(R.id.eTime);
    }

}

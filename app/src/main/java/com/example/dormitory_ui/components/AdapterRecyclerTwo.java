package com.example.dormitory_ui.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dormitory_ui.R;


public class AdapterRecyclerTwo extends RecyclerView.Adapter<AdapterRecyclerTwo.MyView> {
    private final Context context;


    public AdapterRecyclerTwo(Context context) {
        this.context = context;

    }

    public static class MyView extends RecyclerView.ViewHolder {
        public MyView(@NonNull View itemView) {
            super(itemView);

            // Set click listener on the entire item view
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();

            });
        }
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_2, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerTwo.MyView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}

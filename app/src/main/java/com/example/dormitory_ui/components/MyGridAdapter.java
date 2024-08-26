package com.example.dormitory_ui.components;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dormitory_ui.R;
import com.example.dormitory_ui.models.Dormitory;

import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.pages.RegisterRoom3;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;

import java.util.List;

public class MyGridAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> items;
    private final List<Integer> imageResIds;
    private final List<Dormitory> dormitoryLs;
    private final Student student;
    private final String contractStarDate;
    private final String contractEndDate;

    public MyGridAdapter(Context context, List<String> items, List<Integer> imageResIds, List<Dormitory> dormitoryLs, Student student,String contractStarDate,String contractEndDate) {
        this.context = context;
        this.items = items;
        this.imageResIds = imageResIds;
        this.dormitoryLs = dormitoryLs;
        this.student = student;
        this.contractStarDate  = contractStarDate;
        this.contractEndDate = contractEndDate;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_1, parent, false);
        }

        TextView text = convertView.findViewById(R.id.text);
        text.setText(items.get(position));

        ImageView image = convertView.findViewById(R.id.image);
        image.setImageResource(imageResIds.get(position));

        convertView.setOnClickListener(v -> {
            Dormitory dormitoryIndex = dormitoryLs.get(position);

            // Messages.showMessage(context, "Selected Dormitory: " + dormitoryIndex.getId_dormitory() + "-" );
            Intent intent = new Intent(context, RegisterRoom3.class);
            intent.putExtra(Constants.DORMITORYDATA, new Gson().toJson(dormitoryIndex));
            intent.putExtra(Constants.STUDENT, new Gson().toJson(student));
            intent.putExtra(Constants.CONTRACT_START_DATE,contractStarDate);
            intent.putExtra(Constants.CONTRACT_END_DATE,contractEndDate);

            intent.putExtra(Constants.DORMITORYID,String.valueOf(dormitoryIndex.getId_dormitory()));
            context.startActivity(intent);
        });

        return convertView;
    }
}

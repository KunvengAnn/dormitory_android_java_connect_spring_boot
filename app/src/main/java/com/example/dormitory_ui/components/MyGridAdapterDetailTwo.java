package com.example.dormitory_ui.components;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dormitory_ui.R;
import com.example.dormitory_ui.models.Room;
import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.pages.RoomDetailPage;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;

import java.util.List;

public class MyGridAdapterDetailTwo extends BaseAdapter {
    private final Context context;
    private final List<Room> RoomLs;
    private final List<Integer> imageResIds;
    private final Student student;
    private final String contractStarDate;
    private final String contractEndDate;
    private final String dormitroyId;

    public MyGridAdapterDetailTwo(Context context, List<Room> RoomLs, List<Integer> imageResIds, Student student, String contractStarDate, String contractEndDate,String dormitoryId) {
        this.context = context;
        this.RoomLs = RoomLs;
        this.imageResIds = imageResIds;
        this.student = student;
        this.contractStarDate = contractStarDate;
        this.contractEndDate = contractEndDate;
        this.dormitroyId = dormitoryId;
    }

    @Override
    public int getCount() {
        return RoomLs.size();
    }

    @Override
    public Object getItem(int position) {
        return RoomLs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_detail, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.grid_idRoom);
            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.roomMaxCapacity = convertView.findViewById(R.id.grid_room_max_capacity);
            viewHolder.quantityPerson = convertView.findViewById(R.id.grid_quantity_person);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Room room = RoomLs.get(position);

        viewHolder.textView.setText(String.valueOf(room.getRoom_number()));
        viewHolder.image.setImageResource(imageResIds.get(position));
        viewHolder.roomMaxCapacity.setText(String.valueOf(room.getRoom_max_capacity()));
        viewHolder.quantityPerson.setText(String.valueOf(room.getQuantity_person()));

        // Set click listener for the entire item view
        convertView.setOnClickListener(v -> {
            Integer selectedRoomId = room.getId_room();

            Log.d(Constants.INFO, "selectedRoomId: " + selectedRoomId + "-" + room.getRoom_number());

            Intent intent = new Intent(context, RoomDetailPage.class);

            intent.putExtra(Constants.ROOMJSON, new Gson().toJson(room));
            intent.putExtra(Constants.STUDENT, new Gson().toJson(student));
            intent.putExtra(Constants.CONTRACT_START_DATE, contractStarDate);
            intent.putExtra(Constants.CONTRACT_END_DATE, contractEndDate);
            intent.putExtra(Constants.DORMITORYID,dormitroyId);
            context.startActivity(intent);
        });


        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
        ImageView image;
        TextView roomMaxCapacity;
        TextView quantityPerson;
    }
}

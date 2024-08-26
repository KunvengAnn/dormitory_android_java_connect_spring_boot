package com.example.dormitory_ui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dormitory_ui.R;
import com.example.dormitory_ui.components.MyGridAdapterDetailTwo;
import com.example.dormitory_ui.models.Dormitory;
import com.example.dormitory_ui.models.Room;
import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RegisterRoom3 extends AppCompatActivity {
    private GridView gridView;
    private ImageView btnBackDormitory;
    private AutoCompleteTextView autoCompleteTextViewFloor;
    private List<Room> rooms; // Store the rooms list here for filtering

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_room_layout);

        autoCompleteTextViewFloor = findViewById(R.id.idAutoCompleteTextView_dormitory_floor);
        gridView = findViewById(R.id.gridview_detail);
        btnBackDormitory = findViewById(R.id.id_BtnBackDormitory);

        getDataFromIntent();
        setupAutoCompleteTextView();
        setBtnBackDormitory();
    }

    private void setBtnBackDormitory() {
        btnBackDormitory.setOnClickListener(v -> finish());
    }

    private void setupAutoCompleteTextView() {
        autoCompleteTextViewFloor.setOnItemClickListener((parent, view, position, id) -> {
            String selectedFloor = (String) parent.getItemAtPosition(position);
            updateGridView(selectedFloor,null,null,null,null); // Filter and update GridView based on selected floor
        });
    }

    private void updateGridView(String selectedFloor,Student student,String contractStarDate,String contractEndDate,String dormitoryId) {
        List<Room> filteredRooms = new ArrayList<>();

        // Check if the selected floor is "Tất cả" which means "All" in Vietnamese
        if ("Tất cả".equals(selectedFloor)) {
            filteredRooms.addAll(rooms);
        } else {
            // Otherwise, filter rooms based on the selected floor
            for (Room room : rooms) {
                // Extract the floor number from the room number
                String roomNumberStr = String.valueOf(room.getRoom_number());
                if (roomNumberStr.length() > 1) {
                    // ex. 101 mean -> floor 1, 202 -> 2, 303 -> 3
                    String roomFloor = roomNumberStr.substring(0, 1);
                    // Check if the room's floor matches the selected floor
                    if (roomFloor.equals(selectedFloor)) {
                        filteredRooms.add(room); // Add the room to the filtered list
                    }
                }
            }
        }

        List<Integer> imageResIds = new ArrayList<>();
        int defaultImageResId = R.drawable.home;

        for (int i = 0; i < filteredRooms.size(); i++) {
            imageResIds.add(defaultImageResId);
        }

        MyGridAdapterDetailTwo adapter = new MyGridAdapterDetailTwo(this, filteredRooms, imageResIds,student,contractStarDate,contractEndDate,dormitoryId);
        gridView.setAdapter(adapter);
    }


    private Set<String> extractUniqueFloors(List<Room> rooms) {
        Set<String> floors = new HashSet<>();
        for (Room room : rooms) {
            String roomNumberStr = String.valueOf(room.getRoom_number());
            if (roomNumberStr.length() > 1) {
                // ex. 101 mean -> floor 1, 202 -> 2, 303 -> 3
                String floor = roomNumberStr.substring(0, 1);
                floors.add(floor);
            }
        }
        return floors;
    }

    private void populateAutoCompleteTextView(Set<String> floors) {
        List<String> floorList = floors.stream().sorted().collect(Collectors.toList());
        floorList.add(0, "Tất cả");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, floorList);
        autoCompleteTextViewFloor.setAdapter(adapter);
        autoCompleteTextViewFloor.setText("Tất cả", false);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();

        String dormitoryDataJson = intent.getStringExtra(Constants.DORMITORYDATA);
        String studentJson = intent.getStringExtra(Constants.STUDENT);
        String contractStarDate = intent.getStringExtra(Constants.CONTRACT_START_DATE);
        String contractEndDate = intent.getStringExtra(Constants.CONTRACT_END_DATE);
        String dormitoryId = intent.getStringExtra(Constants.DORMITORYID);

        if (dormitoryDataJson != null) {
            Gson gson = new Gson();
            Type dormitoryType = new TypeToken<Dormitory>() {}.getType();
            Dormitory dormitory = gson.fromJson(dormitoryDataJson, dormitoryType);

            // Extract rooms
            rooms = dormitory.getRooms();
            Set<String> floors = extractUniqueFloors(rooms);
            populateAutoCompleteTextView(floors);

            // Sort the rooms list by room ID in ascending order
            Collections.sort(rooms, new Comparator<Room>() {
                @Override
                public int compare(Room room1, Room room2) {
                    return Integer.compare(room1.getId_room(), room2.getId_room());
                }
            });

            if (studentJson != null) {
                Student student = gson.fromJson(studentJson, Student.class);

                String selectedFloor = "Tất cả";
                updateGridView(selectedFloor,student,contractStarDate,contractEndDate,dormitoryId);
            }
        }

    }
}

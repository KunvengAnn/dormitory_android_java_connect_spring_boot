package com.example.dormitory_ui.Exceptions;

import com.example.dormitory_ui.models.Dormitory;

import java.util.List;

public interface DormitoryException {
    void success(List<Dormitory> dormitories);
    void error(String errorMessage);
}

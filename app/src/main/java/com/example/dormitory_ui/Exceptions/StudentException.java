package com.example.dormitory_ui.Exceptions;

import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.models.StudentDTO;

public interface StudentException {
    void onSuccess(StudentDTO studentDTO);
    void onSuccessTwo(Student student);
    void onError(String errorMessage);
}

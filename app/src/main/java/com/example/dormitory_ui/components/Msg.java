package com.example.dormitory_ui.components;

import android.widget.Toast;
import android.content.Context;

public class Msg {

    private static Toast toast;

    // Display a toast message
    public static void showMessage(Context context, String message) {
        // Cancel any previous toast to avoid multiple toasts being shown
        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}

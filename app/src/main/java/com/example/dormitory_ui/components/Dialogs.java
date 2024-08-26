package com.example.dormitory_ui.components;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public class Dialogs {

    public static void showConfirmationDialog(
            Context context,
            String title,
            String message,
            String positiveButtonText,
            String negativeButtonText,
            DialogInterface.OnClickListener onPositiveClickListener,
            DialogInterface.OnClickListener onNegativeClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, onPositiveClickListener)
                .setNegativeButton(negativeButtonText, onNegativeClickListener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

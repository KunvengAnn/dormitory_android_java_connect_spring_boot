package com.example.dormitory_ui.components;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.dormitory_ui.R;

public class LoadingDialog {

    private static Dialog customLoadingDialog;

    public static void showCustomLoadingDialog(Context context) {
        customLoadingDialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_loading_dialog, null);
        customLoadingDialog.setContentView(view);
        customLoadingDialog.setCancelable(false);
        customLoadingDialog.show();
    }

    public static void dismissCustomLoadingDialog() {
        if (customLoadingDialog != null && customLoadingDialog.isShowing()) {
            customLoadingDialog.dismiss();
            customLoadingDialog = null; // Clear reference to allow garbage collection
        }
    }
}

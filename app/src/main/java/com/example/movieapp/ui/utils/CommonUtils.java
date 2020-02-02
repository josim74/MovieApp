package com.example.movieapp.ui.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.util.Log;

import com.example.movieapp.R;
import com.example.movieapp.ui.App;

public class CommonUtils {
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = ((ConnectivityManager) App.context.getSystemService(App.context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        try {
            progressDialog.show();
            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }catch (Exception e){
            Log.d("PROGRESS_EXCEPTION", e.getMessage());
        }

        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}

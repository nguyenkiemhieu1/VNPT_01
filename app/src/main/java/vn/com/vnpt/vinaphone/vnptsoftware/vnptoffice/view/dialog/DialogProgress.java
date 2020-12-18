package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import lombok.Getter;
import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.R;

public class DialogProgress {
    Context context;
    String messager;
    @Getter
    ProgressDialog progressDialog;
    private static Handler myHandler = new Handler();
    private static final int TIME_TO_WAIT = 500;
    private static final float DIM_AMOUNT=0.5f;
    public DialogProgress(Context context,String messager) {
        this.context=context;
        this.messager=messager;
        if (context!=null)
        {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage(messager);
            progressDialog.getWindow().setDimAmount(DIM_AMOUNT);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
    }
    public void showProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                restartThread();
            } else {
                progressDialog.show();
            }
        }
    }


    public void hideProgressDialog() {
        if (progressDialog != null) {
            startThread();
        }

    }
    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }
    };

    public void startThread() {
        if (myRunnable != null) {
            myHandler.postDelayed(myRunnable, TIME_TO_WAIT);
        }
    }

    public void stopThread() {
        if (myRunnable != null) {
            myHandler.removeCallbacks(myRunnable);
        }
    }

    public void restartThread() {
        if (myRunnable != null) {
            myHandler.removeCallbacks(myRunnable);
            myHandler.postDelayed(myRunnable, TIME_TO_WAIT);
        }
    }
}

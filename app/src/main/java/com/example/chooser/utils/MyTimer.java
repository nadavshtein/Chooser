package com.example.chooser.utils;

import android.content.Context;

import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.example.chooser.activity.MainActivity.gameMode;


public class MyTimer extends CountDownTimer {
    /**
     * @param millisInFuture    The number of millis in the future from the call
     * to {@link #start()} until the countdown is done and {@link #onFinish()}
     * is called.
     * @param countDownInterval The interval along the way to receive
     * {@link #onTick(long)} callbacks.
     */

    protected ConstraintLayout myLayout;
    protected Vibrator vibrator;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyTimer(long millisInFuture, long countDownInterval, ConstraintLayout layout) {
        super(millisInFuture, countDownInterval);

        myLayout = layout;
        vibrator = (Vibrator) myLayout.getContext().getSystemService(Context.VIBRATOR_SERVICE); //for vibrating phone
    }


    @Override
    public void onTick(long millisUntilFinished) {

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onFinish() {

        gameMode.endScene();
        int vibrationTime=350;
        vibrator.vibrate(vibrationTime);

    }
}

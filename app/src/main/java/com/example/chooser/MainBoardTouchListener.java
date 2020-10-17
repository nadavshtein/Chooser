package com.example.chooser;

import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.chooser.activity.MainActivity;
import com.example.chooser.utils.MyTimer;

import java.util.HashMap;


import static com.example.chooser.activity.MainActivity.gameMode;

public class MainBoardTouchListener implements View.OnTouchListener {

    public static boolean isFinish; // isFinished=true iff timer session is finished
    public ConstraintLayout myLayout;
    private HashMap<Integer, PointF> locations; // saving pointer locations in order to implement actionmove
    private CountDownTimer timerDown, timerUp;
    public MediaPlayer tapSound;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MainBoardTouchListener(ConstraintLayout layout) {

        locations = new HashMap<>();
        myLayout = layout;
        isFinish = false;
        timerDown = new MyTimer(2500, 1000, myLayout);
        timerUp = new MyTimer(2000, 1000, myLayout);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        switch (motionEvent.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_POINTER_DOWN:

                if (!isFinish) {

                    if (!MainActivity.isMuted) {  // voice control
                        tapSound = MediaPlayer.create(myLayout.getContext(), R.raw.tap);
                        tapSound.start();
                    }

                    int index = motionEvent.getActionIndex();
                    int pointerId = motionEvent.getPointerId(index);
                    savingLocation(motionEvent, index, pointerId);

                    gameMode.onDown(locations.get(pointerId).x, locations.get(pointerId).y, pointerId);
                    updateTimers(timerDown);
                }

                break;


            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_POINTER_UP:

                if (!isFinish) {
                    int indexUp = motionEvent.getActionIndex();
                    int pointerIdUp = motionEvent.getPointerId(indexUp);
                    if (gameMode.getImageViewsByPointersCopy().containsKey(pointerIdUp)) {
                        gameMode.onUp(pointerIdUp);
                        locations.remove(pointerIdUp);
                        updateTimers(timerUp);
                    }
                }

                break;

            case MotionEvent.ACTION_MOVE:

                if (!isFinish) {
                    for (int pointerIdCurrent : locations.keySet()) {

                        int pointerIndexCurrent = motionEvent.findPointerIndex(pointerIdCurrent);

                        if (locations.get(pointerIdCurrent).x != motionEvent.getX(pointerIndexCurrent)
                                || locations.get(pointerIdCurrent).y != motionEvent.getY(pointerIndexCurrent)) {

                            changePlace(gameMode.getImageViewsByPointersCopy().get(pointerIdCurrent), motionEvent.getX(pointerIndexCurrent), motionEvent.getY(pointerIndexCurrent));
                            locations.get(pointerIdCurrent).x = motionEvent.getX(pointerIndexCurrent);
                            locations.get(pointerIdCurrent).y = motionEvent.getY(pointerIndexCurrent);

                        }

                    }

                }
                break;


            case MotionEvent.ACTION_CANCEL:
                timerUp.cancel();
                timerDown.cancel();
                isFinish = true;
                gameMode.cancelAndReset();

                break;
        }


        return true;
    }


    private void savingLocation(MotionEvent motionEvent, int index, int pointerId) {

        PointF point = new PointF();
        point.x = motionEvent.getX(index);
        point.y = motionEvent.getY(index);
        locations.put(pointerId, point); // saving tap location
    }

    private void updateTimers(CountDownTimer timer) {

        timerUp.cancel();
        timerDown.cancel();

        int currentFingersCount=gameMode.getImageViewsByPointersCopy().size();
        if (currentFingersCount > gameMode.getModeNumber()) {
            timer.start();
        }

    }


    //placing view in the center of the tap
    public static void changePlace(View view, float x, float y) {

        view.setX(x - view.getWidth() / 2);
        view.setY(y - view.getHeight() / 2);
    }

}


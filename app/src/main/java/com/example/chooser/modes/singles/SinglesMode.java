package com.example.chooser.modes.singles;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.TransitionManager;

import com.example.chooser.MainBoardTouchListener;
import com.example.chooser.R;
import com.example.chooser.modes.GameMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

import static com.example.chooser.activity.MainActivity.gameMode;

public class SinglesMode extends GameMode {

    private Set<Drawable> availableDrawableSet; // drawables that are not used on screen yet
    private Map<Drawable, Integer> existingDrawablesMap; //  key=existing drawables on screen value=count of this drawable
    private List<ImageView> chosenImageViews;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SinglesMode(ConstraintLayout layout, int modeNum) {

        super(layout, modeNum);
        chosenImageViews=new ArrayList<>();
        availableDrawableSet = new HashSet<>();
        availableDrawableSet.addAll(drawableList);
        existingDrawablesMap = new HashMap<>();
        initializeExistingDrawableMap();

    }

    private void initializeExistingDrawableMap() {

        for (Drawable drawable : drawableList) {

            existingDrawablesMap.put(drawable, 0);
        }
    }


    @Override
    public void onUp(int pointerId) {

        // updating relevant maps
        Drawable removedDrawable = imageViewsByPointers.get(pointerId).getDrawable();
        existingDrawablesMap.put(removedDrawable,existingDrawablesMap.get(removedDrawable)-1);
        if (existingDrawablesMap.get(removedDrawable)==0) {
            availableDrawableSet.add(removedDrawable);
        }

        TransitionManager.beginDelayedTransition((ViewGroup) myLayout, animationSet);
        imageViewsByPointers.get(pointerId).setVisibility(View.GONE);
        imageViewsByPointers.remove(pointerId);

    }


    protected void colorsControl(ImageView iv) {

        if (availableDrawableSet.size() > 0) { // there is still color without use
            chooseNewColor(iv);
        } else {
            chooseRandomColor(iv);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void cancelAndReset() {

        for (int pointerId : imageViewsByPointers.keySet()) {
            TransitionManager.beginDelayedTransition((ViewGroup) myLayout, animationSet);
            imageViewsByPointers.get(pointerId).setVisibility(View.GONE);
        }

        gameMode=new SinglesMode(myLayout,modeNumber);
        myLayout.setOnTouchListener(new MainBoardTouchListener(myLayout));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void endScene() {

        chooseRandomFingers();
        concealUnchosenViews();

        if (modeNumber == 1) {
            startKonfettiOne();
        } else {
            startKonfettiMultiple();
        }

        startEndingAnimationTimer();

        //reset
        gameMode = new SinglesMode(myLayout, modeNumber);
        myLayout.setOnTouchListener(new MainBoardTouchListener(myLayout));
    }

    private void startEndingAnimationTimer() {

        CountDownTimer endingTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MainBoardTouchListener.isFinish = true;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFinish() {
                MainBoardTouchListener.isFinish = false;
                for (ImageView chosenImageView : chosenImageViews) {
                    TransitionManager.beginDelayedTransition((ViewGroup) myLayout, animationSet);
                    chosenImageView.setVisibility(View.GONE);
                }
                myLayout.setOnTouchListener(new MainBoardTouchListener(myLayout));
            }
        };

        endingTimer.start();
    }

    private void startKonfettiMultiple() {

        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < chosenImageViews.size(); i++) {

            colors.add(colorMap.get(chosenImageViews.get(i).getDrawable()));
        }
        KonfettiView konfettiView = myLayout.findViewById(R.id.viewKonfetti);
        konfettiView.build()
                .addColors(colors)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12, 5f))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f) // falling from top of the screen
                .streamFor(500, 1500L);


    }

    private void startKonfettiOne() {

        KonfettiView konfettiView = myLayout.findViewById(R.id.viewKonfetti);
        konfettiView.build()
                .addColors(colorMap.get(chosenImageViews.get(0).getDrawable()))
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12, 5f))
                .setPosition(chosenImageViews.get(0).getX() + 150, chosenImageViews.get(0).getY() + 150)
                //  .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f) // falling from top of the screen
                .streamFor(500, 1500L);
    }

    private void concealUnchosenViews() {

        for (ImageView imageView : imageViewsByPointers.values()) {

            if (!chosenImageViews.contains(imageView)) {
                TransitionManager.beginDelayedTransition((ViewGroup) myLayout, animationSet);
                imageView.setVisibility(View.GONE);
            }
        }
    }

    private void chooseRandomFingers() {

        List<Integer> idsList = new ArrayList<>();
        idsList.addAll(imageViewsByPointers.keySet());
        int randomIndex;
        int chosenFinger;
        for (int i = 0; i < modeNumber; i++) {
            randomIndex = random.nextInt(idsList.size());
            chosenFinger = idsList.get(randomIndex);
            idsList.remove(randomIndex);
            chosenImageViews.add(imageViewsByPointers.get(chosenFinger));
        }
    }


    private void chooseRandomColor(ImageView iv) {

        Drawable chosenDrawable=null;
        int rand = random.nextInt(drawableList.size());
        switch (rand) {
            case 0:
                chosenDrawable=drawableList.get(0);
                break;
            case 1:
                chosenDrawable=drawableList.get(1);
                break;
            case 2:
                chosenDrawable=drawableList.get(2);
                break;
            case 3:
                chosenDrawable=drawableList.get(3);
                break;
            case 4:
                chosenDrawable=drawableList.get(4);
                break;
            case 5:
                chosenDrawable=drawableList.get(5);
                break;

        }

        iv.setImageDrawable(chosenDrawable);
        existingDrawablesMap.put(chosenDrawable,existingDrawablesMap.get(chosenDrawable)+1);
    }

    private void chooseNewColor(ImageView iv) {

        int rand;
        rand = random.nextInt(availableDrawableSet.size());
        List<Drawable> tmpList = new ArrayList<>();
        tmpList.addAll(availableDrawableSet);
        Drawable drawable = tmpList.get(rand);
        tmpList.clear();
        availableDrawableSet.remove(drawable);
        existingDrawablesMap.put(drawable, existingDrawablesMap.get(drawable) + 1);
        iv.setImageDrawable(drawable);
    }


}

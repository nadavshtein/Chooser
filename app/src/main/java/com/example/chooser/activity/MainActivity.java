package com.example.chooser.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.chooser.MainBoardTouchListener;
import com.example.chooser.R;
import com.example.chooser.booms.BoomMenuPiece;
import com.example.chooser.booms.MyBoomBox;
import com.example.chooser.booms.groups.BoomGroupsButton;
import com.example.chooser.booms.singles.BoomSinglesButton;
import com.example.chooser.modes.GameMode;
import com.example.chooser.modes.singles.SinglesMode;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ConstraintLayout myLayout = null;
    private ImageButton volume;
    public static boolean isMuted = false;
    public static GameMode gameMode;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLayout = findViewById(R.id.myLayout);

        // volume setting
        volume = findViewById(R.id.volume);
        setVolume();

        gameMode = new SinglesMode(myLayout, 1); // default game mode
        myLayout.setOnTouchListener(new MainBoardTouchListener(myLayout));

        setMenuButtons();
    }


    private void setVolume() {

        volume.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (!isMuted) {
                    isMuted = true;
                    volume.setImageDrawable(getDrawable(R.drawable.ic_baseline_volume_off_24));
                } else {
                    isMuted = false;
                    volume.setImageDrawable(getDrawable(R.drawable.ic_baseline_volume_up_24));
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setMenuButtons() {

        List<Drawable> numImgList = new ArrayList<>(); // list of numbers images.
        initializeNumImgList(numImgList);

        List<Integer> colorList = new ArrayList<>();
        initializeColorList(colorList);

        List<BoomMenuPiece> pieceListSingles = new ArrayList<>();
        initializePiecesListSingles(pieceListSingles, colorList, numImgList);

        List<BoomMenuPiece> pieceListGroups = new ArrayList<>();
        initializePiecesListGroups(pieceListGroups, colorList, numImgList);

        BoomMenuButton bmbGroups = findViewById(R.id.bmbGroups);
        BoomMenuButton bmbSingles = findViewById(R.id.bmbSingles);
        MyBoomBox singlesButton = new BoomSinglesButton(myLayout, bmbSingles, pieceListSingles);
        MyBoomBox groupsButton = new BoomGroupsButton(myLayout, bmbGroups, pieceListGroups);

        singlesButton.initializeBoomButton();
        groupsButton.initializeBoomButton();

    }

    private void initializePiecesListGroups(List<BoomMenuPiece> pieceList,
                                            List<Integer> colorList,
                                            List<Drawable> numImgList) {

        for (int i = 1; i < 5; i++) {
            BoomMenuPiece menuPiece = new BoomMenuPiece(colorList.get(i), i+1, numImgList.get(i));
            pieceList.add(menuPiece);
        }
    }

    private void initializePiecesListSingles(List<BoomMenuPiece> pieceList,
                                             List<Integer> colorList,
                                             List<Drawable> numImgList) {

        for (int i = 0; i < 5; i++) {
            BoomMenuPiece menuPiece = new BoomMenuPiece(colorList.get(i), i+1, numImgList.get(i));
            pieceList.add(menuPiece);
        }
    }


    private void initializeColorList(List<Integer> colorList) {

        colorList.add(Color.parseColor("#e99e9e"));
        colorList.add(Color.parseColor("#cdf5ee"));
        colorList.add(Color.parseColor("#8690fa"));
        colorList.add(Color.parseColor("#fff087"));
        colorList.add(Color.parseColor("#ff9a00"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initializeNumImgList(List<Drawable> numImgList) {

        numImgList.add(getDrawable(R.drawable.one));
        numImgList.add(getDrawable(R.drawable.two));
        numImgList.add(getDrawable(R.drawable.three));
        numImgList.add(getDrawable(R.drawable.four));
        numImgList.add(getDrawable(R.drawable.five));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { // when phone back key is pressed

        switch (keyCode) {

            case KeyEvent.KEYCODE_BACK:
                this.finishAffinity();

        }

        return false;
    }


}
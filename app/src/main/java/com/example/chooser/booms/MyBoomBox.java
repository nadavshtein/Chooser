package com.example.chooser.booms;

import android.graphics.Color;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.List;

public abstract class MyBoomBox {

    protected ConstraintLayout myLayout;
    protected BoomMenuButton bmb;
    protected List<BoomMenuPiece> piecesList;  // to edit the button builders


    public MyBoomBox(ConstraintLayout myLayout, BoomMenuButton bmb, List<BoomMenuPiece> piecesList) {
        this.bmb = bmb;
        this.piecesList = piecesList;
        this.myLayout = myLayout;
    }

    public void initializeBoomButton() {

        bmb.setNormalColor(Color.WHITE);

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder();
            BoomMenuPiece boomMenuPiece = piecesList.get(i);
            builder.normalImageDrawable(boomMenuPiece.getNumberImg());
            builder.normalColor(boomMenuPiece.getColor());
            bmb.addBuilder(builder);
            final int fingersMode = boomMenuPiece.getFingersMode();
            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {

                    changeGameMode(fingersMode);
                }
            });
        }

    }

    protected abstract void changeGameMode(int fingersMode);


}

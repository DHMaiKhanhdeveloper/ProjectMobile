package com.example.fer_medindex.custom;

import android.content.Context;
import android.graphics.Typeface;

public class Utils {
    private static Typeface blackjackTypeface;

    public static Typeface getBlackjackTypeface(Context context) {
        if(blackjackTypeface == null) {
            blackjackTypeface = Typeface.createFromAsset(context.getAssets(),"fonts/LobsterTwo-Italic.otf");
        }
        return blackjackTypeface;
    }
}

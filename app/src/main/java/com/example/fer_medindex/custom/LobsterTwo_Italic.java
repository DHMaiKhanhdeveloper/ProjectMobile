package com.example.fer_medindex.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class LobsterTwo_Italic extends AppCompatTextView {
    public LobsterTwo_Italic(@NonNull Context context) {
        super(context);
        setFontsTextView();
    }

    public LobsterTwo_Italic(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFontsTextView();
    }

    public LobsterTwo_Italic(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontsTextView();
    }
    private void setFontsTextView(){
        Typeface typeface = Utils.getBlackjackTypeface(getContext());
        setTypeface(typeface);
    }
}

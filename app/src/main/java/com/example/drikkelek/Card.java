package com.example.drikkelek;

import android.graphics.drawable.Drawable;

public class Card {
    String type;
    int drawable;
    String text;

    public Card(String type, int drawable, String text) {
        this.type = type;
        this.drawable = drawable;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getText() {
        return text;
    }
}

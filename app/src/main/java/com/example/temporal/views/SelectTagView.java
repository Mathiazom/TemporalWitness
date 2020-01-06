package com.example.temporal.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

public class SelectTagView extends EntryTagView {


    private static final String TAG = "TW-SelectTagView";


    private boolean isSelected;


    public SelectTagView(Context context) {
        super(context);
        init();
    }
    public SelectTagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public SelectTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){

        setIsSelected(false);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){

            performClick();

            return true;

        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        super.performClick();

        setIsSelected(!isSelected);

        return true;
    }


    public void setIsSelected(final boolean selected){

        this.isSelected = selected;

        // Display selection state
        final Drawable background = getBackground();
        background.setAlpha(isSelected ? 255 : 40);
        setBackground(background);

    }

    public boolean isSelected(){

        return this.isSelected;

    }


}

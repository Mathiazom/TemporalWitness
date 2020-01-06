package com.example.temporal.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;

import com.example.temporal.models.TrackingTag;

public class EntryTagView extends AppCompatTextView {


    private static final String TAG = "TW-SelectTagView";


    private TrackingTag trackingTag;


    public EntryTagView(Context context) {
        super(context);
    }
    public EntryTagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public EntryTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setTrackingTag(final TrackingTag tag){

        this.trackingTag = tag;

        // Display tag name
        setText(tag.getName());

        // Set background colour
        ViewCompat.setBackgroundTintList(
                this,
                ColorStateList.valueOf(
                        Color.parseColor(tag.getColHex())
                )
        );


    }

    public TrackingTag getTrackingTag() {
        return this.trackingTag;
    }


}

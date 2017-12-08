package com.incon.service.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.incon.service.R;


public class CustomViewPager extends ViewPager {
    private boolean swipeable;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomViewPager);
        try {
            swipeable = a.getBoolean(R.styleable.CustomViewPager_swipeable, true);
        } finally {
            a.recycle();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swipeable ? super.onInterceptTouchEvent(event) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipeable ? super.onTouchEvent(event) : false;
    }
}

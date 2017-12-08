package com.incon.service.custom.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomAutoCompleteView extends AppCompatAutoCompleteTextView {
    public CustomAutoCompleteView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CustomAutoCompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomAutoCompleteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    // this is how to disable AutoCompleteTextView filter
    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            showDropDown();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        showDropDown();
        return super.onTouchEvent(event);
    }
}
package com.incon.service.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;


import com.incon.service.R;

import java.util.HashMap;

public class CustomTextInputLayout extends TextInputLayout {
    static HashMap<String, Typeface> typefaceHashMap = new HashMap<>();

    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext()
                    .obtainStyledAttributes(attrs, R.styleable.CustomTextInputLayout);
            String fontName = a.getString(R.styleable.CustomTextInputLayout_fontName);
            if (fontName != null) {
                //Creates new typeface if it is not previously created
                // or else returns the previously created one.
                Typeface myTypeface = (typefaceHashMap.get(fontName) == null)
                        ? createTypeface(fontName)
                        : typefaceHashMap.get(fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }

    }
    private Typeface createTypeface(String fontName) {
        typefaceHashMap.put(fontName, Typeface.createFromAsset(getContext().getAssets(), fontName));
        return typefaceHashMap.get(fontName);
    }


}

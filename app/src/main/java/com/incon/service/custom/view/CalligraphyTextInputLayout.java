package com.incon.service.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * TextInputLayout for use with Calligraphy that supports a fontPath defined by the
 * hintTextAppearance attribute
 */
public class CalligraphyTextInputLayout extends TextInputLayout {

    private String fontPath;

    public CalligraphyTextInputLayout(Context context) {
        super(context);
    }

    public CalligraphyTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontPath(attrs);
    }

    public CalligraphyTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontPath(attrs);
    }

    private void setFontPath(AttributeSet attrs) {
        int attrId = CalligraphyConfig.get().getAttrId();
        fontPath = pullHintFontPathFromTextAppearance(getContext(), attrs, attrId);
    }

    @Override
    public void addView(View child, int index, final ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child instanceof EditText) {
            // Typeface is set when adding an EditText, so we must set ours after
            Typeface typeface = TypefaceUtils.load(getResources().getAssets(), fontPath);
            setTypeface(typeface);
        }
    }

    /**
     *  Modified from CalligraphyUtils.pullHintFontPathFromTextAppearance()
     */
    static String pullHintFontPathFromTextAppearance(final Context context, AttributeSet attrs,
                                                     int attributeId) {
        if (attributeId == -1 || attrs == null) {
            return null;
        }

        int textAppearanceId = -1;
        final TypedArray typedArrayAttr = context.obtainStyledAttributes(attrs,
                new int[]{android.support.design.R.attr.hintTextAppearance});
        if (typedArrayAttr != null) {
            try {
                textAppearanceId = typedArrayAttr.getResourceId(0, -1);
            } catch (Exception ignored) {
                // Failed for some reason
                return null;
            } finally {
                typedArrayAttr.recycle();
            }
        }

        final TypedArray textAppearanceAttrs = context.obtainStyledAttributes(textAppearanceId,
                new int[]{attributeId});
        if (textAppearanceAttrs != null) {
            try {
                return textAppearanceAttrs.getString(0);
            } catch (Exception ignore) {
                // Failed for some reason.
                return null;
            } finally {
                textAppearanceAttrs.recycle();
            }
        }
        return null;
    }
}

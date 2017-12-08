package com.incon.service.custom.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class CustomAppBarLayout extends AppBarLayout
        implements AppBarLayout.OnOffsetChangedListener {

    private View view;
    private int viewActualHeight;

    public CustomAppBarLayout(Context context) {
        super(context);
    }

    public CustomAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setView(final View view) {
        this.view = view;

        ViewRenderObserver viewRenderObserver = new ViewRenderObserver();
        viewRenderObserver.setOnObserverCallback(view,
                new ViewRenderObserver.ViewRenderObserverCallback() {
            @Override
            public void onPreDraw() {
                viewActualHeight = view.getHeight();
                addOnOffsetChangedListener(CustomAppBarLayout.this);
            }
        });
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (view == null) {
            return;
        }

        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.height = (int) (viewActualHeight
                - ((viewActualHeight * 50 / 100F) * percentage));
        //layoutParams.topMargin = diff;
        view.setLayoutParams(layoutParams);
    }
}

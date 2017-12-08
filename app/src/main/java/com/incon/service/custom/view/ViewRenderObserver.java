package com.incon.service.custom.view;

import android.view.View;
import android.view.ViewTreeObserver;

public class ViewRenderObserver {

    private ViewRenderObserverCallback viewRenderObserverCallback;

    public interface ViewRenderObserverCallback {
        void onPreDraw();
    }

    public void setOnObserverCallback(final View view, ViewRenderObserverCallback  callback) {
        viewRenderObserverCallback = callback;
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (viewTreeObserver.isAlive()) {
                    viewTreeObserver.removeOnPreDrawListener(this);
                }
                else {
                    ViewTreeObserver viewTreeObserver1 = view.getViewTreeObserver();
                    viewTreeObserver1.removeOnPreDrawListener(this);
                }
                viewRenderObserverCallback.onPreDraw();
                return false;
            }
        });
    }

}




package com.incon.service.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

import com.incon.service.utils.Logger;


public class CustomWebView extends WebView {
    ScrollListener scrollListener;
    private boolean isAtEnd;

    public CustomWebView(Context context) {
        super(context);
        init();
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {

       /* ViewRenderObserver viewRenderObserver = new ViewRenderObserver();
        viewRenderObserver.setOnObserverCallback(this,
                new ViewRenderObserver.ViewRenderObserverCallback() {
                    @Override
                    public void onPreDraw() {
                        validateHeight();
                    }
                });*/

    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        validateHeight();
        super.onScrollChanged(l, t, oldl, oldt);
    }


    private void validateHeight() {
        boolean isAtEnd = false;
        int height = (int) Math.floor((this.getContentHeight()) * this.getScale());
        int webViewHeight = this.getMeasuredHeight();
        Logger.d("onScrollChanged", "height = " + height + " : webViewHeight : " + webViewHeight
                + " scale:" + this.getScale());
        if (this.getScrollY() + webViewHeight >= height) {
            Log.i("THE END", "reached");

            isAtEnd = true;
        }

        if (scrollListener != null) {
            scrollListener.onScrollEnd(isAtEnd);

            /*String js = "javascript:(function(){"
                    + "document.getElementsByTagName('div')[0].style.height =" + (height + 200)
                    + ";"
                    + "document.getElementsByTagName('div')[0].style.width = 'auto';"
                    + "})()";
            loadUrl(js);*/
        }
    }

    public interface ScrollListener {
        void onScrollEnd(boolean isEnd);
    }
}

package com.incon.service.custom.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class RecyclerItemPaddingDecorator extends RecyclerView.ItemDecoration {
    private final int size;
    private float applyDimension;

    public RecyclerItemPaddingDecorator(int size) {
        this.size = size;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (applyDimension == 0)
            applyDimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size,
                    parent.getContext().getResources().getDisplayMetrics());
        // Apply offset only to first item
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left += applyDimension;
        }
    }
}

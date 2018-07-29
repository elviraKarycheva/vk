package com.example.karyc.vkontaktikum;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MarginItemDecoration extends RecyclerView.ItemDecoration {
    private int spaceTop;
    private int spaceSide;

    public MarginItemDecoration(int spaceTop, int spaceSide) {
        this.spaceTop = spaceTop;
        this.spaceSide = spaceSide;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.top = spaceTop;
        outRect.left = spaceSide;
        outRect.right = spaceSide;
    }
}

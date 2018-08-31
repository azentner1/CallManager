package com.example.phone.phonecallmanager.custom_ui.recylcer_view_ui;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.phone.phonecallmanager.R;


public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable drawable;
    private int paddingLeft = 0;
    private int paddingRight = 0;

    public DividerItemDecoration(Resources resources, int paddingLeft, int paddingRight) {
        this.drawable = ResourcesCompat.getDrawable(resources, R.drawable.recycler_view_divider, null);
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
    }

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = paddingLeft;
        int right = parent.getWidth() - paddingRight;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + drawable.getIntrinsicHeight();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(c);
        }
    }
}

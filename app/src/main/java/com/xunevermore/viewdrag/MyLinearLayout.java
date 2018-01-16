package com.xunevermore.viewdrag;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class MyLinearLayout extends LinearLayout {

    private int size = 100;
    private ViewDragHelper viewDragHelper;
    private View container;
    private View dragView;

    private int marginTop;

    private int containerHeight;

    public MyLinearLayout(Context context) {
        this(context,null);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        viewDragHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {


            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child!=container;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                int paddingTop = getPaddingTop();
                int bottom = getHeight() - size;
                return Math.min(Math.max(top,paddingTop),bottom);
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                marginTop = dragView.getTop();
                containerHeight = getHeight()-marginTop-dragView.getHeight();


                requestLayout();
            }


        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if(containerHeight!=0){
            MarginLayoutParams layoutParams = (MarginLayoutParams) dragView.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin,marginTop,layoutParams.rightMargin,layoutParams.bottomMargin);
            dragView.setLayoutParams(layoutParams);

            ViewGroup.LayoutParams layoutParams1 = container.getLayoutParams();
            layoutParams1.height=containerHeight;
            container.setLayoutParams(layoutParams1);
        }
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dragView = getChildAt(0);
        container = getChildAt(1);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }
}

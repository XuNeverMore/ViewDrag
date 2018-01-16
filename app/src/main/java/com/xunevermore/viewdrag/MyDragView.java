package com.xunevermore.viewdrag;
/**
 * Created by Administrator on 2018/1/16 0016.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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

public class MyDragView extends ViewGroup {

    private ViewDragHelper viewDragHelper;
    private View container;
    private View dragView;


    private int topSpacing = 200;




    private int marginTop;

    private int containerHeight;

    public MyDragView(Context context) {
        this(context, null);
    }

    public MyDragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        viewDragHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {


            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child != container;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                int paddingTop = getPaddingTop();
                int bottom = getMeasuredHeight() - dragView.getMeasuredHeight();
                return marginTop = Math.min(Math.max(top, paddingTop), bottom);
            }


            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);

                requestLayout();
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {


                int measuredHeight = getMeasuredHeight();
                int top = releasedChild.getTop();

                int middlePoi = (topSpacing+measuredHeight)/2;
                if (top > middlePoi) {
                    viewDragHelper.smoothSlideViewTo(releasedChild, 0, measuredHeight-dragView.getMeasuredHeight());

                } else {
                    viewDragHelper.smoothSlideViewTo(releasedChild, 0, topSpacing);
                }

                invalidate();
            }
        });
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dragView = getChildAt(0);
        container = getChildAt(1);
        ViewCompat.setElevation(dragView,16);
        ViewCompat.setElevation(container,16);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child0 = getChildAt(0);
        measureChild(child0, widthMeasureSpec, heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int measuredHeight = getMeasuredHeight();
        marginTop = (int) Math.min(Math.max(marginTop,topSpacing), measuredHeight-dragView.getMeasuredHeight());

        containerHeight = getMeasuredHeight() - marginTop - child0.getMeasuredHeight();
        View child1 = getChildAt(1);
        child1.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(containerHeight, MeasureSpec.EXACTLY));


    }

    private static final String TAG = "MyDragView";

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        View child0 = getChildAt(0);

        child0.layout(0, marginTop, child0.getMeasuredWidth(), marginTop + child0.getMeasuredHeight());
        Log.i(TAG, "onLayout: " + child0.getMeasuredWidth() + "," + child0.getMeasuredHeight());


        View child1 = getChildAt(1);

        child1.layout(0, marginTop + child0.getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight());


    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            marginTop = dragView.getTop();
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }
}

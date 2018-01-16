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

public class MyView extends LinearLayout {

    private ViewDragHelper viewDragHelper;
    private View edegView;
    private int left;
    private int top;
    private View oneView;

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);


    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child!=oneView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {

                int leftEdeg = getPaddingLeft();

                int rightEdeg = getWidth() - getPaddingRight() - child.getWidth();

                return Math.min(Math.max(left,leftEdeg),rightEdeg);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                int bottomEdeg = getHeight() - getPaddingBottom() - child.getWidth();

                int topEdeg = getPaddingTop();


                return Math.min(bottomEdeg,Math.max(topEdeg,top));
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
//                super.onEdgeDragStarted(edgeFlags, pointerId);
                viewDragHelper.captureChildView(oneView,pointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {

//                super.onViewReleased(releasedChild, xvel, yvel);

                if(releasedChild==edegView){
                    viewDragHelper.settleCapturedViewAt(left,top);
                    invalidate();
                }


            }
        });
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        edegView = getChildAt(2);

        oneView = getChildAt(0);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        left = edegView.getLeft();
        top = edegView.getTop();
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if(viewDragHelper.continueSettling(true)){
            invalidate();
        }
    }
}

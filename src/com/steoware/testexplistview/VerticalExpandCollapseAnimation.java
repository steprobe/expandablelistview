package com.steoware.testexplistview;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class VerticalExpandCollapseAnimation extends Animation {

    private final View mTargetView;
    private final ViewGroup.LayoutParams mLayoutParams;
    private final int mStartHeight;
    private final int mFinalHeight;

    public VerticalExpandCollapseAnimation(View targetView, int startHeight, int finalHeight) {
        mTargetView = targetView;
        mLayoutParams = targetView.getLayoutParams();
        mStartHeight = startHeight;
        mFinalHeight = finalHeight;
    }

    public int getCurrentHeight(long currentTime) {
        long startTime = getStartTime();
        if (startTime == START_ON_FIRST_FRAME) {
            startTime = currentTime;
        }

        startTime += getStartOffset();

        if (currentTime <= startTime) {
            return mStartHeight;
        }

        long duration = getDuration();
        if (currentTime >= startTime + duration) {
            return mFinalHeight;
        }

        float normalizedTime = ((float)(currentTime - startTime)) / ((float)duration);
        return getCurrentHeight(normalizedTime);
    }

    private int getCurrentHeight(float interpolatedTime) {
        return (int)((mFinalHeight - mStartHeight) * interpolatedTime) + mStartHeight;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        super.applyTransformation(interpolatedTime, transformation);

        mLayoutParams.height = getCurrentHeight(interpolatedTime);
        mTargetView.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}

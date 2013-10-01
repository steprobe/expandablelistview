package com.steoware.testexplistview;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class ExpanderToggleManager {

    private static final int INVALID_POSITION = -1;
    private static final int ANIMATION_DURATION = 330;

    private int mCollapsePosition = INVALID_POSITION;
    private VerticalExpandCollapseAnimation mCollapseAnimation;
    private VerticalExpandCollapseAnimation mExpandAnimation;

    private final int mPanelHeight;
    private int mOpenPosition = INVALID_POSITION;

    private final ViewGroup mContainer;

    public static class ToggleButtonTag {

        public int position;
        public View viewToExpand;

        public ToggleButtonTag(int position, View viewToExpand) {
            this.position = position;
            this.viewToExpand = viewToExpand;
        }
    }

    public ExpanderToggleManager(int panelHeight, ViewGroup container) {
        mPanelHeight = panelHeight;
        mContainer = container;
    }

    private interface ForEachMatchingPosition {
        public void onMatch(ToggleButtonTag tag);
    }

    private void forEachMatchingPosition(int position, final ForEachMatchingPosition callback) {

        final int childCount = mContainer.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = mContainer.getChildAt(i);
            View toggleButton = childView.findViewById(R.id.lv_item_button);

            ToggleButtonTag tag = null;
            if (toggleButton != null
                    && (tag = (ToggleButtonTag) toggleButton.getTag()) != null
                    && tag.position == position) {

                callback.onMatch(tag);
            }
        }
    }

    public void toggle(final int position) {

        boolean allClosed = false;

        if (mOpenPosition != INVALID_POSITION) {

            mCollapseAnimation = null;
            forEachMatchingPosition(mOpenPosition,
                    new ForEachMatchingPosition() {

                        @Override
                        public void onMatch(ToggleButtonTag tag) {
                            View container = tag.viewToExpand;

                            if (mCollapseAnimation == null) {
                                mCollapseAnimation = new VerticalExpandCollapseAnimation(
                                        container, mPanelHeight, 0);
                                mCollapseAnimation
                                        .setDuration(ANIMATION_DURATION);
                                mCollapseAnimation
                                        .setAnimationListener(new AnimationListener() {

                                            @Override
                                            public void onAnimationEnd(
                                                    Animation arg0) {
                                                mCollapseAnimation = null;
                                            }

                                            @Override
                                            public void onAnimationRepeat(
                                                    Animation animation) {
                                                // Nothing to do
                                            }

                                            @Override
                                            public void onAnimationStart(
                                                    Animation animation) {
                                                // Nothing to do
                                            }

                                        });
                            }
                            container.startAnimation(mCollapseAnimation);
                            mCollapsePosition = mOpenPosition;
                        }
                    });

            if (position == mOpenPosition) {
                allClosed = true;
            }

            mOpenPosition = INVALID_POSITION;
        }

        if (!allClosed && mOpenPosition != position) {

            mExpandAnimation = null;
            forEachMatchingPosition(position, new ForEachMatchingPosition() {

                @Override
                public void onMatch(ToggleButtonTag tag) {
                    ViewGroup container = (ViewGroup) tag.viewToExpand;

                    if (mExpandAnimation == null) {
                        mExpandAnimation = new VerticalExpandCollapseAnimation(
                                container, 0, mPanelHeight);

                        mExpandAnimation.setDuration(ANIMATION_DURATION);
                        mExpandAnimation
                                .setAnimationListener(new AnimationListener() {

                                    @Override
                                    public void onAnimationEnd(
                                            Animation animation) {
                                        mExpandAnimation = null;
                                    }

                                    @Override
                                    public void onAnimationRepeat(
                                            Animation animation) {
                                        // Nothing to be done
                                    }

                                    @Override
                                    public void onAnimationStart(
                                            Animation animation) {
                                        // Nothing to be done
                                    }
                                });
                    }
                    container.startAnimation(mExpandAnimation);
                }
            });

            mOpenPosition = position;
        }
    }

    public boolean isCurrentPosition(int position) {
        return position == mOpenPosition;
    }

    public void closeAnyOpen() {
        if (mOpenPosition != INVALID_POSITION) {
            toggle(mOpenPosition);
        }
    }

    public void setCurrentHeight(int position, View container) {
        int height = 0;
        VerticalExpandCollapseAnimation animation = null;

        if (position == mOpenPosition) {
            if (mExpandAnimation != null) {
                final long timeMillis = AnimationUtils
                        .currentAnimationTimeMillis();
                height = mExpandAnimation.getCurrentHeight(timeMillis);
                animation = mExpandAnimation;

            }
            else {
                height = mPanelHeight;
            }

        }
        else if (position == mCollapsePosition) {
            if (mCollapseAnimation != null) {
                final long timeMillis = AnimationUtils
                        .currentAnimationTimeMillis();
                height = mCollapseAnimation.getCurrentHeight(timeMillis);
                animation = mCollapseAnimation;
            }
        }

        container.getLayoutParams().height = height;
        container.setAnimation(animation);
    }
}

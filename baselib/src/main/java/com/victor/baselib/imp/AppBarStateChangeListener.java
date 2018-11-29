package com.victor.baselib.imp;

import android.support.design.widget.AppBarLayout;

/**
 * @author fanwentao
 * @Description 监听AppBarLayout的滚动
 * @date 2018/7/4
 */
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }
            mCurrentState = State.IDLE;
        }
        onStateChanged(i);
    }

    /**
     * 监听滚动状态
     * @param appBarLayout
     * @param state 展开状态，折叠状态，准备状态
     */
    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);

    public void onStateChanged(int i) {
    }

    public enum State {
        EXPANDED,       // 展开状态
        COLLAPSED,      // 折叠状态
        IDLE            // 准备状态
    }
}

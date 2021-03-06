package com.victor.baselib.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.victor.baselib.R;

/**
 * @author fanwentao
 * @Description
 * @date 2018/6/29
 */
public class UcTabBehavior extends CoordinatorLayout.Behavior<View> {

    private float deltaY;
    private int titleHeight;
    private float maxMove;

    public UcTabBehavior() {
    }

    public UcTabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        titleHeight = context.getResources().getDimensionPixelSize(R.dimen.y210);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (deltaY == 0) {
            deltaY = dependency.getY() - child.getHeight();
            maxMove = child.getY() - titleHeight;
        }

        float dy = dependency.getY() - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        float y = (dy / deltaY) * child.getHeight();

//        if (dy < maxMove) {
//            child.setTranslationY(y);
//        }
        return true;
    }
}

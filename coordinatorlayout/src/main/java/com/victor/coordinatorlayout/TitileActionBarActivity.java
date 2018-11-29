package com.victor.coordinatorlayout;

/**
 * @author fanwentao
 * @Description
 * @date 2018/7/2
 */
public class TitileActionBarActivity extends CoordinatorActionBarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_action_bar;
    }

    @Override
    protected String setTitle() {
        return "Title Action Bar";
    }

}

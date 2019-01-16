package com.victor.playandroid.login

import android.os.Bundle
import android.widget.TextView
import com.victor.baselib.ui.BaseFitsSystemWindowsActivity
import com.victor.playandroid.R
import com.victor.playandroid.register.RegisterActivity

class LoginActivity : BaseFitsSystemWindowsActivity() {

    override fun getLayoutId(): Int = R.layout.login_activity

    override fun getTitleRes(): Int = R.string.login

    override fun initView() {
        enableBackButton(R.drawable.ic_navigate_before)
        disableMenu()
        findViewById<TextView>(R.id.login_register).setOnClickListener {
            startActivity(RegisterActivity::class.java)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}
package com.victor.playandroid.register

import android.os.Bundle
import android.widget.EditText
import butterknife.BindView
import butterknife.OnClick
import com.victor.baselib.ui.BaseFitsSystemWindowsActivity
import com.victor.baselib.utils.Tools
import com.victor.playandroid.R
import com.victor.playandroid.R2

class RegisterActivity : BaseFitsSystemWindowsActivity(), IRegisterView {

    @BindView(R2.id.register_user)
    lateinit var etUserName: EditText
    @BindView(R2.id.register_password)
    lateinit var etPassword: EditText
    @BindView(R2.id.register_repassword)
    lateinit var etRepassword: EditText

    var presenter: RegisterPresenter? = null


    override fun getLayoutId(): Int = R.layout.register_activity

    override fun getTitleRes(): Int = R.string.register

    override fun initView() {
        enableBackButton(R.drawable.ic_navigate_before)
        disableMenu()
    }

    override fun initData(savedInstanceState: Bundle?) {
        presenter = RegisterPresenter(this, RegisterModel())
    }

    @OnClick(R.id.register_register)
    fun registerOnClick() {
        val userName = etUserName.text.toString()
        val password = etPassword.text.toString()
        val repassword = etRepassword.text.toString()

        if (Tools.isEmpty(userName)) {
            showToast(R.string.login_user_empty)
            return
        } else if (userName.length < 3) {
            showToast(R.string.login_user_limit)
            return
        }
        if (Tools.isEmpty(password)) {
            showToast(R.string.login_password_empty)
            return
        } else if (password.length < 6) {
            showToast(R.string.login_password_limit)
            return
        }
        if (Tools.isEmpty(repassword)) {
            showToast(R.string.register_repassword_empty)
            return
        } else if (repassword.length < 6) {
            showToast(R.string.login_password_limit)
            return
        } else if (password != (repassword)) {
            showToast(R.string.register_password_diff)
            return
        }
        showLoading(true)
        presenter?.register(userName, password, repassword)
    }

}
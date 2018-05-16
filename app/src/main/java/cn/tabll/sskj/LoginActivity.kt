package cn.tabll.sskj

import android.animation.Animator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private var log = AnkoLogger<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        log.info("登陆注册页已启动")
    }

    private fun init(){
        login_tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) { }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (login_tabLayout.selectedTabPosition) {
                    0 -> {
                        sign_up_cardView.visibility = CardView.GONE
                        sign_in_cardView.visibility = CardView.VISIBLE
                        animateCircularReveal(sign_in_cardView)
                    }
                    1 -> {
                        sign_in_cardView.visibility = CardView.GONE
                        sign_up_cardView.visibility = CardView.VISIBLE
                        animateCircularReveal(sign_up_cardView)
                    }
                }
            }
        })

        get_verification_button.setOnClickListener {
            phone_number_signUp_textView.isEnabled = false
            get_verification_button.isEnabled = false
            phone_number_signUp_textView.text
            toast(phone_number_signUp_textView.text)
        }

        confirm_button.setOnClickListener {
            toast("已请求")
        }

        confirm_signUp_button.setOnClickListener {
            toast("已请求")
        }
    }

    /**
     * 注册与登陆的切换动画
     **/
    private fun animateCircularReveal(view: View){
        val cx = (view.right - view.left) / 2
        val cy = view.top
        val finalRadius = Math.max(view.width, view.height)
        val anim: Animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 200f, finalRadius.toFloat())
        anim.start()
    }
}

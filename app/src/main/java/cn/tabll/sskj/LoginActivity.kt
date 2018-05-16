package cn.tabll.sskj

import android.animation.Animator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //var anim: Animator = ViewAnimationUtils.createCircularReveal(tv9, cx, cy, 0, finalRadius)





        login_tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) { }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (login_tabLayout.selectedTabPosition) {
                    0 -> {
                        sign_up_cardView.visibility = CardView.GONE
                        sign_in_cardView.visibility = CardView.VISIBLE
                        val cx = (sign_in_cardView.right - sign_in_cardView.left) / 2
                        val cy = sign_in_cardView.top
                        val finalRadius = Math.max(sign_in_cardView.width, sign_in_cardView.height)
                        val anim: Animator = ViewAnimationUtils.createCircularReveal(sign_in_cardView, cx, cy, 200f, finalRadius.toFloat())
                        anim.start()
                    }
                    1 -> {
                        sign_in_cardView.visibility = CardView.GONE
                        sign_up_cardView.visibility = CardView.VISIBLE
                        val cx = (sign_in_cardView.right - sign_in_cardView.left) / 2
                        val cy = sign_in_cardView.top
                        val finalRadius = Math.max(sign_in_cardView.width, sign_in_cardView.height)
                        val anim: Animator = ViewAnimationUtils.createCircularReveal(sign_up_cardView, cx, cy, 200f, finalRadius.toFloat())
                        anim.start()
                    }
                }
                //toast(login_tabLayout.selectedTabPosition)
            }
        })

    }

}

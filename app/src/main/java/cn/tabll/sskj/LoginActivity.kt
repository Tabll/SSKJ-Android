package cn.tabll.sskj

import android.animation.Animator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewAnimationUtils
import cn.tabll.sskj.https.HttpConnectors
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*

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
            //phone_number_signUp_textView.text
            //toast(phone_number_signUp_textView.text)
            doAsync {
                sendVerificationMessage() //发送验证码
                var secondLeft = 10
                while (secondLeft != 0) {
                    secondLeft--
                    uiThread {
                        get_verification_button.text = secondLeft.toString()
                    }
                    when (secondLeft) {
                        0 -> {
                            uiThread {
                                get_verification_button.isEnabled = true
                                get_verification_button.text = "获取验证码"
                            }
                        }
                        else -> Thread.sleep(1000)
                    }
                }
            }
        }

        confirm_button.setOnClickListener {

            val httpConnectors = HttpConnectors()
            val codes = mapOf("my-test-key" to "HA22GU46aGIU784QF1DV")

            var result:String?
            doAsync {
                result = httpConnectors.httpPost("https://www.tabll.cn/sskjapi/test.php", codes, "utf-8")
                uiThread {
                    toast(result!!)
                }
            }

            //toast(result)
        }

        confirm_signUp_button.setOnClickListener {
            toast("已请求")
        }
    }

    /**
     * 发送短信验证码
     **/
    private fun sendVerificationMessage(){
        doAsync {
            val httpConnector = HttpConnectors()
            val codes = mapOf(
                    "sign-up-state" to "1",
                    "user-phone-number" to phone_number_signUp_textView.text.toString(),
                    "user-verify-code" to "",
                    "user-password" to "")
            val result = httpConnector.httpPost("https://www.tabll.cn/sskjapi/signup.php", codes, "utf-8")
            when (result){
                "success" -> {
                    uiThread {
                        toast("短信发送成功")
                    }
                }
                else -> {
                    uiThread {
                        toast(result)
                    }
                }

            }


            uiThread {
                //toast(result!!)
            }
        }
    }

    /**
     * 注册与登陆CardView的切换动画
     **/
    private fun animateCircularReveal(view: View){
        val cx = (view.right - view.left) / 2
        val cy = view.top
        val finalRadius = Math.max(view.width, view.height)
        val anim: Animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 200f, finalRadius.toFloat())
        anim.start()
    }
}

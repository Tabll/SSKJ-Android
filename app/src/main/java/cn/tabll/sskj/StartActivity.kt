package cn.tabll.sskj

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import kotlinx.android.synthetic.main.activity_start.*
import org.jetbrains.anko.*
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION



class StartActivity : AppCompatActivity() {

    private var log = AnkoLogger<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        log.info("启动")

        val decorView = window.decorView
        val uiOptions = (SYSTEM_UI_FLAG_HIDE_NAVIGATION or SYSTEM_UI_FLAG_IMMERSIVE_STICKY or SYSTEM_UI_FLAG_FULLSCREEN)
        decorView.systemUiVisibility = uiOptions

        showAlphaAnimation()
        launchMainActivity()
    }

    override fun onRestart() {
        startActivity<MainActivity>() //跳转至主页面
        super.onRestart()
    }

    private fun showAlphaAnimation(){
        log.info("渐变动画开始")
        val alphaShowLaunchImage = AlphaAnimation(0.3f,1.0f) //渐变动画
        alphaShowLaunchImage.duration = 450 //持续时间
        alphaShowLaunchImage.interpolator = AccelerateInterpolator() //动画速度：加速动画
        launchImageView.startAnimation(alphaShowLaunchImage) //启动动画
    }

    private fun launchMainActivity(){
        log.info("准备跳转")
        doAsync {
            Thread.sleep(500) //休眠0.5秒
            uiThread {
                startActivity<MainActivity>() //跳转至主页面
            }
        }
    }
}

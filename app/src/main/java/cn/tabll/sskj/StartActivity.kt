package cn.tabll.sskj

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.*

class StartActivity : AppCompatActivity() {

    private var log = AnkoLogger<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        log.info("启动")

        launchMainActivity()
    }

    override fun onRestart() {
        startActivity<MainActivity>() //跳转至主页面
        super.onRestart()
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

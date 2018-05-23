package cn.tabll.sskj

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class PersonalInfoActivity : AppCompatActivity() {

    val log = AnkoLogger<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        log.info("个人信息页已启动")
    }
}

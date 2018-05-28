package cn.tabll.sskj

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.tabll.sskj.R.id.*
import cn.tabll.sskj.data.SharedPreferencesMaker
import kotlinx.android.synthetic.main.activity_personal_info.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick

class PersonalInfoActivity : AppCompatActivity() {

    private val log = AnkoLogger<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        init()
        log.info("个人信息页已启动")
    }


    private fun init(){
        sign_out_cardView.onClick {
            deleteInfo()
            log.info("已退出登陆")
            finish()
        }

        user_name_textView.text = SharedPreferencesMaker().readSharedPreferencesFromKey(this, "UserName")

        user_info_main_cardView.onLongClick {
            finish()
        }
    }

    private fun deleteInfo(){
        val sharedPreferencesMaker = SharedPreferencesMaker()
        sharedPreferencesMaker.removeSharedPreferences(this, "UserName")
        sharedPreferencesMaker.removeSharedPreferences(this, "Token")
        sharedPreferencesMaker.removeSharedPreferences(this, "State")
    }
}

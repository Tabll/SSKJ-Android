package cn.tabll.sskj.views

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.tabll.sskj.LoginActivity
import cn.tabll.sskj.PersonalInfoActivity
import cn.tabll.sskj.R
import cn.tabll.sskj.TalkActivity
import cn.tabll.sskj.data.SharedPreferencesMaker
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find

class MineFragment : Fragment() {

    private val log = AnkoLogger<String>()

    private fun isUserSignIn(): Boolean{
        return when (SharedPreferencesMaker().readSharedPreferencesFromKey(ctx,"State")){
            "1" -> true
            else -> false
        }
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            if (isUserSignIn()){
                log.info("登陆状态检查：已登陆")
                uiThread {
                    changeSignInInfo()
                }
            }else{
                log.info("登陆状态检查：未登陆")
            }
        }
    }

    private fun changeSignInInfo(){
        find<TextView>(R.id.mineFragment_infoTextView).text = SharedPreferencesMaker().readSharedPreferencesFromKey(ctx,"UserName")
        log.verbose("登陆信息已更新")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                appBarLayout {
                    //android:theme = @style/AppTheme.AppBarOverlay //not support attribute
                    toolbar {
                        backgroundColor = resources.getColor(R.color.colorPrimary)
                        title = "我的账户"
                        setTitleTextColor(Color.WHITE)
                        textView {
                            text = resources.getString(R.string.developer_website)
                            textColor = Color.WHITE
                            textSize = 20f //sp
                            setTypeface(typeface, Typeface.BOLD)
                        }.lparams {
                            gravity = Gravity.CENTER
                        }
                        //app:popupTheme = @style/AppTheme.PopupOverlay //not support attribute
                    }.lparams(width = matchParent)
                }.lparams(width = matchParent)
                cardView {
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        imageView {
                            //android:contentDescription = @string/shop //not support attribute
                            imageResource = R.drawable.ic_company_logo
                            scaleType = ImageView.ScaleType.FIT_START
                            onClick {
                                if (isUserSignIn()){
                                    startActivity<PersonalInfoActivity>()
                                }else{
                                    startActivity<LoginActivity>()
                                }
                            }
                        }.lparams(width = dip(120), height = dip(120)) {
                            gravity = Gravity.CENTER
                        }
                        textView {
                            id = R.id.mineFragment_infoTextView
                            text = "请登陆"
                        }.lparams {
                            gravity = Gravity.CENTER_HORIZONTAL
                            bottomMargin = dip(20)
                        }
                    }.lparams(width = matchParent, height = matchParent)
                }.lparams(width = matchParent)

                cardView {
                    onClick {
                        toast("暂无此功能")
                    }
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        imageView {
                            padding = dip(18)
                            imageResource = R.drawable.ic_navigation_mine_how_to_use_products
                            scaleType = ImageView.ScaleType.FIT_START
                        }.lparams(width = dip(60), height = dip(60)) {
                            gravity = Gravity.CENTER
                        }
                        textView {
                            gravity = Gravity.CENTER
                            text = resources.getString(R.string.how_to_use_products)
                        }.lparams(width = matchParent, height = matchParent) {
                            gravity = Gravity.CENTER
                        }
                    }.lparams(width = matchParent, height = matchParent) {
                        marginStart = dip(90)
                        marginEnd = dip(90)
                    }
                }.lparams(width = matchParent) {
                    topMargin = dip(20)
                    bottomMargin = dip(20)
                }
                cardView {
                    onClick {
                        toast("暂无此功能")
                    }
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        imageView {
                            padding = dip(18)
                            imageResource = R.drawable.ic_navigation_mine_connect_my_products
                            scaleType = ImageView.ScaleType.FIT_START
                        }.lparams(width = dip(60), height = dip(60)) {
                            gravity = Gravity.CENTER
                        }
                        textView {
                            gravity = Gravity.CENTER
                            text = resources.getString(R.string.connect_my_products)
                        }.lparams(width = matchParent, height = matchParent) {
                            gravity = Gravity.CENTER
                        }
                    }.lparams(width = matchParent, height = matchParent) {
                        marginStart = dip(90)
                        marginEnd = dip(90)
                    }
                }.lparams(width = matchParent)
                cardView {
                    onClick {
                        toast("暂无此功能")
                    }
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        imageView {
                            padding = dip(18)
                            imageResource = R.drawable.ic_navigation_mine_order_history
                            scaleType = ImageView.ScaleType.FIT_START
                        }.lparams(width = dip(60), height = dip(60)) {
                            gravity = Gravity.CENTER
                        }
                        textView {
                            gravity = Gravity.CENTER
                            text = resources.getString(R.string.order_history)
                        }.lparams(width = matchParent, height = matchParent) {
                            gravity = Gravity.CENTER
                        }
                    }.lparams(width = matchParent, height = matchParent) {
                        marginStart = dip(90)
                        marginEnd = dip(90)
                    }
                }.lparams(width = matchParent)
                cardView {
                    onClick {
                        toast("暂无此功能")
                    }
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        imageView {
                            padding = dip(18)
                            imageResource = R.drawable.ic_navigation_mine_custom_made_business
                            scaleType = ImageView.ScaleType.FIT_START
                        }.lparams(width = dip(60), height = dip(60)) {
                            gravity = Gravity.CENTER
                        }
                        textView {
                            gravity = Gravity.CENTER
                            text = resources.getString(R.string.custom_made_business)
                        }.lparams(width = matchParent, height = matchParent) {
                            gravity = Gravity.CENTER
                        }
                    }.lparams(width = matchParent, height = matchParent) {
                        marginStart = dip(90)
                        marginEnd = dip(90)
                    }
                }.lparams(width = matchParent)
                cardView {
                    onClick {
                        startActivity<TalkActivity>()
                    }
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        imageView {
                            padding = dip(18)
                            //android:contentDescription = @string/shop //not support attribute
                            imageResource = R.drawable.ic_navigation_mine_contact_service
                            scaleType = ImageView.ScaleType.FIT_START
                        }.lparams(width = dip(60), height = dip(60)) {
                            gravity = Gravity.CENTER
                        }
                        textView {
                            gravity = Gravity.CENTER
                            text = resources.getString(R.string.contact_service)
                        }.lparams(width = matchParent, height = matchParent) {
                            gravity = Gravity.CENTER
                        }
                    }.lparams(width = matchParent, height = matchParent) {
                        marginStart = dip(90)
                        marginEnd = dip(90)
                    }
                }.lparams(width = matchParent)
            }
        }.view
    }
}

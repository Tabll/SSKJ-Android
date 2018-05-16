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
import cn.tabll.sskj.LoginActivity
import cn.tabll.sskj.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class MineFragment : Fragment() {
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
                            //centerInParent()
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
                                startActivity<LoginActivity>()
                            }
                        }.lparams(width = dip(120), height = dip(120)) {
                            gravity = Gravity.CENTER
                        }
                        textView {
                            text = "上善已与您相伴100天"
                        }.lparams {
                            gravity = Gravity.CENTER_HORIZONTAL
                            bottomMargin = dip(20)
                        }
                    }.lparams(width = matchParent, height = matchParent)
                }.lparams(width = matchParent)
                //recyclerView {
                //}.lparams(width = matchParent, height = matchParent)
            }
        }.view
    }
}
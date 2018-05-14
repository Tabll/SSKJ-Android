package cn.tabll.sskj.views

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import cn.tabll.sskj.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.nestedScrollView

class ShopFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                appBarLayout {
                    //android:theme = @style/AppTheme.AppBarOverlay //not support attribute
                    toolbar {
                        backgroundColor = resources.getColor(R.color.colorPrimary)
                        title = "商城"
                        setTitleTextColor(Color.WHITE)
                        //app:popupTheme = @style/AppTheme.PopupOverlay //not support attribute
                    }.lparams(width = matchParent)
                }.lparams(width = matchParent)
                nestedScrollView {
                    imageView {
                        //android:contentDescription = @string/shop //not support attribute
                        imageResource = R.drawable.shop_image
                        scaleType = ImageView.ScaleType.FIT_START
                    }.lparams(width = matchParent)
                }.lparams(width = matchParent, height = matchParent)
            }
        }.view
    }
}
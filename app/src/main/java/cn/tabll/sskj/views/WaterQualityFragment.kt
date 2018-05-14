package cn.tabll.sskj.views

import android.os.Bundle
import android.support.design.R.attr.srcCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cn.tabll.sskj.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class WaterQualityFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                imageView (R.drawable.water_quality_image){
                    scaleType = ImageView.ScaleType.FIT_START
                    //android:contentDescription = @string/water_quality //not support attribute
                }.lparams(width = dip(0)) {
                    weight = 1f
                }
            }
        }.view
    }
}
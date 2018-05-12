package cn.tabll.sskj.views

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.tabll.sskj.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.nestedScrollView

class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                coordinatorLayout {
                    fitsSystemWindows = true
                    appBarLayout {
                        fitsSystemWindows = true
                        collapsingToolbarLayout {
                            //id = R.id.toolbarlayout
                            contentScrim = resources.getDrawable(R.color.colorPrimary, null)
                            fitsSystemWindows = true

                            textView {
                                text = resources.getString(R.string.water_quality_over_all)
                                textColor = Color.WHITE
                            }.lparams {
                                gravity = Gravity.CENTER_HORIZONTAL
                                topMargin = dip(20)
                            }

                            //imageView {
                            //    imageResource = R.drawable.launch_image
                            //    scaleType = ImageView.ScaleType.CENTER_CROP
                            //}.lparams{
                            //    collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                            //    parallaxMultiplier = 0.7f
                            //}

                            //---app:layout_scrollFlags = scroll|exitUntilCollapsed //not support attribute
                            toolbar {

                                //---app:layout_collapseMode = pin //not support attribute
                            }.lparams(width = matchParent, height = dip(56)){
                                collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                            }
                        }.lparams(width = matchParent, height = dip(300)){
                            scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED

                        }
                    }.lparams(width = matchParent)
                    nestedScrollView {

                        listView {
                            id = R.id.main_fragment_listView
                        }.lparams(width = matchParent, height = matchParent)

                        //---app:layout_behavior = @string/appbar_scrolling_view_behavior //not support attribute
                    }.lparams(width = matchParent, height = matchParent){
                        behavior = AppBarLayout.ScrollingViewBehavior()
                    }
                }.lparams(width = matchParent, height = matchParent)
            }
        }.view
    }
}

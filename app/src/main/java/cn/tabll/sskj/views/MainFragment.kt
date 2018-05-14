package cn.tabll.sskj.views

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.tabll.sskj.Listener.AppBarStateChangeListener
import cn.tabll.sskj.R
import kotlinx.android.synthetic.main.view_water_wave.*
import kotlinx.android.synthetic.main.view_water_wave.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.nestedScrollView

class MainFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        water_wave_view.startWave()
    }

    override fun onPause() {
        super.onPause()
        water_wave_view.stopWave()
    }

    override fun onDestroy() {
        super.onDestroy()
        water_wave_view.stopWave()
    }

    override fun onStop() {
        super.onStop()
        water_wave_view.stopWave()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                coordinatorLayout {
                    fitsSystemWindows = true
                    appBarLayout {
                        fitsSystemWindows = true
                        val appBarStateChangeListener = object: AppBarStateChangeListener(){
                            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                                Log.d("STATE", state.name)
                                when(state) {
                                    State.EXPANDED -> water_wave_view.startWave()
                                    State.COLLAPSED -> water_wave_view.stopWave()
                                    else -> return
                                }
                                //if( state == State.EXPANDED ) {
                                //    water_wave_view.startWave()
                                //}else if(state == State.COLLAPSED){
                                //    water_wave_view.stopWave()
                                //}else {
                                //    //water_wave_view.stopWave()
                                //}
                            }
                        }
                        addOnOffsetChangedListener(appBarStateChangeListener)

                        collapsingToolbarLayout {
                            id = R.id.fragment_main_toolbarLayout
                            contentScrim = resources.getDrawable(R.color.colorPrimary, null)
                            fitsSystemWindows = true

                            //WaterWaveView(this.context).lparams(width = dip(100), height = dip(100))
                            include<View>(R.layout.view_water_wave)
                            //waterWaveView.startWave()
                            //water_wave_view.stopWave()
                            //water_wave_view.setWaterLevel(0.6f)
                            //water_wave_view.startWave()
                            textView {
                                text = resources.getString(R.string.water_quality_over_all)
                                textColor = Color.WHITE
                            }.lparams {
                                gravity = Gravity.CENTER_HORIZONTAL
                                topMargin = dip(20)
                            }
                            //waterWaveView.setmWaterLevel(0.7f)
                            //waterWaveView.startWave()
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

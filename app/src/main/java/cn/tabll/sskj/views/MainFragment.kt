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
import cn.tabll.sskj.listener.AppBarStateChangeListener
import cn.tabll.sskj.R
import kotlinx.android.synthetic.main.view_water_wave.*
import kotlinx.android.synthetic.main.view_water_wave.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.nestedScrollView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainFragment : Fragment() {

    fun stopWave() {
        this.water_wave_view.stopWave()
    }

    fun startWave() {
        this.water_wave_view.startWave()
    }

    override fun onStart() {
        super.onStart()
        water_wave_view.setWaterLevel(0.7f)
        water_wave_view.startWave()
    }

    override fun onPause() {
        super.onPause()
        water_wave_view.stopWave()
    }

    //override fun onDestroy() {
    //    super.onDestroy()
    //}
    //override fun onStop() {
    //    super.onStop()
    //}

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
                            }
                        }
                        addOnOffsetChangedListener(appBarStateChangeListener)

                        collapsingToolbarLayout {
                            id = R.id.fragment_main_toolbarLayout
                            contentScrim = resources.getDrawable(R.color.colorPrimary, null)
                            fitsSystemWindows = true

                            include<View>(R.layout.view_water_wave)

                            textView {
                                text = resources.getString(R.string.water_quality_over_all)
                                textColor = Color.WHITE
                            }.lparams {
                                gravity = Gravity.CENTER_HORIZONTAL
                                topMargin = dip(20)
                            }

                            toolbar {

                            }.lparams(width = matchParent, height = dip(56)){
                                collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                            }
                        }.lparams(width = matchParent, height = dip(300)){
                            scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                        }
                    }.lparams(width = matchParent)
                    nestedScrollView {
                        swipeRefreshLayout {
                            recyclerView {

                            }
                        }.lparams(width = matchParent, height = matchParent)

                        //listView {
                        //    id = R.id.main_fragment_listView
                        //}.lparams(width = matchParent, height = matchParent)

                    }.lparams(width = matchParent, height = matchParent){
                        behavior = AppBarLayout.ScrollingViewBehavior()
                    }
                }.lparams(width = matchParent, height = matchParent)
            }
        }.view
    }
}

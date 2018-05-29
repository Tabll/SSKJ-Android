package cn.tabll.sskj.views

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.*
import cn.tabll.sskj.listener.AppBarStateChangeListener
import cn.tabll.sskj.R
import cn.tabll.sskj.adapters.MainFragmentProductionAdapter
import cn.tabll.sskj.data.SharedPreferencesMaker
import cn.tabll.sskj.https.HttpConnectors
import cn.tabll.sskj.objects.ProductionInformation
import cn.tabll.sskj.tools.AdapterItemTouchCallbackHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.view_water_wave.*
import kotlinx.android.synthetic.main.view_water_wave.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.util.ArrayList

class MainFragment : Fragment() {

    private val log = AnkoLogger<String>()

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

    private fun getProductionsInformation(): ArrayList<ProductionInformation>{
        log.info("开始获取设备信息")
        val httpConnector = HttpConnectors()
        val codes = mapOf(
                "user-phone-number" to SharedPreferencesMaker().readSharedPreferencesFromKey(ctx, "UserName"),
                "token" to SharedPreferencesMaker().readSharedPreferencesFromKey(ctx, "Token"))
        val jsonCodes = httpConnector.httpPost("https://www.tabll.cn/sskjapi/productions.php", codes, "utf-8")
        val turnsType = object : TypeToken<List<ProductionInformation>>() {}.type
        return Gson().fromJson<ArrayList<ProductionInformation>>(jsonCodes, turnsType) //解析
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var mySwipeRefreshLayout = SwipeRefreshLayout(ctx)

        var adapter = MainFragmentProductionAdapter(ArrayList())

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
                                    State.EXPANDED -> {
                                        water_wave_view.startWave()
                                        mySwipeRefreshLayout.isEnabled = true
                                    }
                                    State.COLLAPSED -> {
                                        water_wave_view.stopWave()
                                        mySwipeRefreshLayout.isEnabled = false
                                    }
                                    else -> {
                                        mySwipeRefreshLayout.isEnabled = false
                                        return
                                    }
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
                    mySwipeRefreshLayout = swipeRefreshLayout {
                        id = R.id.mainFragment_refreshLayout
                        setColorSchemeColors(resources.getColor(R.color.colorPrimary))
                        onRefresh {
                            doAsync {
                                val data = getProductionsInformation()
                                uiThread {
                                    adapter.dataChanged(data)
                                    isRefreshing = false
                                    log.info("数据已刷新")
                                }
                            }
                        }
                        recyclerView {
                            layoutManager = LinearLayoutManager(ctx)
                            itemAnimator = DefaultItemAnimator()
                            doAsync {
                                adapter = MainFragmentProductionAdapter(getProductionsInformation())
                                uiThread{
                                    setAdapter(adapter)
                                    ItemTouchHelper(AdapterItemTouchCallbackHelper(adapter)).attachToRecyclerView(this@recyclerView)
                                }
                            }
                        }
                    }.lparams(width = matchParent, height = matchParent){
                        behavior = AppBarLayout.ScrollingViewBehavior()
                    }
                }.lparams(width = matchParent, height = matchParent)
            }
        }.view
    }
}

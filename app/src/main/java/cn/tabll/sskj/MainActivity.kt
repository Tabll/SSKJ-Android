package cn.tabll.sskj

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import cn.tabll.sskj.adapters.ViewPagerAdapter
import cn.tabll.sskj.tools.BottomNavigationViewHelper
import cn.tabll.sskj.views.MainFragment
import cn.tabll.sskj.views.MineFragment
import cn.tabll.sskj.views.ShopFragment
import cn.tabll.sskj.views.WaterQualityFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var log = AnkoLogger<String>()
    private val mainFragment = MainFragment()
    private var isWaterWaveStarted: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        //fab.setOnClickListener { view ->
        //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //            .setAction("Action", null).show()
        //}

        //val toggle = ActionBarDrawerToggle(
        //        this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        //drawer_layout.addDrawerListener(toggle)
        //toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        init() //初始化
        log.info("主页面已启动")
    }

    private fun init(){
        val bottomNavigationViewHelper = BottomNavigationViewHelper()
        bottomNavigationViewHelper.disableShiftMode(bottom_navigation_view) //解决底部导航栏问题

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_main_page -> {
                    view_pager.currentItem = 0
                    mainFragment.startWave()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_water_quality -> {
                    view_pager.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_shop -> {
                    view_pager.currentItem = 2
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_mine -> {
                    view_pager.currentItem = 3
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener) //底部导航栏点击监听

        val mOnPageChangeListener = object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) { }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }
            override fun onPageSelected(position: Int) {
                //bottom_navigation_view.selectedItemId = position
                bottom_navigation_view.selectedItemId = when (position){
                    0 -> R.id.action_main_page
                    1 -> R.id.action_water_quality
                    2 -> R.id.action_shop
                    3 -> R.id.action_mine
                    else -> {
                        log.error("ViewPage超出底部导航栏个数")
                        R.id.action_main_page
                    }
                }
                when (position){
                    0 -> {
                        mainFragment.startWave()
                        isWaterWaveStarted = true
                    }
                    else -> {
                        if (isWaterWaveStarted){
                            mainFragment.stopWave()
                            isWaterWaveStarted = false
                        }
                    }
                }
            }
        }
        view_pager.addOnPageChangeListener(mOnPageChangeListener) //ViewPager事件监听
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(mainFragment)
        viewPagerAdapter.addFragment(WaterQualityFragment())
        viewPagerAdapter.addFragment(ShopFragment())
        viewPagerAdapter.addFragment(MineFragment())
        view_pager.adapter = viewPagerAdapter //ViewPager适配器
    }

    //override fun onStart() {
    //    super.onStart()
    //    //mainFragment.startWave()
    //}

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    //override fun onCreateOptionsMenu(menu: Menu): Boolean {
    //    // Inflate the menu; this adds items to the action bar if it is present.
    //    menuInflater.inflate(R.menu.main, menu)
    //    return true
    //}

    //override fun onOptionsItemSelected(item: MenuItem): Boolean {
    //    // Handle action bar item clicks here. The action bar will
    //    // automatically handle clicks on the Home/Up button, so long
    //    // as you specify a parent activity in AndroidManifest.xml.
    //    when (item.itemId) {
    //        R.id.action_settings -> return true
    //        else -> return super.onOptionsItemSelected(item)
    //    }
    //}

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

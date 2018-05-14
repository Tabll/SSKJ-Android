package cn.tabll.sskj.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import cn.tabll.sskj.views.MainFragment
import cn.tabll.sskj.views.MineFragment
import cn.tabll.sskj.views.ShopFragment
import cn.tabll.sskj.views.WaterQualityFragment

class ViewPagerAdapter(fm: android.support.v4.app.FragmentManager?) : FragmentPagerAdapter(fm) {


    var fragments: MutableList<Fragment> = ArrayList<Fragment>()

    //private val fragmentList = List<Fragment>(4,MainFragment() as Fragment, WaterQualityFragment(), ShopFragment(), MineFragment())

    //private val fragmentList = listOf(MainFragment(), WaterQualityFragment(), ShopFragment(), MineFragment())

    fun addFragment(fragment: Fragment) {
        fragments.add(fragments.size, fragment)
    }

    override fun getItem(position: Int): Fragment {

        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }


}
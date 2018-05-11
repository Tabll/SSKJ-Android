package cn.tabll.sskj.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import cn.tabll.sskj.views.MainFragment
import cn.tabll.sskj.views.MineFragment
import cn.tabll.sskj.views.ShopFragment
import cn.tabll.sskj.views.WaterQualityFragment

class ViewPagerAdapter(fm: android.support.v4.app.FragmentManager?) : FragmentPagerAdapter(fm) {

    private val fragmentList = listOf(MainFragment(), WaterQualityFragment(), ShopFragment(), MineFragment())

    override fun getItem(position: Int): Fragment {

        return fragmentList[position]
    }

    override fun getCount(): Int {
        return 4
    }


}
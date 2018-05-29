package cn.tabll.sskj.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter

/**
 * 主页面 ViewPager 适配器
 **/

class ViewPagerAdapter(fm: android.support.v4.app.FragmentManager?) : FragmentPagerAdapter(fm) {

    private var fragments: MutableList<Fragment> = ArrayList()

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

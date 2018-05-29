package cn.tabll.sskj.tools

import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.annotation.SuppressLint
import android.util.Log

/**
 * 底部的 BottomNavigationView 修复（如果没有它超过三个以上View的时候就会只显示一个的文字）
 **/

class BottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    fun disableShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShiftingMode(false)
                //再次设置检查值，因此视图将被更新
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e("BNVHelper", "无法获得移位模式字段", e)
        } catch (e: IllegalAccessException) {
            Log.e("BNVHelper", "无法更改移位模式的值", e)
        }
    }
}

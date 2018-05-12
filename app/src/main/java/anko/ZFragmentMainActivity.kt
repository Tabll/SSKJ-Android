package anko

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.*
import org.jetbrains.anko.appcompat.v7.*

import cn.tabll.sskj.R
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout

/**
 * Generate with Plugin
 * @plugin Kotlin Anko Converter For Xml
 * @version 1.2.1
 */
class ZFragmentMainActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		linearLayout {
			coordinatorLayout {
				//android:fitsSystemWindows = true //not support attribute
				appBarLayout {
					//android:fitsSystemWindows = true //not support attribute
					collapsingToolbarLayout {
						id = R.id.fragment_main_toolbarLayout
						//android:fitsSystemWindows = true //not support attribute
						//app:layout_scrollFlags = scroll|exitUntilCollapsed //not support attribute
						textView {
							text = resources.getString(R.string.water_quality_over_all)
							textColor = Color.WHITE
						}.lparams {
							gravity = Gravity.CENTER_HORIZONTAL
							topMargin = dip(20)
						}
						//toolbar {
						//	id = R.id.fragment_main_toolbar
						//	//app:layout_collapseMode = pin //not support attribute
						//}.lparams(width = matchParent, height = dip(56))
					}.lparams(width = matchParent, height = dip(300))
				}.lparams(width = matchParent)
				nestedScrollView {
					//app:layout_behavior = @string/appbar_scrolling_view_behavior //not support attribute
					listView {
						id = R.id.main_fragment_listView
					}.lparams(width = matchParent, height = matchParent)
				}.lparams(width = matchParent, height = matchParent)
			}.lparams(width = matchParent, height = matchParent)
		}
	}
}

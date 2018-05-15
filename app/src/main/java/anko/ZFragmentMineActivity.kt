package anko

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.graphics.Color
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.*

import cn.tabll.sskj.R
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Generate with Plugin
 * @plugin Kotlin Anko Converter For Xml
 * @version 1.2.1
 */
class ZFragmentMineActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		linearLayout {
			orientation = LinearLayout.VERTICAL
			appBarLayout {
				//android:theme = @style/AppTheme.AppBarOverlay //not support attribute
				//toolbar {
				//	backgroundColor = resources.getColor(R.color.colorPrimary)
				//	//app:popupTheme = @style/AppTheme.PopupOverlay //not support attribute
				//}.lparams(width = matchParent)
			}.lparams(width = matchParent)
			cardView {
				imageView {
					//android:contentDescription = @string/shop //not support attribute
					imageResource = R.drawable.ic_company_logo
					scaleType = ImageView.ScaleType.FIT_START
				}.lparams(width = dip(120), height = dip(120))
			}.lparams(width = matchParent)
			recyclerView {
			}.lparams(width = matchParent, height = matchParent)
		}
	}
}

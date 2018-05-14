package anko

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.*
import org.jetbrains.anko.appcompat.v7.*

import cn.tabll.sskj.R
import org.jetbrains.anko.design.appBarLayout

/**
 * Generate with Plugin
 * @plugin Kotlin Anko Converter For Xml
 * @version 1.2.1
 */
class ZFragmentShopActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		linearLayout {
			orientation = LinearLayout.VERTICAL
			appBarLayout {
				//android:theme = @style/AppTheme.AppBarOverlay //not support attribute
				//toolbar {
				//	//app:title = @string/shop //not support attribute
				//	backgroundColor = resources.getColor(R.color.colorPrimary)
				//	//app:popupTheme = @style/AppTheme.PopupOverlay //not support attribute
				//	textView {
				//		text = resources.getString(R.string.developer_website)
				//		textColor = Color.WHITE
				//		textSize = 20f //sp
				//		setTypeface(typeface, Typeface.BOLD)
				//	}.lparams {
				//		centerInParent()
				//		gravity = Gravity.CENTER
				//	}
				//}.lparams(width = matchParent)
			}.lparams(width = matchParent)
			nestedScrollView {
				imageView {
					//android:contentDescription = @string/shop //not support attribute
					imageResource = R.drawable.shop_image
					scaleType = ImageView.ScaleType.FIT_START
				}.lparams(width = matchParent)
			}.lparams(width = matchParent, height = matchParent)
		}
	}
}

package anko

import android.app.Activity
import android.os.Bundle
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.*
import org.jetbrains.anko.appcompat.v7.*

import cn.tabll.sskj.R

/**
 * Generate with Plugin
 * @plugin Kotlin Anko Converter For Xml
 * @version 1.2.1
 */
class ZFragmentWaterQualityActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		linearLayout {
			imageView {
				//android:contentDescription = @string/water_quality //not support attribute
				//app:srcCompat = @drawable/water_quality_image //not support attribute
			}.lparams(width = dip(0)) {
				weight = 1f
			}
		}
	}
}

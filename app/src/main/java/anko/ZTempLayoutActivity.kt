package anko

import android.app.Activity
import android.os.Bundle
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.*

import cn.tabll.sskj.R

/**
 * Generate with Plugin
 * @plugin Kotlin Anko Converter For Xml
 * @version 1.2.1
 */
class ZTempLayoutActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		linearLayout {
			button {
				id = R.id.button
				text = "Button"
			}.lparams {
				weight = 1f
			}
			button {
				id = R.id.button2
				text = "Button"
			}.lparams {
				weight = 1f
			}
		}
	}
}

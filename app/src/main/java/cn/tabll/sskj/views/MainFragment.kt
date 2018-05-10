package cn.tabll.sskj.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.tabll.sskj.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
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
        }.view
    }
}

package cn.tabll.sskj.listener

import android.support.design.widget.AppBarLayout

/**
 * 主页面 AppBar 展开与收缩监听器
 **/

abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener{

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private var mCurrentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        when{
            i == 0 -> {
                mCurrentState = when(mCurrentState) {
                    State.EXPANDED ->  State.EXPANDED
                    else -> {
                        onStateChanged(appBarLayout, State.EXPANDED)
                        State.EXPANDED
                    }
                }
            }
            Math.abs(i) >= appBarLayout.totalScrollRange -> {
                mCurrentState = when(mCurrentState) {
                    State.COLLAPSED -> State.COLLAPSED
                    else -> {
                        onStateChanged(appBarLayout, State.COLLAPSED)
                        State.COLLAPSED
                    }
                }
            }
            else -> {
                mCurrentState = when(mCurrentState) {
                    State.IDLE -> State.IDLE
                    else -> {
                        onStateChanged(appBarLayout, State.IDLE)
                        State.IDLE
                    }
                }
            }
        }
    }
    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)
}

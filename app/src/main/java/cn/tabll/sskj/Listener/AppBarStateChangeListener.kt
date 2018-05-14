package cn.tabll.sskj.listener

import android.support.design.widget.AppBarLayout

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
        //if (i == 0) {
        //    if (mCurrentState != State.EXPANDED) {
        //        onStateChanged(appBarLayout, State.EXPANDED)
        //    }
        //    mCurrentState = State.EXPANDED
        //} else if (Math.abs(i) >= appBarLayout.totalScrollRange) {
        //    if (mCurrentState != State.COLLAPSED) {
        //        onStateChanged(appBarLayout, State.COLLAPSED)
        //    }
        //    mCurrentState = State.COLLAPSED
        //} else {
        //    if (mCurrentState != State.IDLE) {
        //        onStateChanged(appBarLayout, State.IDLE)
        //    }
        //    mCurrentState = State.IDLE
        //}
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)
}
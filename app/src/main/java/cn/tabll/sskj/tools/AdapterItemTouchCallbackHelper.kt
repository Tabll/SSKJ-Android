package cn.tabll.sskj.tools

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class AdapterItemTouchCallbackHelper (private val adapter: AdapterItemTouchHelper) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val swipeFlags: Int = ItemTouchHelper.START or ItemTouchHelper.END
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        adapter.onItemMove(viewHolder!!.adapterPosition, target!!.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        //adapter.onItemDismiss(viewHolder!!.adapterPosition)
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }
}

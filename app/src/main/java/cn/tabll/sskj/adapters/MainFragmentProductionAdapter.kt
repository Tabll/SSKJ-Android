package cn.tabll.sskj.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.tabll.sskj.R
import cn.tabll.sskj.objects.ProductionInformation
import cn.tabll.sskj.tools.AdapterItemTouchHelper
import kotlinx.android.synthetic.main.adapter_items_main_fragment.view.*
import java.util.*

class MainFragmentProductionAdapter(private var productions: ArrayList<ProductionInformation>): Adapter<MainFragmentProductionAdapter.MyHolder>(), AdapterItemTouchHelper {

    inner class MyHolder(itemView: View?): RecyclerView.ViewHolder(itemView){
        fun bind(position: Int){
            updateView(itemView, position)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        //    notifyItemChanged(fromPosition)
        //    notifyItemChanged(toPosition)
//
        //    Collections.swap(productions, fromPosition, toPosition)
//
        //    SelfChoiceList.changeSelfChoiceList(fromPosition, toPosition)
        //    SelfChoiceList.saveSelfChoice(additionalView!!.context)

        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        //    if (openedItem == position){
        //        openedCard!!.context.startActivity<StockMoreInfoActivity>("position" to SelfChoiceList.getStockCodeByID(position))
        //    }
//
        //    if (productions.size > 5 && openedItem != -1){
        //        openedCard?.simpleCardView?.removeView(additionalView)
        //        openedCard?.simpleCardView?.layoutParams?.height = Value().px80
        //        openedCard = null
        //        openedItem = -1
        //    }
        //    //notifyItemChanged(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_items_main_fragment, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = productions.size

    private fun updateView(view: View, position: Int){
        //find<String>(R.string.production_name_is)
        //find

        view.production_name_textView.text = String.format(view.resources.getString(R.string.production_name_is), productions[position].name)
        view.production_type_textView.text = String.format(view.resources.getString(R.string.production_type_is), productions[position].type)
        view.production_state_textView.text = String.format(view.resources.getString(R.string.production_state_is), productions[position].state)

        //view.simpleCardView.setOnClickListener { itemClickListener(view, position) }
        //view.stockNameTextView.text = items!![position].stockName
        //view.stockPriceTextView.text = items!![position].nowPrice
        //view.stockChangingPriceTextView.text = items!![position].changingPrice
        //view.stockChangingPercentTextView.text = items!![position].changingPercent
        //if (!items!![position].isUp){
        //    view.stockPriceTextView.textColor = Color.parseColor("#218E56")
        //    view.stockChangingPriceTextView.textColor = Color.parseColor("#218E56")
        //    view.stockChangingPercentTextView.textColor = Color.parseColor("#218E56")
        //}else{
        //    view.stockPriceTextView.textColor = Color.parseColor("#EE3E3E")
        //    view.stockChangingPriceTextView.textColor = Color.parseColor("#EE3E3E")
        //    view.stockChangingPercentTextView.textColor = Color.parseColor("#EE3E3E")
        //}
    }

    //private fun itemClickListener(view: View, position: Int) {
    //    updateAdditionalView(position)
    //    //view.context.toast("是" + position)
    //    when (openedItem != position) {
    //        true -> {
    //            view.simpleCardView.layoutParams.height = Value().px800
    //            if (openedItem != -1) {
    //                openedCard?.removeView(additionalView)
    //                openedCard?.layoutParams?.height = Value().px80
    //                notifyItemChanged(openedItem, 100)
    //            }
    //            view.simpleCardView.addView(additionalView)
    //            openedCard = view.simpleCardView
    //            openedItem = position
    //        }
    //        false -> {
    //            if (view.simpleCardView.layoutParams.height == Value().px80) {
    //                view.simpleCardView.addView(additionalView)
    //                view.simpleCardView.layoutParams.height = Value().px800
    //                openedCard = view.simpleCardView
    //                openedItem = position
    //            } else {
    //                view.simpleCardView.removeView(additionalView)
    //                view.simpleCardView.layoutParams.height = Value().px80
    //                openedCard = null
    //                openedItem = -1
    //            }
    //        }
    //    }
    //    notifyItemChanged(position, 100)
    //}

    //private fun itemLongClickListener(position: Int, view: View): Boolean {
    //    view.context.startActivity<StockMoreInfoActivity>("position" to position)
    //    return true
    //}

    //fun updateItems(newItems: ArrayList<Stock>){
    //    //items = newItems
    //    productions?.clear()
    //    productions?.addAll(newItems)
//
    //    if (productions!!.size > 5 && openedItem != -1){
    //        openedCard?.simpleCardView?.removeView(additionalView)
    //        openedCard?.simpleCardView?.layoutParams?.height = Value().px80
    //        openedCard = null
    //        openedItem = -1
    //    }
//
    //    notifyDataSetChanged()
    //}

    //fun addItems(newStock: Stock){
    //    productions!!.add(newStock)
    //    notifyItemInserted(productions!!.size)
    //}
}

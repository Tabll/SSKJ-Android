package cn.tabll.sskj.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import cn.tabll.sskj.R
import cn.tabll.sskj.objects.ProductionInformation
import cn.tabll.sskj.tools.AdapterItemTouchHelper
import kotlinx.android.synthetic.main.adapter_items_main_fragment.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputLayout
import kotlin.collections.ArrayList

/**
 * 主页面设备信息适配器
 **/

class MainFragmentProductionAdapter(private var productions: ArrayList<ProductionInformation>): Adapter<MainFragmentProductionAdapter.MyHolder>(), AdapterItemTouchHelper {

    inner class MyHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!){
        fun bind(position: Int){
            updateView(itemView, position)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        notifyItemMoved(fromPosition, toPosition)
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
        view.production_name_textView.text = String.format(view.resources.getString(R.string.production_name_is), productions[position].name)
        view.production_type_textView.text = String.format(view.resources.getString(R.string.production_type_is), productions[position].type)
        view.production_state_textView.text = String.format(view.resources.getString(R.string.production_state_is), productions[position].state)

        view.production_info_imageView.setOnClickListener{ infoImageClickListener(view, position) }
    }

    fun dataChanged(data: ArrayList<ProductionInformation>){
        productions.clear()
        productions.addAll(data)
        notifyDataSetChanged()
    }

    private fun infoImageClickListener(view: View, position: Int){
        var editTextView: EditText? = null
        view.context.alert {
            customView {
                linearLayout{
                    orientation = LinearLayout.VERTICAL
                    textInputLayout {
                        editTextView = editText (productions[position].name) {
                            hint = "自定义设备名称"
                            singleLine = true
                        }
                    }
                    textView {
                        padding = dip(5)
                        hint = "未知设备型号"
                        if (!productions[position].type.isEmpty()){
                            text = String.format(view.resources.getString(R.string.production_type_is), productions[position].type)
                        }
                    }
                    textView {
                        padding = dip(5)
                        hint = "设备状态异常"
                        if (!productions[position].state.isEmpty()) {
                            text = String.format(view.resources.getString(R.string.production_state_is), productions[position].state)
                        }
                    }
                    textView {
                        padding = dip(5)
                        hint = "无使用寿命信息"
                        if (!productions[position].wastage.isEmpty()) {
                            text = String.format(view.resources.getString(R.string.production_wastage_is), productions[position].wastage)
                        }
                    }
                    textView {
                        padding = dip(5)
                        hint = "无设备制造商信息"
                        if (!productions[position].manufacturer.isEmpty()) {
                            text = String.format(view.resources.getString(R.string.production_manufacturer_is), productions[position].manufacturer)
                        }
                    }
                    textView {
                        padding = dip(5)
                        hint = "设备ID信息出错"
                        if (!productions[position].id.isEmpty()) {
                            text = String.format(view.resources.getString(R.string.production_id_is), productions[position].id)
                        }
                    }
                    padding = dip(20)
                }
            }
            title = "设备信息"
            positiveButton("确定", {
                productions[position].name = editTextView!!.text.toString()
                notifyItemChanged(position)
                view.context.toast("已更改")
            })
        }.show()
    }
}

package cn.tabll.sskj.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import cn.tabll.sskj.R
import cn.tabll.sskj.objects.ChatMessages
import java.text.SimpleDateFormat
import java.util.*

class ChatMessageAdapter(context: Context, messages: MutableList<ChatMessages>) : BaseAdapter() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var chatMessages: List<ChatMessages> = messages

    override fun getCount(): Int {

        return chatMessages.size
    }

    override fun getItem(i: Int): Any {
        return chatMessages[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View? {
        //var view = view
        var myView: View? = view
        val chatMessage = chatMessages[i]
        val viewHolder: ViewHolder
        if (myView == null) {
            //通过ItemType设置不同的布局
            if (getItemViewType(i) == 0) {//假如为0，则是小笨的消息
                myView = layoutInflater.inflate(R.layout.from_message, viewGroup, false)
                viewHolder = ViewHolder()
                viewHolder.date = myView!!.findViewById(R.id.from_date)
                viewHolder.message = myView.findViewById(R.id.from_message_info)
            } else {//假如为1，则是发送的消息
                myView = layoutInflater.inflate(R.layout.to_message, viewGroup, false)
                viewHolder = ViewHolder()
                viewHolder.date = myView!!.findViewById(R.id.to_date)
                viewHolder.message = myView.findViewById(R.id.to_message_info)
            }
            myView.tag = viewHolder//存储一下ViewHolder
        } else {
            viewHolder = myView.tag as ViewHolder
        }
        //设置数据
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)//设置时间格式
        viewHolder.date!!.text = simpleDateFormat.format(chatMessage.date)//获取时间
        viewHolder.message!!.text = chatMessage.msg//获取消息
        return myView
    }

    private inner class ViewHolder {
        internal var date: TextView? = null
        internal var message: TextView? = null
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        val chatMessage = chatMessages[position]
        return if (chatMessage.type === ChatMessages.Type.INCOMING) {
            0//接收消息
        } else {
            1//发送消息
        }
    }
}
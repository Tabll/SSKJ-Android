package cn.tabll.sskj

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import cn.tabll.sskj.adapters.ChatMessageAdapter
import cn.tabll.sskj.https.Utils
import cn.tabll.sskj.objects.ChatMessages
import java.util.*
import kotlin.collections.ArrayList

class TalkActivity : AppCompatActivity() {

    private var XBMessages: ListView? = null
    private var chatMessageAdaper: ChatMessageAdapter? = null
    private var messages: MutableList<ChatMessages>? = null

    private var input_text: EditText? = null
    private var sendButton: Button? = null

    private val TAG = "XbActivity"

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            //等待接收子线程完成数据的返回

            val fromMessage = msg.obj as ChatMessages
            messages!!.add(fromMessage)
            chatMessageAdaper!!.notifyDataSetChanged()//表示数据源发送变化
            //super.handleMessage(msg);
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk)

        initView()//初始化所有的View
        initData()//初始化所有的数据
        initListener()//初始化事件
        Log.i(TAG, "小笨初始化已完成")
    }

    private fun initListener() {
        sendButton!!.setOnClickListener(View.OnClickListener {
            val toMessage = input_text!!.text.toString()
            if (TextUtils.isEmpty(toMessage)) {
                Toast.makeText(this, "发送消息为空", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            val tocMessage = ChatMessages()//将按钮中的数据也在List中显示
            tocMessage.date = Date()
            tocMessage.msg = toMessage
            tocMessage.type = ChatMessages.Type.OUTGOING
            messages!!.add(tocMessage)
            chatMessageAdaper!!.notifyDataSetChanged()//通知更新
            input_text!!.setText("")//清空输入框

            object : Thread() {
                override fun run() {
                    val fromMessage = Utils().sendMessage(toMessage)
                    val m = Message.obtain()
                    m.obj = fromMessage
                    handler.sendMessage(m)
                }
            }.start()
        })
    }

    private fun initData() {
        messages = ArrayList()
        //messages.add(new ChatMessage("你好", ChatMessage.Type.INCOMING, new Date()));
        //messages.add(new ChatMessage("你好", ChatMessage.Type.OUTCOMING, new Date()));
        chatMessageAdaper = ChatMessageAdapter(this, messages!!)

        XBMessages!!.adapter = chatMessageAdaper
    }

    private fun initView() {
        XBMessages = findViewById(R.id.XB_list)
        input_text = findViewById(R.id.import_message)
        sendButton = findViewById(R.id.send_button)
    }

    fun back(view: View) {
        this.finish()
    }
}

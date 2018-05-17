package cn.tabll.sskj.objects

import java.util.*

class ChatMessages {

    var name: String? = null
    var msg: String? = null
    var type: Type? = null
    var date: Date? = null

    //定义消息类型
    enum class Type {
        INCOMING, OUTGOING
    }
}
package cn.tabll.sskj.objects

import java.util.*

/**
 * 聊天消息
 **/

class ChatMessages {

    var name: String? = null
    var msg: String? = null
    var type: Type? = null
    var date: Date? = null

    //消息类型（发送/接收）
    enum class Type {
        INCOMING, OUTGOING
    }
}

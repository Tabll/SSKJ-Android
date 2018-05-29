package cn.tabll.sskj.https

import cn.tabll.sskj.objects.ChatMessages
import cn.tabll.sskj.objects.ChatMessagesResults
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URLEncoder
import java.util.*

/**
 * 图灵聊天机器人连接请求
 **/

class Utils {
    private val myURL = "http://www.tuling123.com/openapi/api"
    private val myAPIkey = "49f2b50b474174b487a9d88f3cea5ff2"
    fun sendMessage(msg: String): ChatMessages {
        val chatMessage = ChatMessages()
        val jsonRes = doGet(msg)
        val gson = Gson()
        val result: ChatMessagesResults?
        try {
            result = gson.fromJson(jsonRes, ChatMessagesResults::class.java)
            chatMessage.msg = result!!.text
        } catch (e: JsonSyntaxException) {
            chatMessage.msg = "客服正忙"
        }
        chatMessage.date = Date() //设置时间
        chatMessage.type = ChatMessages.Type.INCOMING //设置类型
        return chatMessage
    }

    private fun doGet(msg: String): String {
        var result = ""
        val url = setParams(msg)//设置参数，返回一个Url
        var thisInputStream: InputStream? = null
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            val urlNet = java.net.URL(url)
            val xb = urlNet.openConnection() as HttpURLConnection
            xb.readTimeout = 5000
            xb.connectTimeout = 5000
            xb.requestMethod = "GET"
            thisInputStream = xb.inputStream
            var len: Int
            val buf = ByteArray(128) //缓冲区128个字节
            byteArrayOutputStream = ByteArrayOutputStream()
            len = thisInputStream.read(buf)
            while (len != -1) {
                byteArrayOutputStream.write(buf, 0, len)
                len = thisInputStream.read(buf)
            }
            byteArrayOutputStream.flush() //清除缓冲区
            result = String(byteArrayOutputStream.toByteArray())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                if (thisInputStream != null) {
                    thisInputStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return result
    }

    private fun setParams(msg: String): String {
        var url = ""
        try {
            url = myURL + "?key=" + myAPIkey + "&info=" + URLEncoder.encode(msg, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return url
    }
}

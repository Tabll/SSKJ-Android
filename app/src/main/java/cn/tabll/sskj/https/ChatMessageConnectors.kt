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

class Utils {
    private val URL = "http://www.tuling123.com/openapi/api"
    private val XB_APIkey = "49f2b50b474174b487a9d88f3cea5ff2"

    fun sendMessage(msg: String): ChatMessages {
        val chatMessage = ChatMessages()

        val jsonRes = doGet(msg)
        val gson = Gson()
        val result: ChatMessagesResults?
        try {
            result = gson.fromJson(jsonRes, ChatMessagesResults::class.java)
            chatMessage.msg = result!!.text
        } catch (e: JsonSyntaxException) {
            chatMessage.msg = "小笨正忙"
        }

        chatMessage.date = Date() //设置时间
        chatMessage.type = ChatMessages.Type.INCOMING //设置类型

        return chatMessage
    }

    private fun doGet(msg: String): String {
        var result = ""
        val url = setParams(msg)//设置参数，返回一个Url
        var thisInputStream: InputStream? = null
        var baos: ByteArrayOutputStream? = null
        try {
            val urlNet = java.net.URL(url)
            val xb = urlNet.openConnection() as HttpURLConnection
            xb.readTimeout = 5000
            xb.connectTimeout = 5000
            xb.requestMethod = "GET"

            thisInputStream = xb.inputStream

            var len: Int = -1
            val buf = ByteArray(128)//缓冲区128个字节
            baos = ByteArrayOutputStream()
            len = thisInputStream.read(buf)
            while (len != -1) {
                baos.write(buf, 0, len)
                len = thisInputStream.read(buf)
            }
            baos.flush()//清除缓冲区
            result = String(baos.toByteArray())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (baos != null) {
                    baos.close()
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
            url = URL + "?key=" + XB_APIkey + "&info=" + URLEncoder.encode(msg, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return url
    }
}

package cn.tabll.sskj.https

import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class HttpConnectors {

    fun httpGet(strUrlPath: String, params: Map<String, String>, encode: String): String {
        var myStrUrlPath = strUrlPath
        var result: String? = null
        val appendUrl = getRequestData(params, encode).toString()
        myStrUrlPath = "$myStrUrlPath?$appendUrl"
        try {
            val url = URL(myStrUrlPath)
            val urlConn = url.openConnection() as HttpURLConnection
            urlConn.connectTimeout = 5000//超时时间
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")//设置头部信息，其实没什么用
            //主角开始登场，不注意就是几个小时的调试，输入流
            val `in` = InputStreamReader(urlConn.inputStream)
            val buffer = BufferedReader(`in`)
            var inputLine: String? = null
            //循环逐行读取输入流中的内容
            result = ""//每次清空数据
            while (buffer.readLine().apply { inputLine = this } != null) {
                result += inputLine!! + "\n"
            }
            `in`.close()
            urlConn.disconnect()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (ioe: IOException) {
            ioe.printStackTrace()
            return "err:" + ioe.message.toString()
        }
        return result!!
    }

    private fun getRequestData(params: Map<String, String>, encode: String): StringBuffer {
        val stringBuffer = StringBuffer()        //存储封装好的请求体信息
        try {
            for ((key, value) in params) {
                stringBuffer.append(key)
                        .append("=")
                        .append(URLEncoder.encode(value, encode))
                        .append("&")
            }
            stringBuffer.deleteCharAt(stringBuffer.length - 1)    //删除最后的一个"&"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return stringBuffer
    }

    fun httpPost(strUrlPath: String, params: Map<String, String>, encode: String): String {
        val data = getRequestData(params, encode).toString().toByteArray()
        try {
            val url = URL(strUrlPath)
            val http = url.openConnection() as HttpURLConnection
            http.connectTimeout = 5000
            http.doInput = true
            http.doOutput = true
            http.requestMethod = "POST"
            http.useCaches = false//使用post方式不能用缓存
            //设置请求体的类型是文本类型
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            //设置请求体的长度
            http.setRequestProperty("Content-Length", data.size.toString())
            //获得输出流，向服务器写入数据
            val out = http.outputStream
            out.write(data)
            val response = http.responseCode
            if (response == HttpURLConnection.HTTP_OK) {
                val inputStream = http.inputStream
                return dealResponseResult(inputStream)
            }
        } catch (ioe: IOException) {
            ioe.printStackTrace()
            return "err:" + ioe.message.toString()
        }
        return "-1"
    }

    private fun dealResponseResult(inputStream: InputStream): String {
        val resultData: String? //存储处理结果
        val byteArrayOutputStream = ByteArrayOutputStream()
        val data = ByteArray(1024)
        var len = 0
        try {
            while (inputStream.read(data).apply { len = this } != -1) {
                byteArrayOutputStream.write(data, 0, len)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        resultData = String(byteArrayOutputStream.toByteArray())
        return resultData
    }
}
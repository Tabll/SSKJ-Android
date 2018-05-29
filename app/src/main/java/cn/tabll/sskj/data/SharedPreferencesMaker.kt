package cn.tabll.sskj.data

import android.content.Context
import android.content.SharedPreferences

/**
 * 部分 Key 数据保存在 SharedPreferences 中
 * SharedPreferences NAME：SSKJ
 * -------------------------------------
 * 其中的KEY：
 * UserName(用户名)
 * UserPassword(用户密码)(不保存)
 * Token(登陆状态验证码)
 * State(当前登陆状态：1已登陆 0未登录)
 **/

class SharedPreferencesMaker {

    fun saveSharedPreferences(context: Context, dataMap: Map<String, String>){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("SSKJ", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        for ((key, value) in dataMap){
            editor.putString(key, value)
        }
        editor.apply()
    }

    fun readSharedPreferencesFromKey(context: Context, key: String): String{
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("SSKJ", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key,"NULL")
    }

    fun removeSharedPreferences(context: Context, key: String){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("SSKJ", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }
}

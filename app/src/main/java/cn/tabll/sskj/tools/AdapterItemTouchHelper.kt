package cn.tabll.sskj.tools

/**
 * 主页面设备信息适配器 Item 监听接口
 **/

interface AdapterItemTouchHelper {
    fun onItemMove(fromPosition: Int, toPosition: Int) //移动
    //fun onItemDismiss(position: Int) //滑动消失
}

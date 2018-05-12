package cn.tabll.sskj.effects

import android.content.Context
import android.graphics.*
import android.view.View
import android.util.AttributeSet
import org.jetbrains.anko.dip


class `WaterWaveView-K`(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var mCycleFactorW = 0f //默认的周期
    private var mCycleMultiple = 1f //周期倍数
    private var mTotalWidth = 0 //控件总宽度,dp,转化为dp为了减少资源消耗
    private var mTotalHeight = 0f //控件总高度,像素点
    private lateinit var mYPositions: FloatArray //用于保存初始时波纹的Y值
    private var mResetYPositions: ArrayList<FloatArray> = ArrayList() //保存移动波纹的Y值
    private var mXOffsetSpeeds: ArrayList<Int> = ArrayList() //波纹移动速度
    private var mXOffsets: ArrayList<Int> = ArrayList() //波纹移动距离
    private var mAmplitude = 30f //波纹的振幅,默认20
    private var mRippleNum = 3 //波纹数量,默认3
    private var mRippleBaseSpeed = 5f //波纹的基础速度
    private var mRippleInterval = 2f //波纹的间隔速度
    private var mRippleYPosition = 0.5f //波纹的Y间隔
    private var mCurrentRippleYPosition = 0.5f //当前水波纹的y值占比
    private var mIsStartRefresh = true //是否要刷新水波纹
    private var paintColor: IntArray
    private var callback: OnPercentageCallback? = null
    private var time = 20L
    private val runnable: Runnable = Runnable {
        postInvalidate()
    }
    companion object {
        private const val OFFSET_Y = 0
        private val GREEN_COLOR = intArrayOf(0x6685f53f, 0x8085f53f.toInt(), 0xcc85f53f.toInt())
        private val YELLOW_COLOR = intArrayOf(0x66ffee59, 0x80ffee59.toInt(), 0xccffee59.toInt())
        private val RED_COLOR = intArrayOf(0x66d94a35, 0x80d94a35.toInt(), 0xccd94a35.toInt())
    }
    private val mWavePaint: Paint //水波纹画笔
    private val mDrawFilter: DrawFilter
    init {

        //val ta: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.app_WaterRipplesView)
        mAmplitude = dip(20f).toFloat()
        mCycleMultiple = 1f
        mRippleYPosition = 0f
        mCurrentRippleYPosition = mRippleYPosition
        for (i in 0 until mRippleNum - 1) {
            mXOffsetSpeeds.add((mRippleBaseSpeed + i * mRippleInterval).toInt())
            mXOffsets.add(0)
        }
// 初始绘制波纹的画笔
        mWavePaint = Paint()
// 去除画笔锯齿
        mWavePaint.isAntiAlias = true
        mWavePaint.strokeWidth = dip(1f).toFloat()
// 设置风格为实线
        mWavePaint.style = Paint.Style.FILL
        mDrawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        paintColor = GREEN_COLOR
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
// 从canvas层面去除绘制时锯齿
        canvas.drawFilter = mDrawFilter
//水波纹的y值小于当前值时的处理
        if (mCurrentRippleYPosition > mRippleYPosition) {
            mRippleYPosition += 0.02f
//防止不被2整除的时候出现的问题
            if (mRippleYPosition > mCurrentRippleYPosition) {
                mRippleYPosition = mCurrentRippleYPosition
            }
//判断波纹的颜色
            //if (mRippleYPosition <= 0.5f) {
            //    paintColor = GREEN_COLOR
            //} else if (mRippleYPosition <= 0.75) {
            //    paintColor = YELLOW_COLOR
            //} else {
            //    paintColor = RED_COLOR
            //}已被下面代码替代
            paintColor = when  {
                mRippleYPosition <= 0.5f -> GREEN_COLOR
                mRippleYPosition <= 0.75 -> YELLOW_COLOR
                else -> RED_COLOR
            }

//回调当前水波纹y轴的占比,用于界面动态显示
            callback!!.onPercentage(mRippleYPosition)
        }
        val startY = mTotalHeight - mRippleYPosition * mTotalHeight + mAmplitude
// 绘制水波纹
        for (j in 0 until mRippleNum - 1) {
// 使用System.arrayCopy方式重新填充第一条波纹的数据
            System.arraycopy(mYPositions, mXOffsets[j], mResetYPositions[j], 0, mYPositions.size - mXOffsets[j])
            System.arraycopy(mYPositions, 0, mResetYPositions[j], mYPositions.size - mXOffsets[j], mXOffsets[j])
            mWavePaint.color = paintColor[j]
            for (i in 0 until mTotalWidth - 1) {
                val x = dip(i.toFloat())
                canvas.drawLine(x.toFloat(), startY - mResetYPositions[j][i], x.toFloat(), mTotalHeight, mWavePaint)
            }
        }
// 改变波纹的移动点
        for (i in 0 until mRippleNum - 1) {
            mXOffsets[i] = mXOffsetSpeeds[i] + mXOffsets[i]
// 如果已经移动到结尾处,则重头记录
            if (mXOffsets[i] >= mTotalWidth)
                mXOffsets[i] = 0
        }
// 延时后刷新水波纹,如果想水波纹更加的流畅,可以不延时,但是会消耗更多的资源
        if (mIsStartRefresh) {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, time)
        }
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
// 记录下view的宽高
        mTotalWidth = dip(w.toFloat())
        mTotalHeight = h.toFloat()
// 用于保存原始波纹的y值
        mYPositions = FloatArray(mTotalWidth)
        for (i in 0 until mRippleNum - 1) {
            mResetYPositions.add(kotlin.FloatArray(mTotalWidth))
        }
// 将周期定为view总宽度
        mCycleFactorW = (2 * Math.PI / mTotalWidth).toFloat()
        setYPositions()
//保持时间一直,从而保持不同手机的水波纹移动速度基本一样
        time = (100 * (185f / mTotalWidth)).toLong()
    }
    private fun setYPositions() {
// 根据view总宽度得出所有对应的y值
        for (i in 0 until mTotalWidth - 1) {
            mYPositions[i] = (mAmplitude * Math.sin((mCycleMultiple * mCycleFactorW * i).toDouble()) + OFFSET_Y).toFloat()
        }
    }
    /**
     * 设置波纹占整个控件的多少
     * @param position 值为0到1,该值小于0和大于1时无效
     */
    fun setRipplePosition(position: Float) {
        if (position in 0.0..1.0) {
            mRippleYPosition = 0f
            mCurrentRippleYPosition = position
        }
    }
    /**
     * 设置百分比的监听
     */
    fun setOnPercentageCallback(callback: OnPercentageCallback) {
        this.callback = callback
    }
    /**
     * 开始刷新水波纹
     */
    fun startRefreshRipple() {
        mIsStartRefresh = true
        postInvalidate()
    }
    /**
     * 停止刷新水波纹
     */
    fun stopRefreshRipple() {
        mIsStartRefresh = false
    }
    interface OnPercentageCallback {
        fun onPercentage(percentage: Float)
    }
}
package cn.tabll.sskj.effects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import cn.tabll.sskj.R;

/**
 * 此部分代码受https://github.com/Geekince/WaterWaveView启发
 * 有多处修改
 **/

public class WaterWaveView extends View {

    private Context mContext;
    private int mScreenWidth = getWidth(); //控件的宽度（虚拟机里是1080）
    private int mScreenHeight = getHeight(); //控件的高度（虚拟机里是747）

    private float mScreenWidth2;
    private float mScreenHeight2;
    private float mScreenWidth4;
    //private float mScreenHeight4;
    private float f1;

    private float offsetWidth; //与圆半径的偏移量
    private float startAngle, sweepAngle; //扇形的起始角度和扫过角度

    private Paint mRingPaint;
    private Paint mCirclePaint;
    private Paint mWavePaint;
    private Paint flowPaint;
    private Paint leftPaint;

    private int mRingSTROKEWidth = 15;
    int mCircleSTROKEWidth = 2;
    int mLineSTROKEWidth = 1;

    int mCircleColor = Color.WHITE;
    int mRingColor = Color.WHITE;
    int mWaveColor = Color.WHITE;

    private Handler mHandler;
    private long c = 0L;
    private boolean mStarted = false;
    final float f = 0.033F;
    int mAlpha = 50;// 透明度
    float mAmplitude = 10.0F; // 振幅
    private float mWaterLevel = 0.5F;// 默认水高0.5(范围：0~1)
    private Path mPath;

    RectF mRectF;

    String flowNum = "89%";
    String flowLeft = "水质良好";

    public WaterWaveView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public WaterWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public WaterWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void setWaterLevel(float mWaterLevel) {
        this.mWaterLevel = mWaterLevel;
        this.flowNum = String.valueOf(mWaterLevel * 100) + "%";
    }

    private void init() {
        mRectF = new RectF();
        mRingPaint = new Paint();
        mRingPaint.setColor(mRingColor);
        mRingPaint.setAlpha(50);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setAntiAlias(true);
        mRingPaint.setStrokeWidth(mRingSTROKEWidth);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeWidth(mCircleSTROKEWidth);

        Paint linePaint = new Paint();
        linePaint.setColor(mCircleColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(mLineSTROKEWidth);

        flowPaint = new Paint(); //文字画笔
        flowPaint.setColor(mCircleColor);
        flowPaint.setStyle(Paint.Style.FILL);
        flowPaint.setAntiAlias(true);

        leftPaint = new Paint(); //文字画笔2
        leftPaint.setColor(mCircleColor);
        leftPaint.setStyle(Paint.Style.FILL);
        leftPaint.setAntiAlias(true);

        mWavePaint = new Paint();
        mWavePaint.setStrokeWidth(1.0F);
        mWavePaint.setColor(mWaveColor);
        mWavePaint.setAlpha(mAlpha);
        mPath = new Path();

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 0) {
                    invalidate();
                    if (mStarted) { //不断发消息给自己，使自己不断被重绘
                        //暂时停止绘制
                        mHandler.sendEmptyMessageDelayed(0, 60L);
                    }
                }
            }
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measure(widthMeasureSpec, true);
        int height = measure(heightMeasureSpec, false);
        if (width < height) {
            //setMeasuredDimension(width, width);
            setMeasuredDimension(width, heightMeasureSpec);
        } else {
            //setMeasuredDimension(height, height);
            setMeasuredDimension(widthMeasureSpec, height);
        }
    }

    /**
     * 测量
     * @param measureSpec measureSpec
     * @param isWidth isWidth
     * @return result
     */
    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight()
                : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth()
                    : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mScreenWidth = w;
        mScreenHeight = h;
        mScreenWidth2 = mScreenWidth / 2;
        mScreenHeight2 = mScreenHeight / 2;
        mScreenWidth4 = mScreenWidth / 4;
        //mScreenHeight4 = mScreenHeight / 4;
        mRectF.set(mScreenWidth4, mScreenHeight2 - mScreenWidth4, mScreenWidth * 0.75f, mScreenHeight2 + mScreenWidth4 ); //绘制水面静止时的高度
        float centerOffset = Math.abs(mScreenWidth2 * mWaterLevel - mScreenWidth4);
        float hoAngle = (float) (Math.asin(centerOffset / (mScreenWidth4)) * 180 / Math.PI);
        f1 = mScreenHeight - ((mScreenHeight - mScreenWidth2) / 2 + mScreenWidth2 * mWaterLevel) - mAmplitude;
        float waveWidth = (float) Math.sqrt(mScreenWidth * mScreenWidth / 16 - centerOffset * centerOffset);
        offsetWidth = mScreenWidth4 - waveWidth; //与圆半径的偏移量
        if (mWaterLevel > 0.5F) {
            startAngle = 360F - hoAngle;
            sweepAngle = 180F + 2 * hoAngle;
        } else {
            startAngle = hoAngle;
            sweepAngle = 180F - 2 * hoAngle;
        }
        float ratioWidth = (float)mScreenWidth / 1080;
        int TEXT_SIZE = Math.round(50 * ratioWidth);
        leftPaint.setTextSize(TEXT_SIZE); //字体大小，原18
        flowPaint.setTextSize(TEXT_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(mContext.getResources().getColor( R.color.colorPrimary)); //控件背景颜色
        //canvas.drawLine(mScreenWidth * 3 / 8, mScreenHeight * 5 / 8, mScreenWidth * 5 / 8, mScreenHeight * 5 / 8, linePaint); //画一条线


        if ((!mStarted) || (mScreenWidth == 0) || (mScreenHeight == 0)) { // 如果未开始则直接返回
            return;
        }
        if (this.c >= 8388607L) {
            this.c = 0L;
        }
        c = 1L + c; //在onDraw时c自增1
        int top = (int) (f1 + mAmplitude);
        mPath.reset();
        int startX;
        int endX;
        if (mWaterLevel > 0.50F) {
            startX = (int) (mScreenWidth4 + offsetWidth);
            endX = (int) (mScreenWidth2 + mScreenWidth4 - offsetWidth);
        } else {
            startX = (int) (mScreenWidth4 + offsetWidth - mAmplitude);
            endX = (int) (mScreenWidth2 + mScreenWidth4 - offsetWidth + mAmplitude);
        }
        while (startX < endX) { // 波浪效果
            int startY = (int)(f1 - mAmplitude * Math.sin(Math.PI * (2.0F * (startX + this.c * mScreenWidth * this.f)) / mScreenWidth));
            canvas.drawLine(startX, startY, startX, top, mWavePaint); //动态波浪
            startX++;
        }

        drawStatic(canvas);

        //canvas.restore();
    }

    private void drawStatic(Canvas canvas){
        float num = flowPaint.measureText(flowNum);
        canvas.drawText(flowNum, mScreenWidth2 - num / 2, mScreenHeight * 4 / 7, flowPaint);
        float left = leftPaint.measureText(flowLeft);
        canvas.drawText(flowLeft, mScreenWidth2 - left / 2, mScreenHeight * 3 / 7, leftPaint);
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mWavePaint); //绘制水波下的图形
        canvas.drawCircle(mScreenWidth2, mScreenHeight2, mScreenWidth4 + mRingSTROKEWidth / 2, mRingPaint); //绘制淡粗圆
        canvas.drawCircle(mScreenWidth2, mScreenHeight2, mScreenWidth4, mCirclePaint); //绘制亮细圆
    }

    @Override
    public Parcelable onSaveInstanceState() {
        // Force our ancestor class to save its state
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.progress = (int) c;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        c = ss.progress;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 关闭硬件加速，防止异常unsupported operation exception
        //this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 波纹开始
     */
    public void startWave() {
        if (!mStarted) {
            this.c = 0L;
            mStarted = true;
            this.mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 波纹停止
     */
    public void stopWave() {
        if (mStarted) {
            this.c = 0L;
            mStarted = false;
            this.mHandler.removeMessages(0);
        }
    }

    /**
     * 保存状态
     */
    static class SavedState extends BaseSavedState {
        int progress;
        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            progress = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(progress);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}

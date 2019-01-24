package com.victor.playandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;

import com.victor.baselib.utils.ToastUtil;
import com.victor.baselib.utils.XLog;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CircleMenuView extends View implements View.OnClickListener, View.OnTouchListener{

    private Paint bgPaint;
    /**
     * menu size
     */
    private int menuSize;
    /**
     * start draw angle
     */
    private int startAngle = 0;
    /**
     * borderline width
     */
    private int borderLineWidth = 3;
    /**
     * menu name
     */
    private List<String> titles = Arrays.asList("设置", "项目目", "体系", "首页页", "登录");
    private List<Integer> menuColors = Arrays.asList(0xFFFF0000, 0xFF80FF00, 0xFF00FFFF, 0xFF0000FF, 0xFF808770);

    private Matrix rotateMatrix = new Matrix();
    private volatile int rotate = 0;
    private AtomicBoolean atomicRunning = new AtomicBoolean(false);


    public CircleMenuView(Context context) {
        this(context, null);
    }

    public CircleMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void init() {
        menuSize = titles.size();
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(Color.BLUE);
        bgPaint.setStrokeWidth(borderLineWidth);
        setClickable(true);
        setOnClickListener(this);
        setOnTouchListener(this);
        XLog.e(XLog.TAG_GU, " 初始matrix:" + rotateMatrix.toShortString());
    }

    public void startRotate() {
        if (atomicRunning.get()) {
            return;
        }
        atomicRunning.getAndSet(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (atomicRunning.get()) {
                    rotate += 20;
                    if (rotate > 360) {
                        rotate = 0;
                    }
                    if (getWidth() > 10) {
                        rotateMatrix.setRotate(rotate, getWidth() / 2, getWidth() / 2);
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stopRotate() {
        if (atomicRunning.get()) {
            atomicRunning.getAndSet(false);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        canvas.setMatrix(rotateMatrix);

//        bgPaint.setColor(Color.BLUE);
//        bgPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(width / 2, width / 2, (width - borderLineWidth) / 2, bgPaint);
        // 绘制各个块的颜色
        processArcColor(canvas, new RectF(borderLineWidth, borderLineWidth, width - borderLineWidth, width - borderLineWidth), startAngle);
        // 绘制中心圆
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.WHITE);
        canvas.drawCircle(width / 2, width / 2, (width - borderLineWidth) / 4, bgPaint);
        // 绘制文本
        drawText(canvas, titles, startAngle, width - borderLineWidth);
    }

    /**
     * 绘制各个块的颜色
     * @param canvas
     * @param sourceRect
     * @param startAngle
     */
    private void processArcColor(Canvas canvas, RectF sourceRect, int startAngle) {
        int endArc = 360 / menuSize;
        bgPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < menuSize; i++) {
            bgPaint.setColor(menuColors.get(i));
            canvas.drawArc(sourceRect, startAngle, endArc, true, bgPaint);
            startAngle += 360 / menuSize;
        }
    }

    /**
     *
     * @param canvas
     * @param titles
     * @param startAngle 起始角度
     * @param diameter 直径
     */
    private void drawText(Canvas canvas, List<String> titles, int startAngle, int diameter) {
        bgPaint.setColor(Color.WHITE);
        bgPaint.setTextSize(48);
        bgPaint.setTextAlign(Paint.Align.CENTER);
        int step = 360 / menuSize;
        Paint.FontMetrics fontMetrics = bgPaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        float arcRotate = 360 / menuSize;
        for (int i = 0; i < titles.size(); i++) {
            RectF rectF = new RectF(diameter / 8f + textHeight / 2, diameter / 8f + textHeight / 2, (diameter) * 7f / 8f  - textHeight / 2, (diameter) * 7f / 8f  - textHeight / 2);
            String title = titles.get(i);
            Path textPath = new Path();
            textPath.addArc(rectF, startAngle, arcRotate);
            canvas.drawTextOnPath(title, textPath, 0, 0, bgPaint);
//            canvas.drawPath(textPath, bgPaint);
            startAngle += step;
        }
    }

    @Override
    public void onClick(View v) {
        if (atomicRunning.get()) {
            stopRotate();
        } else {
            startRotate();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        XLog.e(XLog.TAG_GU, "click : x = " + event.getX() + ", y = " + event.getY());
        return detechMenuClick(event);
    }

    private boolean detechMenuClick(MotionEvent event) {
        boolean consume = false;
        // width / 2, width / 2, (width - borderLineWidth) / 4
        float clickX = event.getX();
        float clickY = event.getY();
        int width = getWidth();

        if (clickX > width / 4 && clickX < width * 3 / 4 && clickY > width / 4 && clickY < width * 3 / 4) {
//            XLog.e(XLog.TAG_GU, "没有被消费");
        } else {
            consume = true;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                XLog.e(XLog.TAG_GU, "click : x = " + clickX + ", y = " + clickY);
                stopRotate();
                // 计算四个象限内点击位置与0度起点线的夹角
                int radius = getWidth() / 2;
                double rotateSrc = 0;
                if (clickX > radius) {
                    if (clickY > radius) { // 0-90
                        // 计算弧度
                        rotateSrc = Math.atan2(clickY - radius, clickX - radius);
                        // 弧度转角度
                        rotateSrc = rotateSrc * 180 / Math.PI;
                    } else { // 270-360
                        // 计算弧度
                        rotateSrc = Math.atan2(radius - clickY, clickX - radius);
                        // 弧度转角度
                        rotateSrc = 360 - rotateSrc * 180 / Math.PI;
                    }
                } else {
                    if (clickY > radius) { // 90-180
                        // 计算弧度
                        rotateSrc = Math.atan2(clickY - radius, radius - clickX);
                        // 弧度转角度
                        rotateSrc = 180 - rotateSrc * 180 / Math.PI;
                    } else { // 180-270
                        // 计算弧度
                        rotateSrc = Math.atan2(radius - clickY, radius - clickX);
                        // 弧度转角度
                        rotateSrc = 180 + rotateSrc * 180 / Math.PI;
                    }
                }
                onClickListener(rotateSrc);
            }
        }

        return consume;
    }

    private void onClickListener(double rotateReal) {
        XLog.e(XLog.TAG_GU, "点击的角度是 : " + rotateReal);
        if (rotate > rotateReal) {
            rotateReal += 360 - rotate;
        } else {
            rotateReal = rotateReal - rotate;
        }
        double baseRotate = 360 / titles.size();
        int index = (int) (rotateReal / baseRotate);
        ToastUtil.getInstance().showToast(getContext(), "点击了： " + titles.get(index));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        atomicRunning.getAndSet(false);
    }

}

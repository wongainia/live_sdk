package com.lib.showfield.utils;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;

/**
 * Created by upingu
 * Date  2020/6/28 14:37
 * <p>
 * Description 序列帧插值器
 **/
public class XFrameAnimation extends Drawable implements Animatable {

    private static final long DEFAULT_DURATION = 100;
    private long duration = DEFAULT_DURATION;
    private final Paint mPaint;
    private final int[] RES_IDS;
    private int resIndex;

    private final Resources mResources;
    private ValueAnimator mAnimator;
    private ValueAnimator.AnimatorUpdateListener mAnimUpdateListener;
    //取第一帧，用于获取图片宽高
    private Drawable mFirstDrawable;

    public XFrameAnimation(int[] RES_IDS, Resources resources) {
        this(DEFAULT_DURATION, RES_IDS, resources);
    }

    public XFrameAnimation(long duration, int[] RES_IDS, Resources resources) {
        this.duration = duration;
        this.RES_IDS = RES_IDS;
        this.mResources = resources;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);

        if (this.RES_IDS == null || this.RES_IDS.length <= 0) {
            throw new RuntimeException(" XFrameAnimation RES_IDS can not null or empty !!!");
        }
        mFirstDrawable = resources.getDrawable(this.RES_IDS[0]);
        createAnimator();
    }

    /**
     * 初始化动画
     */
    private void createAnimator() {
        mAnimator = ValueAnimator.ofInt(RES_IDS.length - 1);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatCount(0);
        mAnimator.setDuration(duration);

        mAnimUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate(((int) animation.getAnimatedValue()));
            }
        };
    }

    /**
     * 重绘
     *
     * @param index 帧索引
     */
    public void invalidate(int index) {
        this.resIndex = index;
        invalidateSelf();
    }

    /**
     * 获取动画帧数
     *
     * @return 帧数量
     */
    public int getFrameCount() {
        return RES_IDS.length;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mResources != null) {
            BitmapDrawable drawable = (BitmapDrawable) mResources.getDrawable(RES_IDS[resIndex % RES_IDS.length]);
            Bitmap bitmap = drawable.getBitmap();
            canvas.drawBitmap(bitmap, 0, 0, mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @Override
    public void start() {
        // If the animators has not ended, do nothing.
        startAnimator();
        invalidateSelf();
    }

    /**
     * 开始执行动画
     */
    private void startAnimator() {
        if (mAnimator != null) {
            mAnimator.addUpdateListener(mAnimUpdateListener);
            mAnimator.start();
        }
    }

    @Override
    public void stop() {
        if (mAnimator != null && mAnimator.isStarted()) {
            mAnimator.removeAllUpdateListeners();
            mAnimator.end();
        }
    }

    @Override
    public boolean isRunning() {
        return mAnimator.isRunning();
    }

    @Override
    public int getIntrinsicWidth() {
        return mFirstDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mFirstDrawable.getIntrinsicHeight();
    }

    public ValueAnimator getAnimator() {
        return mAnimator;
    }
}

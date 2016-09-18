package com.song.billboardlibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by song on 2016/9/17.
 */
public abstract class SmoothViewGroup extends ViewGroup {


    protected static final int STATUS_SMOOTHING = 0;

    protected static final int STATUS_STOP = -1;

    /**
     * ViewGroup宽高
     */
    protected int mWidth,mHeight;

    /**
     * 变化的marginTop值
     */
    protected int mSmoothMarginTop;

    /**
     * 现在的状态
     */
    protected int mStatus =STATUS_STOP;


    /**
     * 滚动的时间间隔
     */
    protected int mDuration = 500;

    /**
     * 重复时间
     */
    protected int mRepeatTimes = 0;


    public SmoothViewGroup(Context context) {
        super(context);
    }

    public SmoothViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmoothViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mSmoothMarginTop = -h;
        initview();
    }

    protected abstract void initview();


    public void startSmooth()
    {
        if (mStatus != STATUS_STOP)
        {
            return;
        }

        ValueAnimator animator = ValueAnimator.ofFloat(-mHeight,0);
        animator.setDuration(mDuration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float marginTop = (float) valueAnimator.getAnimatedValue();
                mSmoothMarginTop = (int) marginTop;
                if (marginTop == 0) {

                    postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            mRepeatTimes++;

                            mSmoothMarginTop = -mHeight;

                            doAnimFinish();

                            mStatus = STATUS_STOP;

                        }
                    }, 50);

                } else {
                    doAnim();
                }
            }
        });
        animator.start();
        mStatus = STATUS_SMOOTHING;
    }


    /**
     * 动画结束
     */
    protected abstract void doAnimFinish();

    /**
     * 开始动画
     */
    protected abstract void doAnim();

    /**
     * 是否是技术圈
     * @return
     */
    protected boolean isOddCircle()
    {
        return mRepeatTimes % 2 == 1;
    }


}

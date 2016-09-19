package com.song.billboardlibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2016/9/17.
 */
public class BillTextView extends SmoothViewGroup implements View.OnClickListener{

    private List<String> mTextList = new ArrayList<>();
    private TextView[] mTexts = new TextView[2];
    private BillViewClickListener mClickListener;
    private int status = STATUS_STOP;//开启无限循环

    int billTextViewGravity = Gravity.CENTER;
    int billTextViewSize = 18;
    int billTextViewColor =  Color.BLACK;
    Drawable billTextViewBg = null;


    public BillTextView(Context context) {
        super(context);
    }

    public BillTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BillTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.BillTextView,0,0);
        billTextViewBg = a.getDrawable(R.styleable.BillTextView_billTextBackground);
        billTextViewColor = a.getColor(R.styleable.BillTextView_billTextColor,Color.BLACK);
        billTextViewGravity = a.getInt(R.styleable.BillTextView_billTextGravity,Gravity.CENTER);
        billTextViewSize = a.getInt(R.styleable.BillTextView_billTextSize,18);
        a.recycle();

    }


    @Override
    protected void initview() {


        if (mTextList.size() == 0)
            return;

        removeAllViews();

        MarginLayoutParams params = new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < mTexts.length; i++) {
            mTexts[i] = new TextView(getContext());
            mTexts[i].setText(getBillText(i));
            mTexts[i].setTag(i);
            mTexts[i].setLines(2);
            mTexts[i].setEllipsize(TextUtils.TruncateAt.END);
            mTexts[i].setTextSize(14);
            mTexts[i].setGravity(billTextViewGravity);
            mTexts[i].setTextColor(billTextViewColor);
            mTexts[i].setBackground(billTextViewBg);
            mTexts[i].setTextSize(TypedValue.COMPLEX_UNIT_SP,billTextViewSize);
            mTexts[i].setOnClickListener(this);



            addViewInLayout(mTexts[i],-1,params,true);
        }

    }


    @Override
    protected void doAnimFinish() {
        if(isOddCircle())
        {
            mTexts[0].setText(getBillText(mRepeatTimes + 1));
            mTexts[0].setTag(mRepeatTimes + 1);
        }
        else
        {
            mTexts[1].setText(getBillText(mRepeatTimes+1));
            mTexts[1].setTag(mRepeatTimes + 1);
        }

        for (int i = 0; i < mTexts.length; i++) {
            mTexts[i].setAlpha(1);
        }
    }

    @Override
    protected void doAnim() {
        if (isOddCircle()) {
            mTexts[1].setAlpha(-mSmoothMarginTop / (float) mHeight);
        } else {
            mTexts[0].setAlpha(-mSmoothMarginTop / (float) mHeight);
        }
        requestLayout();

    }

    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        int cCount = getChildCount();
        MarginLayoutParams cParams;

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0, ct = 0, cr, cb;


            if (isOddCircle()) {
                if (i == 1) {
                    cl = cParams.leftMargin;
                    ct =  mSmoothMarginTop + mHeight;
                } else if (i == 0) {
                    cl = cParams.leftMargin;
                    ct = mSmoothMarginTop;
                }
            } else {
                if (i == 0) {
                    cl = cParams.leftMargin;
                    ct = mSmoothMarginTop + mHeight;
                } else if (i == 1) {
                    cl = cParams.leftMargin;
                    ct =  mSmoothMarginTop;
                }
            }


            cr = cl + mWidth;
            cb = ct + mHeight;
            childView.layout(cl, ct, cr, cb);
        }

    }


    public void setBillText(List<String> billTextList)
    {
        mTextList = billTextList;
        initview();
    }

    /**
     * 获得广告显示的内容
     *
     * @param position
     * @return
     */
    private String getBillText(int position)
    {
        position = position % mTextList.size();
        return mTextList.get(position);
    }

    public void setOnBillViewCllickListener(BillViewClickListener mClickListener)
    {
        this.mClickListener = mClickListener;
    }

    @Override
    public void onClick(View view) {
        int tag = (int) view.getTag();

        Log.e("BillTextView",tag+"");

        tag = tag % mTextList.size();
        if(mClickListener != null)
            mClickListener.onClick(tag);

    }

    public void start()
    {

        if(status == STATUS_SMOOTHING)
            return;

        ValueAnimator animator = ValueAnimator.ofInt(5000,0);
        animator.setDuration(5*1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();




                if(value/10 == 0)
                {
                    startSmooth();
                }
            }
        });

        animator.start();

        status = STATUS_SMOOTHING;

    }

}

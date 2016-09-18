package com.song.billboardlibrary;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2016/9/17.
 */
public class BillTextView extends SmoothViewGroup {

    private List<String> mTextList = new ArrayList<>();
    private TextView[] mTexts = new TextView[2];

    private int mTextLineHeight = 0;


    public BillTextView(Context context) {
        super(context);
    }

    public BillTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BillTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
            mTexts[i].setLines(2);
            mTexts[i].setEllipsize(TextUtils.TruncateAt.END);
            mTexts[i].setTextSize(14);



            if(mTextLineHeight < mTexts[i].getLineHeight())
            {
                mTextLineHeight = mTexts[i].getLineHeight();
            }
            addViewInLayout(mTexts[i],-1,params,true);
        }

    }


    @Override
    protected void doAnimFinish() {
        if(isOddCircle())
        {
            mTexts[0].setText(getBillText(mRepeatTimes + 1));
        }
        else
        {
            mTexts[1].setText(getBillText(mRepeatTimes+1));
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
}

package com.example.menu.data;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.menu.data.StatisticData;

import java.util.List;

public class PieView extends View {
    private int[] mColors = {Color.parseColor("#C04851"), Color.parseColor("#F1C4CD"), Color.parseColor("#b6a476"),
            Color.parseColor("#2775B6"), Color.parseColor("#1BA784"), Color.parseColor("#E2C027"), Color.parseColor("#FA7E23"),
            Color.parseColor("#5a1f1b"), Color.parseColor("#f6ad8f")};
    private float mStartAngle = 0;
    private List<StatisticData> data;
    private int mWidth, mHeight;
    private Paint painter = new Paint();

    public PieView(Context context) {
        this(context, null);
        painter.setTextSize(40);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        painter.setStyle(Paint.Style.FILL);
        painter.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == data)
            return;
        float currentStartAngle = mStartAngle;
        canvas.translate(mWidth / 2, mHeight / 2);
        int categoryHeight = 200;
        int singleCategoryLength = 70;
        float r = (float) (Math.min(mWidth, mHeight - 200) / 2 * 0.8);
        RectF rect = new RectF(-r, -r, r, r);
        int noSingleLine = 5;
        int singleCategoryGridWidth = (mWidth - 20) / 5;
        int singleCategoryGridHeight = 100;
        int gap = singleCategoryGridHeight - singleCategoryLength;

        for (int i = 0; i < data.size(); i++) {
            StatisticData pie = data.get(i);
            painter.setColor(pie.getColor());
            RectF category = new RectF(singleCategoryGridWidth * (i % noSingleLine) - mWidth / 2,
                    singleCategoryGridHeight * (i / noSingleLine) - r - categoryHeight,
                    singleCategoryGridWidth * (i % noSingleLine) + singleCategoryLength - mWidth / 2,
                    singleCategoryGridHeight * (i / noSingleLine) + singleCategoryLength - r - categoryHeight);
            canvas.drawRect(category, painter);
            painter.setTextSize(35);
            canvas.drawText(pie.getCategory(),
                    singleCategoryGridWidth * (i % noSingleLine) - mWidth / 2 + singleCategoryLength + 5,
                    singleCategoryGridHeight * (i / noSingleLine) - r - categoryHeight + singleCategoryLength / 2, painter);
            canvas.drawText(pie.getAmount() + "",
                    singleCategoryGridWidth * (i % noSingleLine) - mWidth / 2 + singleCategoryLength + 5,
                    singleCategoryGridHeight * (i / noSingleLine) - r - categoryHeight + singleCategoryLength, painter);
            canvas.drawArc(rect, currentStartAngle, pie.getAngle(), true, painter);
            painter.setColor(Color.BLACK);
            painter.setStrokeWidth(8);
//            double x = Math.cos(Math.toRadians(currentStartAngle + pie.getAngle() / 2)) * r;
//            double y = Math.sin(Math.toRadians(currentStartAngle + pie.getAngle() / 2)) * r;
//            painter.setTextSize(40);
//            canvas.drawText(pie.getAmount() + "", (float) x, (float) y, painter);
            currentStartAngle += pie.getAngle();
        }

    }

    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();
    }

    public void setData(List<StatisticData> data) {
        this.data = data;
        setStartAngle(0);
        initData(data);
        invalidate();
    }

    private void initData(List<StatisticData> data) {
        if (null == data || data.size() == 0)
            return;
        if (data.size() > 10) {
            double otherAmount = 0;
            float percent = 0;
            while (data.size() > 9) {
                StatisticData single = data.remove(data.size() - 1);
                otherAmount += single.getAmount();
                percent += single.getPercent();
            }
            StatisticData other = new StatisticData("Others", otherAmount);
            other.setPercent(percent);
            data.add(other);
        }
        for (int i = 0; i < data.size(); i++) {
            StatisticData pie = data.get(i);
            int j = i % mColors.length;
            pie.setColor(mColors[j]);
        }
    }
}

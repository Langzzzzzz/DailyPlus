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
    private int[] mColors = {0xE7AD3F00, 0xECD54B00, 0xC1D26C00, 0x62B36700, 0x539DA000, 0x3A8DE900, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
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
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rect = new RectF(-r, -r, r, r);

        for (int i = 0; i < data.size(); i++) {
            StatisticData pie = data.get(i);
            painter.setColor(pie.getColor());
            canvas.drawArc(rect, currentStartAngle, pie.getAngle(), true, painter);
            painter.setColor(Color.BLACK);
            painter.setStrokeWidth(8);
            double x = Math.cos(Math.toRadians(currentStartAngle + pie.getAngle() / 2)) * r;
            double y = Math.sin(Math.toRadians(currentStartAngle + pie.getAngle() / 2)) * r;
            canvas.drawText(pie.getCategory(), (float) x, (float) y, painter);
            canvas.drawText(pie.getAmount() + "", (float) x, (float) y + 50, painter);
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

        for (int i = 0; i < data.size(); i++) {
            StatisticData pie = data.get(i);
            int j = i % mColors.length;
            pie.setColor(mColors[j]);
        }
    }
}

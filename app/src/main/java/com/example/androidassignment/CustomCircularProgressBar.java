package com.example.androidassignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomCircularProgressBar extends View {

    private Paint backgroundPaint;
    private Paint progressPaint;
    private RectF oval;
    private int progress = 53; // Default progress, change dynamically

    public CustomCircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Paint for the background (grey color)
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.parseColor("#F4F6F8")); // Grey color;
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(20);
        backgroundPaint.setAntiAlias(true);

        // Paint for the progress (gold color)
        progressPaint = new Paint();
        progressPaint.setColor(Color.parseColor("#FFD700")); // Gold color
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(20);
        progressPaint.setAntiAlias(true);

        oval = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get the center point and radius for the circle
        int width = getWidth();
        int height = getHeight();
        int minDimension = Math.min(width, height);
        int radius = (minDimension / 2) - 20; // Adjust for stroke width

        // Set the oval dimensions for the circle
        oval.set(
                (width / 2) - radius,
                (height / 2) - radius,
                (width / 2) + radius,
                (height / 2) + radius
        );

        // Draw the background circle (grey)
        canvas.drawArc(oval, 0, 360, false, backgroundPaint);

        // Draw the progress arc (gold) for current progress
        float sweepAngle = (progress / 100f) * 360; // Calculate sweep angle for 53%
        canvas.drawArc(oval, -90, sweepAngle, false, progressPaint);
    }

    // Method to set progress dynamically
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate(); // Redraw the view with updated progress
    }
}

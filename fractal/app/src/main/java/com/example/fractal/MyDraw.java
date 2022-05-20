package com.example.fractal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyDraw extends View {

    Paint paint = new Paint();
    int w, h;

    public MyDraw (Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas){
        drawCircles(canvas, w/2, h/2, 1000);
    }

    public void drawCircles(Canvas canvas, int x, int y, int r){
        canvas.drawCircle(x, y, r, paint);

        if(r > 50) {
            drawCircles(canvas, x, y - r, r / 2);
            drawCircles(canvas, x + r, y, r / 2);
            drawCircles(canvas, x, y + r, r / 2);
            drawCircles(canvas, x - r, y, r / 2);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.w = w;
        this.h = h;
    }


}

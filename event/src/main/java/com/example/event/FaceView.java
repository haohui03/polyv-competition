package com.example.event;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class FaceView extends View {
    public float X, Y;
    public FaceView(Context context) {
        super(context);
        X = 20;
        Y = 20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.face);
        canvas.drawBitmap(bitmap, X, Y, paint);
        if(bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}

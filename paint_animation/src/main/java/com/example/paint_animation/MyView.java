package com.example.paint_animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(0x99008953); // 设置画笔颜色
        paint.setStyle(Paint.Style.FILL_AND_STROKE); // 填充方式
        canvas.drawRect(10, 10, 280, 150, paint);  // 矩形

        paint.setAntiAlias(true); // 抗锯齿
        paint.setColor(0xffa4c739);
        RectF rectF = new RectF(10,10,100,100); // 外轮廓矩形
        rectF.offset(400,20);
        canvas.drawArc(rectF, -10, -160, false, paint);  // 圆弧

        paint.setColor(0xff343378);
        canvas.drawCircle(100, 250, 50, paint); // 圆

        RectF rectF1 = new RectF(300, 150, 600, 300);
        canvas.drawRoundRect(rectF1, 20, 20, paint); // 圆角矩形

        canvas.drawLine(40,400, 300,500,paint); // 线

        paint.setColor(0xff123123);
        paint.setTextAlign(Paint.Align.LEFT); // 左对齐
        paint.setTextSize(64); // sp
        canvas.drawText("人生苦短",500, 500,paint);  // 文字


//        Bitmap bitmap = drawableToBitamp(getResources().getDrawable(R.drawable.discovery));
//        canvas.drawBitmap(bitmap, 100, 600, paint);
//        Bitmap cut_bitmap = Bitmap.createBitmap(bitmap, 20, 20, 100, 100); // 裁剪
//        canvas.drawBitmap(cut_bitmap, 100, 600, paint);

        // 绘制路径
        paint.setStyle(Paint.Style.STROKE); // 描边
        Path path = new Path();
        path.addCircle(200, 600, 100, Path.Direction.CW); // 顺时针
        canvas.drawPath(path, paint); // 绘制圆形路径
        canvas.drawTextOnPath("我欲乘风归去", path, 0, 0, paint);


    }
    private Bitmap drawableToBitamp(Drawable drawable)
    {
        //声明将要创建的bitmap
        Bitmap bitmap = null;
        //获取图片宽度
        int width = drawable.getIntrinsicWidth();
        //获取图片高度
        int height = drawable.getIntrinsicHeight();
        //图片位深，PixelFormat.OPAQUE代表没有透明度，RGB_565就是没有透明度的位深，否则就用ARGB_8888。详细见下面图片编码知识。
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        //创建一个空的Bitmap
        bitmap = Bitmap.createBitmap(width,height,config);
        //在bitmap上创建一个画布
        Canvas canvas = new Canvas(bitmap);
        //设置画布的范围
        drawable.setBounds(0, 0, width, height);
        //将drawable绘制在canvas上
        drawable.draw(canvas);
        return bitmap;
    }

}

package com.example.mygame2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

// Game управляет всеми объектами в игре //


class Game extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;
    private GameLoop gameLoop;

    public Game(Context context) {
        super(context);

       //get surface holder

    SurfaceHolder surfaceHolder = getHolder();
    surfaceHolder.addCallback(this);

    this.context = context;
    gameLoop =  new GameLoop(this, surfaceHolder);
    setFocusable(true);

}
    @Override
   public void surfaceCreated(SurfaceHolder holder) {
       gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(
             SurfaceHolder holder, int format, int width, int height){}


    @Override
    public void surfaceDestroyed(
            SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
    }
    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS:" + averageUPS, 100, 100, paint);

    }
    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS:" + averageFPS, 100, 200, paint);

    }

    public void update() {
        //Состояние обновления игры
    }
}

package com.example.mygame2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

// Game управляет всеми объектами в игре //


class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private GameLoop gameLoop;

    public Game(Context context) {
        super(context);

       //get surface holder

    SurfaceHolder surfaceHolder = getHolder();
    surfaceHolder.addCallback(this);

    gameLoop =  new GameLoop(this, surfaceHolder);

    //Определение игрока

    player = new Player(getContext(), 2*500, 500, 30);
    setFocusable(true);

}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Сенсорные события
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                player.setPosition((double)event.getX(), (double) event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                player.setPosition((double)event.getX(), (double) event.getY());
                return true;
        }

        return super.onTouchEvent(event);
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

        player.draw(canvas);
    }
    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS:" + averageUPS, 100, 100, paint);

    }
    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS:" + averageFPS, 100, 200, paint);

    }

    public void update() {
        //Состояние обновления игры
        player.update();
    }
}

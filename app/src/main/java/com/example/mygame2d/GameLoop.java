package com.example.mygame2d;

import android.graphics.Canvas;
import android.view.SurfaceHolder;



class GameLoop extends Thread{
    public static final double MAX_UPS = 30.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private Game game;
    private boolean isRunning = false;
     private SurfaceHolder surfaceHolder;
    private double averageUPS;
    private double averageFPS;


    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
         this.game  = game;
         this.surfaceHolder = surfaceHolder;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }


     @Override
     public void run() {
         super.run();

         //Объявление времени и подсчет количества циклов

         int updateCount = 0;
         int frameCount = 0;

         long startTime;
         long elapsedTime;
         long sleepTime;


         //Игр.цикл
         Canvas canvas = null;
         startTime = System.currentTimeMillis();
         while(isRunning){

             // Попытка обновления и визуализации игры
             try{
                 canvas = surfaceHolder.lockCanvas();
                 synchronized (surfaceHolder) {
                     game.update();
                     game.draw(canvas);
                     updateCount++;
                 }
             } catch (IllegalArgumentException e) {
                 e.printStackTrace();
             } finally {
                 if(canvas !=null){
                     try{
                         surfaceHolder.unlockCanvasAndPost(canvas);
                         frameCount++;
                     }catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
             }

             // Остановка игро.цикла чтобы не превышать частоту обновления
             elapsedTime = System.currentTimeMillis() - startTime;
             sleepTime = (long)(updateCount*UPS_PERIOD - elapsedTime);
             if(sleepTime > 0 ) {
                 try {
                     sleep(sleepTime);
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }
             }

             // Если рендеринг долгий, пропуск кадров чтобы не отставать от частоты обновления

             while(sleepTime < 0 && updateCount <MAX_UPS-1 ) {
                 game.update();
                 updateCount++;
                 elapsedTime = System.currentTimeMillis() - startTime;
                 sleepTime = (long)  (updateCount*UPS_PERIOD - elapsedTime);
             }


             // Рассчитать среднее значение частот обновления и кадров
              elapsedTime = System.currentTimeMillis() - startTime;
              if(elapsedTime >- 1000) {
                  averageUPS = updateCount / (1E-3 * elapsedTime);
                  averageFPS = frameCount / (1E-3 * elapsedTime);
                  updateCount = 0;
                  frameCount = 0;
                  startTime = System.currentTimeMillis();
              }
         }
     }
 }

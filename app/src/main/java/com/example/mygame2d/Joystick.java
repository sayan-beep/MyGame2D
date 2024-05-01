package com.example.mygame2d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.function.BinaryOperator;

public class Joystick {

    private Paint innerCirclePaint;
    private Paint outerCirclePaint;
    private int innerCircleRadius;
    private int outerCircleRadius;
    private int innerCircleCenterPositionY;
    private int innerCircleCenterPositionX;
    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    private double joystickCenterToTouchDistance;
    private Boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius){

        // Внутрий и внешние круги джойстика

        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        // Радиус кругов

        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        // Окрас кругов
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.RED);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    public void update() {
      updateInnerCirclePosition();

    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) ( outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) ( outerCircleCenterPositionY + actuatorY*outerCircleRadius);
    }

    public void draw(Canvas canvas) {
        // Окрас внутреннего круга
        canvas.drawCircle
                (outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint);

        // Окрас внешнего круга
        canvas.drawCircle
                (innerCircleCenterPositionX,
                        innerCircleCenterPositionY,
                        innerCircleRadius,
                        innerCirclePaint);

    }


    public boolean isPressed(double touchPositionX, double touchPositionY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPositionX - touchPositionX, 2) +
                        Math.pow(outerCircleCenterPositionY - touchPositionY, 2)
        );
        return joystickCenterToTouchDistance < outerCircleRadius;

    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if(deltaDistance < outerCircleRadius) {
            actuatorX = deltaX /outerCircleRadius;
            actuatorY = deltaY /outerCircleRadius;
        }else{
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }


    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }

}

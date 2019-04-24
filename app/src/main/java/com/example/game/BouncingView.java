package com.example.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.Random;

public class BouncingView extends View implements SensorEventListener {
    float xMin;
    float yMin;
    float xMax;
    float yMax;
    float speedX;
    float speedY;
    float radius;
    float posX;
    float posY;
    Paint paint;
    RectF rectF;
    int foodX, foodY, foodSize;
    double speedVectorValue;
    Random random;
    int score;
    Context context;
    float speedFactor;
    SensorManager sensorManager;
    Sensor sensor;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onDraw(Canvas canvas) {


        paint.setColor(Color.GREEN);
        canvas.drawRect(foodX - foodSize, foodY - foodSize, foodX + foodSize, foodY + foodSize, paint);

        paint.setColor(Color.RED);
        canvas.drawOval(posX - radius, posY - radius, posX + radius, posY + radius, paint);
        paint.setColor(Color.WHITE);

        paint.setColor(Color.BLACK);
        paint.setTextSize(80);
        canvas.drawText(String.valueOf(score), xMax / 2, 100, paint);
        update();
        invalidate();


    }


    @Override
    public void onSizeChanged(int width, int height,
                              int oldW, int oldH) {
        xMax = width - radius;
        yMax = height - radius;

    }


    void update() {

        /*Jedzenie*/
        float vectorX = posX - foodX;
        float vectorY = posY - foodY;
        float vectorValue = (float) Math.sqrt(Math.pow(vectorX, 2) + Math.pow(vectorY, 2));
        if (vectorValue < radius) {
            speedFactor*=1.05;
            radius+=2;
            score++;
            this.randomFood();
        }

        /*Ruch*/
        posY += speedY;
        posX += speedX;


        /*Kolizje */
        if (posX > xMax || (posY - radius) < 0 || posY > yMax || posX - radius < 0) {
            sensorManager.unregisterListener(this,sensor);
            setWillNotDraw(true);
            Intent intent = new Intent(context,Summary.class);
            intent.putExtra("SCORE",score);
            context.startActivity(intent);
        }



    }


    public BouncingView(Context context) {
        super(context);
        random = new Random(System.currentTimeMillis());
//        Toast.makeText(getContext(), sensorManager.toString(), Toast.LENGTH_LONG).show();
        this.context = context;
        /*Screen resolution*/
        Display d = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int xMax = d.getWidth();
        int yMax = d.getHeight();


        /*Ball size*/
        radius = (float) (xMax * 0.05);


        /*Random food at start*/
        foodX = (int) (random.nextInt((int) (xMax - 2 * radius)) + radius);
        foodY = (int) (random.nextInt((int) (xMax - 2 * radius)) + radius);
        foodSize = (int) (random.nextInt((int) (radius / 2 - radius / 8)) + radius / 8);

        /*center ball*/
        posX = (xMax - radius) / 2;
        posY = (yMax - radius) / 2;
        speedX = speedY = 0;
        speedFactor = 1;

        paint = new Paint();
        rectF = new RectF();

        speedVectorValue = Math.sqrt(speedX * speedX + speedY * speedY);
        /*Gyroscope*/
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_FASTEST);

        score = 0;

        Log.i("Konstruktor", this.toString());
    }

    private void randomFood() {
        /*Food position*/
        foodX = (int) (random.nextInt((int) (xMax - 2 * radius)) + radius);
        foodY = (int) (random.nextInt((int) (xMax - 2 * radius)) + radius);
        foodSize = (int) (random.nextInt((int) (radius / 1.5 - radius / 6)) + radius / 6);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = (sensorEvent.values[0]*1000)/1000;
        float y = (sensorEvent.values[1]*1000)/1000;
       /* double signX = Math.signum(x);
        double signY = Math.signum(y);*/

        speedX = (float) Math.round(x * (-1) * 6 * 10*speedFactor) / 10;
        speedY = (float) Math.round(y * 6 * 10 *speedFactor) / 10;
        // Log.i("Speed", "speedX: " + speedX + "------ speedY: " + speedY + "food size" + foodSize);
        Log.i("klasa", this.toString());

        //  Toast.makeText(getContext(), "x: " + x + " y: " + y, Toast.LENGTH_LONG).show();
        //  speedX = (float) (signX*(-1)* Math.pow(x,4));
        //  speedY = (float) (signY* Math.pow(y,4));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public String toString() {
        return "BouncingView{" +
                "xMin=" + xMin +
                ", yMin=" + yMin +
                ", xMax=" + xMax +
                ", yMax=" + yMax +
                ", speedX=" + speedX +
                ", speedY=" + speedY +
                ", radius=" + radius +
                ", posX=" + posX +
                ", posY=" + posY +
                ", paint=" + paint +
                ", rectF=" + rectF +
                ", foodX=" + foodX +
                ", foodY=" + foodY +
                ", foodSize=" + foodSize +
                ", speedVectorValue=" + speedVectorValue +
                ", random=" + random +
                ", sensorManager=" + sensorManager +
                ", sensor=" + sensor +
                '}';
    }
}



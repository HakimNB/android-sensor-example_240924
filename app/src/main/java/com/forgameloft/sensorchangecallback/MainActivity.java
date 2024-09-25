package com.forgameloft.sensorchangecallback;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.androidgamesdk.GameActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends GameActivity implements SensorEventListener {
    static {
        System.loadLibrary("sensorchangecallback");
    }

    private SensorManager mSensorManager;
    private Sensor mHingeSensor;

    @Override
    protected void onStart() {
        super.onStart();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mHingeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HINGE_ANGLE);
        List<Sensor> allSensor = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.d("MainActivity", "MainActivity.onStart");
        for(final Sensor s: allSensor) {
            Log.d("MainActivity", "MainActivity.sensor: " + s.getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mHingeSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            hideSystemUi();
        }
    }

    private void hideSystemUi() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String debug = "MainActivity.onSensorChanged: " +
                " ts: " + sensorEvent.timestamp +
                " acc: " + sensorEvent.accuracy +
                " first: " + sensorEvent.firstEventAfterDiscontinuity +
                " name: " + sensorEvent.sensor.getName() +
                " values: " + Arrays.toString(sensorEvent.values);
        Log.d("MainActivity", debug);
        Toast.makeText(this, debug, Toast.LENGTH_SHORT).show();

        // Device
        // 2024-09-24 14:45:46.959 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onAccuracyChanged: {Sensor name="Hinge Angle (wake-up)", vendor="Google", version=1, type=36, maxRange=180.0, resolution=5.0, power=0.001, minDelay=0} i = 3
        // 2024-09-24 14:45:46.959 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@8e088d2 timestamp: 351529014864209 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [0.0]
        // 2024-09-24 14:45:47.723 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@5d834ae timestamp: 352851581212937 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [5.0]
        // 2024-09-24 14:45:47.724 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@8e088d2 timestamp: 352851581212937 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [5.0]
        // 2024-09-24 14:45:47.886 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@5d834ae timestamp: 352851777423147 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [50.0]
        // 2024-09-24 14:45:47.887 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@8e088d2 timestamp: 352851777423147 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [50.0]
        // 2024-09-24 14:45:47.894 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@5d834ae timestamp: 352851797304474 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [55.0]
        // 2024-09-24 14:45:47.895 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@8e088d2 timestamp: 352851797304474 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [55.0]
        // 2024-09-24 14:45:47.924 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@5d834ae timestamp: 352851817185802 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [60.0]
        // 2024-09-24 14:45:47.924 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@8e088d2 timestamp: 352851817185802 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [60.0]
        // 2024-09-24 14:45:47.932 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@5d834ae timestamp: 352851837066804 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [65.0]
        // 2024-09-24 14:45:47.932 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@8e088d2 timestamp: 352851837066804 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [65.0]
        // 2024-09-24 14:45:48.963 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@5d834ae timestamp: 352852870885230 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [60.0]
        // 2024-09-24 14:45:48.964 29183-29183 MainActivity            com...gameloft.sensorchangecallback  D  MainActivity.onSensorChanged: android.hardware.SensorEvent@8e088d2 timestamp: 352852870885230 accuracy: 3 firstEventAfterDiscontinuity: false sensor: Hinge Angle (wake-up) values: [60.0]

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("MainActivity", "MainActivity.onAccuracyChanged: " + sensor.toString() + " i = " + i);
    }
}
package com.forgameloft.sensorchangecallback;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.util.Consumer;
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter;
import androidx.window.layout.DisplayFeature;
import androidx.window.layout.FoldingFeature;
import androidx.window.layout.WindowInfoTracker;
import androidx.window.layout.WindowLayoutInfo;

import com.google.androidgamesdk.GameActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends GameActivity implements SensorEventListener {
    static {
        System.loadLibrary("sensorchangecallback");
    }

    public static MainActivity instance = null;

    private SensorManager mSensorManager;
    private Sensor mHingeSensor;

    private WindowInfoTrackerCallbackAdapter mWindowInfoTracker;
    private final LayoutStateChangeCallback mLayoutStateChangeCallback = new LayoutStateChangeCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.instance = this;
        mWindowInfoTracker = new WindowInfoTrackerCallbackAdapter(WindowInfoTracker.getOrCreate(this));
    }
    
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

        mWindowInfoTracker.addWindowLayoutInfoListener(this, Runnable::run, mLayoutStateChangeCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mHingeSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWindowInfoTracker.removeWindowLayoutInfoListener(mLayoutStateChangeCallback);
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

    private long mSensorLastTimestamp = 0;
    private String mSensorLastValues = "UNDEF";
    private String mFeatureLastState = "UNDEF";
    private String mFeatureLastOrientation = "UNDEF";

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if ( sensorEvent.sensor.getType() == Sensor.TYPE_HINGE_ANGLE ) {
            String sensorValues = Arrays.toString(sensorEvent.values);
            String debug = "MainActivity.onSensorChanged:" +
                    " ts: " + sensorEvent.timestamp +
                    " type: " + sensorEvent.sensor.getType() +
                    " acc: " + sensorEvent.accuracy +
                    " first: " + sensorEvent.firstEventAfterDiscontinuity +
                    " name: " + sensorEvent.sensor.getName() +
                    " values: " + sensorValues;
            Log.d("MainActivity", debug);

            mSensorLastTimestamp = sensorEvent.timestamp;
            mSensorLastValues = sensorValues;
        }

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

    class LayoutStateChangeCallback implements Consumer<WindowLayoutInfo> {
        @Override
        public void accept(WindowLayoutInfo newLayoutInfo) {
            MainActivity.this.runOnUiThread( () -> {
                // Use newLayoutInfo to update the layout.
                List<DisplayFeature> displayFeatures = newLayoutInfo.getDisplayFeatures();
                Log.d("MainActivity", "MainActivity.WindowLayoutInfo: " + newLayoutInfo.toString());
                for ( final DisplayFeature d : displayFeatures ) {
                    if ( d instanceof FoldingFeature ) {
                        FoldingFeature f = (FoldingFeature) d;
                        String debug = "MainActivity.FoldingFeature:" +
                            " state: " + f.getState() +
                            " occlusion: " + f.getOcclusionType() +
                            " orientation: " + f.getOrientation() +
                            " separating: " + f.isSeparating() +
                            " bounds: " + f.getBounds().toString();
                        Log.d("MainActivity", debug);

                        String szFeatureState = f.getState().toString();
                        String szFeatureOrientation = f.getOrientation().toString();
                        boolean bEqualState = szFeatureState.equalsIgnoreCase(mFeatureLastState);
                        boolean bEqualOrientation = szFeatureOrientation.equalsIgnoreCase(mFeatureLastOrientation);
                        if ( !(bEqualState && bEqualOrientation) ) {
                            StringBuffer sbDebug = new StringBuffer();
                            if ( !bEqualState ) {
                                sbDebug.append(mFeatureLastState);
                                sbDebug.append(" => ");
                                sbDebug.append(szFeatureState);
                                sbDebug.append(" : ");
                            }
                            if ( !bEqualOrientation ) {
                                sbDebug.append(mFeatureLastOrientation);
                                sbDebug.append(" => ");
                                sbDebug.append(szFeatureOrientation);
                                sbDebug.append(" : ");
                            }
                            sbDebug.append(mSensorLastValues);

                            mFeatureLastState = szFeatureState;
                            mFeatureLastOrientation = szFeatureOrientation;
                            Log.d("MainActivity", "MainActivity.FoldingFeature CHANGES: " + sbDebug.toString());
                            Toast.makeText(MainActivity.instance, sbDebug.toString(), Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(MainActivity.instance, debug, Toast.LENGTH_SHORT).show();
                        // MainActivity.FoldingFeature state: FLAT occlusion: NONE orientation: VERTICAL separating: false
                    }
                }
            });
        }
    }
}
package com.example.hellomotionsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private  Sensor Acceleromter;

    private TextView acc_x;
    private TextView acc_y;
    private TextView acc_z;
    private  double alpha;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent){

        float gravity [] = new float[3];
        float alpha = 0.8f;
        gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

        double linear_acceleration [] = new double[3];
        linear_acceleration[0] = sensorEvent.values[0];
        linear_acceleration[1] = sensorEvent.values[1];
        linear_acceleration[2] = sensorEvent.values[2] - gravity[2];

        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            acc_x.setText("mag_Xaxis:" + linear_acceleration[0]);
            acc_y.setText("mag_Yaxis:" + linear_acceleration[1]);
            acc_z.setText("mag_Zaxis:" + linear_acceleration[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acc_x = (TextView) findViewById(R.id.acc_Xaxis);
        acc_y = (TextView) findViewById(R.id.acc_Yaxis);
        acc_z = (TextView) findViewById(R.id.acc_Zaxis);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Acceleromter = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,Acceleromter,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
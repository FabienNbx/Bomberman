package iut.projet.bomberman.Controllers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorController implements SensorEventListener {
    OnSensorValueChangedListener listener;
    public SensorController(OnSensorValueChangedListener listener) {
        this.listener = listener;
    }
@Override
public void onSensorChanged(SensorEvent event) {
        float x;
        float y;
        x = event.values[0];  //rotation autour de l'axe x
        y = event.values[1]; //rotation autour de l'axe y
        listener.onSensorValueChanged(x,y);
        }

@Override
public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
}
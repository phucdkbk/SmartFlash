package com.alandk.smartflash.activity;

import java.util.Calendar;
import java.util.Timer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.alandk.smartflash.common.ColorPicker;

public class MainActivity extends Activity {

    ImageButton btnSwitch;
    Button buttonScreenlight;

    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Parameters params;
    MediaPlayer mp;
    private ColorPicker colorPicker;

    PendingIntent pi;
    BroadcastReceiver br;
    AlarmManager am;

    public boolean isFlashOn() {
        return isFlashOn;
    }

    public void setFlashOn(boolean isFlashOn) {
        this.isFlashOn = isFlashOn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_picker);

        // flash switch button
        btnSwitch = (ImageButton) findViewById(R.id.btnSwitch);
        buttonScreenlight = (Button) findViewById(R.id.buttonScreenLight);
        //colorPicker = (ColorPicker) findViewById(R.id.colorPicker);

        /*
         * First check if device is supporting flashlight or not
         */
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                }
            });
            alert.show();
            return;
        }

        // get the camera
        getCamera();

        // displaying button image
        toggleButtonImage();

        /*
         * Switch button click event to toggle flash on/off
         */
        btnSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    // turn off flash
                    turnOffFlash();
                } else {
                    // turn on flash
                    turnOnFlash();
                }
            }
        });

        buttonScreenlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				// TODO Auto-generated method stub
                //showScreenLight();
                showColorPicker();

            }
        });

//        Timer timer = new Timer();
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 10);
//        timer.scheduleAtFixedRate(new ScheduleRepeat(this), cal.getTime() , 2000);
        setup();
        //am.setRepeating(type, triggerAtMillis, intervalMillis, operation);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, pi);
    }

    private void showScreenLight() {
        Intent intent = new Intent(this, ScreenLightActivity.class);
        startActivityForResult(intent, 0);
    }
    
    private void showColorPicker() {
        Intent intent = new Intent(this, ColorPickerActivity.class);
        startActivityForResult(intent, 0);
    }

    public void turnOnOffFlash() {
        if (isFlashOn) {
            // turn off flash
            turnOffFlash();
        } else {
            // turn on flash
            turnOnFlash();
        }
    }

    private void setup() {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent i) {
                Toast.makeText(c, "Rise and Shine!", Toast.LENGTH_LONG).show();
                turnOnOffFlash();
            }
        };
        registerReceiver(br, new IntentFilter("com.authorwjf.wakeywakey"));
        pi = PendingIntent.getBroadcast(this, 0, new Intent("com.authorwjf.wakeywakey"), 0);
        am = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
    }

    /*
     * Get the camera
     */
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }

    /*
     * Turning On flash
     */
    public void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            //playSound();

//            params = camera.getParameters();
//            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
//            camera.setParameters(params);
//            camera.startPreview();
//            isFlashOn = true;
            // changing button/switch image
            //toggleButtonImage();
        }

    }

    /*
     * Turning Off flash
     */
    public void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            playSound();

            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

            // changing button/switch image
            toggleButtonImage();
        }
    }

    /*
     * Playing sound
     * will play button toggle sound on flash on / off
     * */
    private void playSound() {
        if (isFlashOn) {
            mp = MediaPlayer.create(MainActivity.this, R.raw.light_switch_off);
        } else {
            mp = MediaPlayer.create(MainActivity.this, R.raw.light_switch_on);
        }
        mp.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mp.start();
    }

    /*
     * Toggle switch button images
     * changing image states to on / off
     * */
    private void toggleButtonImage() {
        if (isFlashOn) {
            btnSwitch.setImageResource(R.drawable.btn_switch_on);
        } else {
            btnSwitch.setImageResource(R.drawable.btn_switch_off);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // on pause turn off the flash
        turnOffFlash();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // on resume turn on the flash
        if (hasFlash) {
            turnOnFlash();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // on starting the app get the camera params
        getCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

}

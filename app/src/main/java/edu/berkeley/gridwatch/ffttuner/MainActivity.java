package edu.berkeley.gridwatch.ffttuner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.util.fft.FFT;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private Button startbtn;
    private Button spambtn;

    private TextView state_txt;

    private TextView display_txt_view;

    private boolean mFFTStarted = false;
    private boolean mFFTFinished = false;

    private static String mFFTArray = "";
    private static String mFFTMsg = "";

    private final int sampleRate = 22050;
    private final int fftsize = 32768/2;
    private final int overlap = fftsize/2;

    private static WorkEventResultReceiver m_resultReceiver;


    String display_txt_str = "-1";

    //int UI_update_rate_ms = 2000;

    private Handler ui_update;

    private RxPermissions mPermissions;
    //private StreamAudioRecorder mStreamAudioRecorder;
    //private StreamAudioPlayer mStreamAudioPlayer;
    //private AudioProcessor mAudioProcessor;
    //private FileOutputStream mFileOutputStream;
    private boolean mIsRecording = false;



    private RxPermissions getRxPermissions() {
        if (mPermissions == null) {
            mPermissions = new RxPermissions(this);
        }
        return mPermissions;
    }



    private void stop_ui() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startbtn.setText("Start");
                spambtn.setText("Start");
                mIsRecording = false;
                state_txt.setText("stopped");
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        m_resultReceiver = new WorkEventResultReceiver(null);



        //Handle UI
        startbtn = (Button) findViewById(R.id.startbtn);
        spambtn = (Button) findViewById(R.id.spam_button);

        state_txt = (TextView) findViewById(R.id.state_text);
        display_txt_view = (TextView) findViewById(R.id.display_text);

        /*
        ui_update = new Handler();
        ui_update.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mIsRecording) {
                    Log.e("display", display_txt_str);
                    display_txt_view.setText(display_txt_str);
                } else {
                    Log.e("display", "no longer recording");
                }
                ui_update.postDelayed(this, UI_update_rate_ms);
            }
        }, UI_update_rate_ms);
        */

        //Handle Permissions
        boolean isPermissionsGranted = getRxPermissions().isGranted(RECORD_AUDIO);
        if (!isPermissionsGranted) {
            state_txt.setText("press start to get permissions");
        }

        startbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                if (mIsRecording) {
                    //stopRecord();
                    stop_ui();
                    stopFFT();
                } else {
                    boolean isPermissionsGranted = getRxPermissions().isGranted(RECORD_AUDIO);

                    if (!isPermissionsGranted) {
                        getRxPermissions()
                                .request(RECORD_AUDIO)
                                .subscribe(granted -> {
                                    if (granted) {
                                        Toast.makeText(getApplicationContext(), "Permission granted",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "Permission not granted", Toast.LENGTH_SHORT).show();
                                    }
                                }, Throwable::printStackTrace);
                    } else {
                        //doFFT(); //MAIN CALL
                        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, fftsize, overlap);
                        ((FFTTuner) getApplication()).setDispatcher(dispatcher);
                        startbtn.setText("Stop");
                        mIsRecording = true;
                        state_txt.setText("running");
                        startFFT();
                    }
                }

            }
        });

        spambtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                boolean isPermissionsGranted = getRxPermissions().isGranted(RECORD_AUDIO);
                if (!isPermissionsGranted) {
                    getRxPermissions()
                            .request(RECORD_AUDIO)
                            .subscribe(granted -> {
                                if (granted) {
                                    Toast.makeText(getApplicationContext(), "Permission granted",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Permission not granted", Toast.LENGTH_SHORT).show();
                                }
                            }, Throwable::printStackTrace);
                } else {
                    //doFFT(); //MAIN CALL
                    AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, fftsize, overlap);
                    ((FFTTuner) getApplication()).setDispatcher(dispatcher);
                    spambtn.setText("Stop");
                    mIsRecording = true;
                    state_txt.setText("spamming");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            Log.e("spam", "restarting");
                            startFFT();
                            Random rand = new Random();
                            boolean val = rand.nextInt(25)==0;
                            if (!val) { // reschedule for a bit
                                handler.postDelayed(this, 100);
                            }
                        }
                    }, 100);
                }
            }
        });

    }

    private void startFFT() {
        mFFTStarted = true;
        launch_service(FFTService.class, IntentConfig.START);
    }

    private void stopFFT() {
        mFFTStarted = true;
        launch_service(FFTService.class, IntentConfig.STOP);
    }

    private void launch_service(Class a, String msg) {
        Intent intent = new Intent(this, a);
        intent.putExtra(IntentConfig.RECEIVER_KEY, m_resultReceiver);
        if (msg != null) {
            Log.e("starting with message", msg);
            intent.putExtra(IntentConfig.MESSAGE_KEY, msg);
        }
        this.startService(intent);
    }

    class WorkEventResultReceiver extends ResultReceiver
    {
        public WorkEventResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == IntentConfig.FFT) {
                Log.e("received", "FFT");
                mFFTArray = resultData.getString(IntentConfig.FFT_ARRAY);
                mFFTMsg = resultData.getString(IntentConfig.MESSAGE_KEY);
                if (mFFTMsg != null) {
                    if (mFFTMsg.equals(IntentConfig.DONE)) {
                        Log.e("ui", "done");
                        mFFTFinished = true;
                        stop_ui();
                    } else {
                        Log.e("ui", "updating");
                        Log.e("ui", mFFTMsg);
                        display_txt_str = mFFTMsg;
                    }
                }
            }
        }
    }






}

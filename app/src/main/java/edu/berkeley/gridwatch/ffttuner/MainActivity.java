package edu.berkeley.gridwatch.ffttuner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.util.fft.FFT;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private Button startbtn;
    private TextView state_txt;
    private TextView array_txt;
    private RxPermissions mPermissions;
    //private StreamAudioRecorder mStreamAudioRecorder;
    //private StreamAudioPlayer mStreamAudioPlayer;
    //private AudioProcessor mAudioProcessor;
    //private FileOutputStream mFileOutputStream;
    private boolean mIsRecording = false;

    private final int sampleRate = 22050;
    private final int fftsize = 32768/2;
    private final int overlap = fftsize/2;

    private AudioDispatcher dispatcher;

    private RxPermissions getRxPermissions() {
        if (mPermissions == null) {
            mPermissions = new RxPermissions(this);
        }
        return mPermissions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        startbtn = (Button) findViewById(R.id.startbtn);
        state_txt = (TextView) findViewById(R.id.state_text);
        array_txt = (TextView) findViewById(R.id.array_text);

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
                    startbtn.setText("Start");
                    mIsRecording = false;
                    state_txt.setText("stopped");
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
                        //startRecord();
                        doFFT();
                        startbtn.setText("Stop");
                        mIsRecording = true;
                        state_txt.setText("running");
                    }
                }

            }
        });

    }

    private void doFFT() {
        try {
            dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, fftsize, overlap);

            AudioProcessor fftProcessor = new AudioProcessor(){

                FFT fft = new FFT(fftsize);
                float[] amplitudes = new float[fftsize/2];

                @Override
                public void processingFinished() {
                    // TODO Auto-generated method stub
                }

                @Override
                public boolean process(AudioEvent audioEvent) {
                    float[] audioFloatBuffer = audioEvent.getFloatBuffer();
                    float[] transformbuffer = new float[fftsize*2];
                    System.arraycopy(audioFloatBuffer, 0, transformbuffer, 0, audioFloatBuffer.length);
                    fft.forwardTransform(transformbuffer);
                    fft.modulus(transformbuffer, amplitudes);
                    
                    Log.e("amp", amplitudes.toString());
                    Log.e("fft", fft.toString());

                    return true;
                }

            };
            dispatcher.addAudioProcessor(fftProcessor);
            new Thread(dispatcher,"Audio dispatching").start();

        } catch (IllegalStateException e) {
            Log.e("error", e.getMessage());
        }
    }


}

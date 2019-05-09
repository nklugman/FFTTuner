package edu.berkeley.gridwatch.ffttuner;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.util.fft.FFT;

public class FFTService  extends IntentService {

    private final int sampleRate = 22050;
    private final int fftsize = 32768/2;
    private final int overlap = fftsize/2;

    private DecimalFormat df;


    AudioDispatcher dispatcher;

    Thread audioThread;

    String peaks = "";
    String display_txt_str = "";

    private Handler wd;

    private static ResultReceiver mResultReceiver;

    public FFTService() {
        super("FFTService");
    }



    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.e("fft", "intent hit");

        df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        mResultReceiver = workIntent.getParcelableExtra(IntentConfig.RECEIVER_KEY);
        dispatcher = ((FFTTuner) this.getApplication()).getDispatcher();

        String msg = workIntent.getExtras().getString(IntentConfig.MESSAGE_KEY);

        if (msg != null) {
            if (msg.equals(IntentConfig.START)) {
                Log.e("info", "starting fft");
                doFFT();
            }
            if (msg.equals(IntentConfig.STOP)) {
                Log.e("info", "stopping fft");
                stop();
            }
        } else {
            Log.e("error", "message null");
        }
    }


    private void report_done() {
        peaks = peaks.substring(0, peaks.length()-1);
        Log.e("peaks", peaks);

        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.FFT_ARRAY, peaks);
        bundle.putString(IntentConfig.MESSAGE_KEY, IntentConfig.DONE);
        mResultReceiver.send(IntentConfig.FFT, bundle);


    }

    private void report_update() {
        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.FFT_ARRAY, peaks);
        bundle.putString(IntentConfig.MESSAGE_KEY, display_txt_str);
        mResultReceiver.send(IntentConfig.FFT, bundle);
    }

    private void stop() {
        Log.e("stopping", "hit");
        try {


            dispatcher.stop();

        } catch (Exception e) {
            Log.e("error", "stopping thread: " + e.getLocalizedMessage());
        }
        ((FFTTuner) getApplication()).setIsRunning(false);
        ((FFTTuner) getApplication()).setFFTString(peaks);
        report_done();

    }

    private void doFFT() {

        boolean running = ((FFTTuner) getApplication()).getIsRunning();

            try {
                //dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, fftsize, overlap);
                AudioProcessor fftProcessor = new AudioProcessor() {
                    FFT fft = new FFT(fftsize);
                    float[] amplitudes = new float[fftsize / 2];

                    @Override
                    public void processingFinished() {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public boolean process(AudioEvent audioEvent) {

                        ((FFTTuner) getApplication()).setIsRunning(true);


                        float[] audioFloatBuffer = audioEvent.getFloatBuffer();
                        float[] transformbuffer = new float[fftsize * 2];
                        System.arraycopy(audioFloatBuffer, 0, transformbuffer, 0, audioFloatBuffer.length);
                        fft.forwardTransform(transformbuffer);
                        fft.modulus(transformbuffer, amplitudes);

                        //Log.i("amp", Arrays.toString(amplitudes));

                        Log.e("recording", "hit");

                        display_txt_str = "";
                        String cur_peaks = "";


                        for (int i = 0; i < audioFloatBuffer.length; i++) {
                            float hz = (float) fft.binToHz(i, sampleRate);
                            try {
                                if (i <= amplitudes.length) {
                                    float mag = amplitudes[i];

                                /*
                                if (hz >= 54 && hz <= 66 ||
                                        hz >= 114 && hz <= 126 ||
                                        hz >= 174 && hz <= 186 ||
                                        hz >= 234 && hz <= 246 ||
                                        hz >= 294 && hz <= 306) {
                                    */
                                    if (hz >= 54 && hz <= 66) {
                                        //Log.i("fft", String.valueOf(hz) + "," + String.valueOf(mag));
                                        String new_mag = df.format(mag);
                                        display_txt_str = display_txt_str + String.valueOf(hz) + "\t\t" + String.valueOf(mag) + "\n";
                                        cur_peaks = cur_peaks + String.valueOf(new_mag) + ",";
                                    }
                                } else { //*****TODO**** This happens a lot... index into amplitudes might be wrong...
                                    //Log.e("error", "i:" + String.valueOf(i) + " " + "length:" + String.valueOf(amplitudes.length));
                                }
                            } catch (Exception e) {
                                Log.e("error", "in process, " + e.getLocalizedMessage());
                            }

                        }

                        //Log.e("amp", amplitudes.toString());
                        //Log.e("fft", fft.toString());

                        report_update();
                        peaks = peaks + cur_peaks.substring(0, cur_peaks.length() - 1) + "@";

                        Log.e("seconds", String.valueOf(dispatcher.secondsProcessed()));
                        if (dispatcher.secondsProcessed() > 4) {
                            stop();
                        }


                        return true;
                    }

                };
                dispatcher.addAudioProcessor(fftProcessor);
                audioThread = new Thread(dispatcher, "Audio dispatching");
                audioThread.start();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("handler", "timeout is done");
                        stop();
                    }
                }, 2000);
            } catch (IllegalStateException e) {
                Log.e("error", e.getMessage());
            }

    }

}

package edu.berkeley.gridwatch.ffttuner;

import android.app.Application;

import be.tarsos.dsp.AudioDispatcher;

public class FFTTuner extends Application {

    private AudioDispatcher dispatcher;
    private boolean isRunning;
    private String fft_string;

    public AudioDispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher (AudioDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public String getFFTString() {
        return fft_string;
    }

    public void setFFTString(String fft_string) {
        this.fft_string = fft_string;
    }
}
package edu.berkeley.gridwatch.ffttuner;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

public class FFTService  extends IntentService {

    private static ResultReceiver mResultReceiver;

    public FFTService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.e("fft", "intent hit");
        mResultReceiver = workIntent.getParcelableExtra(IntentConfig.RECEIVER_KEY);
    }


}

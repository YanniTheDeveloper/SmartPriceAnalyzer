package ytd.smartpriceanalyzer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class EntrySplash extends AppCompatActivity {

    CountDownTimer mCountDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_splash);
        MainActivity.dbHelper = new ItemDatabaseHelper(this);
        ItemHandler.items = MainActivity.dbHelper.readData();
        skipTimer(2000,500);
        mCountDownTimer.start();
    }
    private void skipTimer(int time, int interval){
        mCountDownTimer=new CountDownTimer(time,interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("skipTimer", "onTick: nothing... ", null);
            }

            @Override
            public void onFinish() {
                //Do what you want
                EntrySplash.super.finish();
            }

        };

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
    }
}

package tw.sean.as_servicetest1_20181021;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private MyReceiver myReceiver;
    private IntentFilter filter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        myReceiver = new MyReceiver();
        filter.addAction("lune");
        //registerReceiver(myReceiver, filter);

        seekBar = findViewById(R.id.seekbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(myReceiver, filter);//註冊搭配解除註冊
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);//註冊搭配解除註冊
    }

    public void test1(View view){
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("start", true);
        startService(intent);
    }
    public void test2(View view){
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("stop", false);
        //intent.putExtra("key", (int)(Math.random()*49+1));
        startService(intent);
    }
    public void test3(View view){
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int len = intent.getIntExtra("len", -1);
            int now = intent.getIntExtra("now", -1);
            //Log.v("brad", "receive len = " + len);
            if (len != -1) {
                seekBar.setMax(len);
            }else if (now != -1) {
                seekBar.setProgress(now);
            }

        }
    }
}

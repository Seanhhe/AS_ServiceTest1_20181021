package tw.sean.as_servicetest1_20181021;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service {
    private MediaPlayer mediaPlayer;
    private Timer timer;

    public MyService() {
    }

    //為抽象方法，不可刪除
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer != null) return;
        mediaPlayer = MediaPlayer.create(this, R.raw.lune);
        int len = mediaPlayer.getDuration();//start()前get
        Intent intent = new Intent("lune");
        intent.putExtra("len", len);
        sendBroadcast(intent);//發送廣播，接收由MainActivity收


        timer = new Timer();
        timer.schedule(new MyTask(), 0, 100);
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                int now = mediaPlayer.getCurrentPosition();//get music play position
                //把值傳出去
                Intent intent = new Intent("lune");
                intent.putExtra("now", mediaPlayer.getCurrentPosition());
                sendBroadcast(intent);//發送廣播，接收由MainActivity收
            }

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isStart = intent.getBooleanExtra("start", false);
        if (isStart) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }else {
            if (mediaPlayer != null && mediaPlayer.isPlaying())
            mediaPlayer.pause();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

}

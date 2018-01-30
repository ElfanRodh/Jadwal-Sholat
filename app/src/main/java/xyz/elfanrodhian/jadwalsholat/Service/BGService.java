package xyz.elfanrodhian.jadwalsholat.Service;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.elfanrodhian.jadwalsholat.Generator.ServiceGenerator;
import xyz.elfanrodhian.jadwalsholat.Model.Jadwal;
import xyz.elfanrodhian.jadwalsholat.R;

/**
 * Created by elfar on 28/01/18.
 */

public class BGService extends Service {

    private Timer timer;
    private TimerTask timerTask, TF;
    private Handler handler = new Handler();
    GotService gotService;
    private static String subuh, dhuhur, ashar, magrib, isya, custom, custom2;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        gotService = ServiceGenerator.createService(GotService.class);

        final boolean[] play = {true, true, true, true, true};

        reload();

        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run(){

                        Calendar calander = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");

                        String time = simpleDateFormat.format(calander.getTime());
                        //your code is here
                        if (time.toLowerCase().equals(subuh) && play[0]) {
                            MediaPlayer adzan = MediaPlayer.create(getApplicationContext(), R.raw.adzan);
                            adzan.start();
                            play[0] = false;
                        } else if (time.toLowerCase().equals(dhuhur) && play[1]) {
                            MediaPlayer adzan = MediaPlayer.create(getApplicationContext(), R.raw.adzan);
                            adzan.start();
                            play[1] = false;
                        } else if (time.toLowerCase().equals(ashar) && play[2]) {
                            MediaPlayer adzan = MediaPlayer.create(getApplicationContext(), R.raw.adzan);
                            adzan.start();
                            play[2] = false;
                        } else if (time.toLowerCase().equals(magrib) && play[3]) {
                            MediaPlayer adzan = MediaPlayer.create(getApplicationContext(), R.raw.adzan);
                            adzan.start();
                            play[3] = false;
                        } else if (time.toLowerCase().equals(isya) && play[4]) {
                            MediaPlayer adzan = MediaPlayer.create(getApplicationContext(), R.raw.adzan);
                            adzan.start();
                            play[4] = false;
                        } else if (time.toLowerCase().equals(custom) && play[1]) {
                            MediaPlayer adzan = MediaPlayer.create(getApplicationContext(), R.raw.adzan);
                            adzan.start();
                            play[1] = false;
                        } else if (time.toLowerCase().equals(custom2) && play[2]) {
                            MediaPlayer adzan = MediaPlayer.create(getApplicationContext(), R.raw.adzan);
                            adzan.start();
                            play[2] = false;
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 5000, 5000);

        TF = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run(){
                        play[0] = true;
                        play[1] = true;
                        play[2] = true;
                        play[3] = true;
                        play[4] = true;
                        Log.d("SOKO", play.toString() );
                    }
                });
            }
        };

        timer.schedule(TF, 5000, 15*60*1000); //15 menit

        return START_STICKY;
    }

    private void reload() {
        Call<Jadwal> call = gotService.getJadwal();
        call.enqueue(new Callback<Jadwal>() {
            @Override
            public void onResponse(Call<Jadwal> call, final Response<Jadwal> response) {
                Log.d("SOKO","SUKSES"+response);
                subuh = response.body().getItems().get(0).getFajr().toString();
                dhuhur = response.body().getItems().get(0).getDhuhr().toString();
                ashar = response.body().getItems().get(0).getAsr().toString();
                magrib = response.body().getItems().get(0).getMaghrib().toString();
                isya = response.body().getItems().get(0).getIsha().toString();
                custom = "8:52 am";
                custom2 = "8:48 am";
            }
            @Override
            public void onFailure(Call<Jadwal> call, Throwable t) {
                Log.d("SOKO","Gagal "+t);
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

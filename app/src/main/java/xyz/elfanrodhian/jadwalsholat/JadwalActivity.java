package xyz.elfanrodhian.jadwalsholat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.elfanrodhian.jadwalsholat.Generator.ServiceGenerator;
import xyz.elfanrodhian.jadwalsholat.Model.Jadwal;
import xyz.elfanrodhian.jadwalsholat.Service.GotService;

public class JadwalActivity extends AppCompatActivity {

    GotService gotService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        Intent i = getIntent();

        gotService = ServiceGenerator.createService(GotService.class);

        final TextView txtSubuh, txtDhuhur, txtAshar, txtMagrib, txtIsya, txtTanggal;
        txtSubuh    = (TextView) findViewById(R.id.txtSubuh);
        txtDhuhur   = (TextView) findViewById(R.id.txtDhuhur);
        txtAshar    = (TextView) findViewById(R.id.txtAshar);
        txtMagrib   = (TextView) findViewById(R.id.txtMagrib);
        txtIsya     = (TextView) findViewById(R.id.txtIsya);
        txtTanggal  = (TextView) findViewById(R.id.txtTanggal);

        Call<Jadwal> call = gotService.getJadwalTgl(i.getStringExtra("link"));
        call.enqueue(new Callback<Jadwal>() {
            @Override
            public void onResponse(Call<Jadwal> call, Response<Jadwal> response) {
                Log.d("SOKO", "SUKSES");
                txtSubuh.setText(": " + response.body().getItems().get(0).getFajr().toString());
                txtDhuhur.setText(": " + response.body().getItems().get(0).getDhuhr().toString());
                txtAshar.setText(": " + response.body().getItems().get(0).getAsr().toString());
                txtMagrib.setText(": " + response.body().getItems().get(0).getMaghrib().toString());
                txtIsya.setText(": " + response.body().getItems().get(0).getIsha().toString());
                txtTanggal.setText("Tanggal : " + response.body().getItems().get(0).getDateFor().toString());
            }

            @Override
            public void onFailure(Call<Jadwal> call, Throwable t) {
                Log.d("SOKO", "Gagal " + t);
            }
        });
    }
}

package xyz.elfanrodhian.jadwalsholat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.elfanrodhian.jadwalsholat.Generator.ServiceGenerator;
import xyz.elfanrodhian.jadwalsholat.Model.Item;
import xyz.elfanrodhian.jadwalsholat.Model.Jadwal;
import xyz.elfanrodhian.jadwalsholat.Service.GotService;

public class TodayActivity extends AppCompatActivity {

    GotService gotService;
    Button btnReload, btnToday;
    RecyclerView rvJadwal;

    List<Item> mJadwalList = new ArrayList<>();
    RecyclerView.LayoutManager mLayoutManager;
    TextView txtSubuh, txtDhuhur, txtAshar, txtMagrib, txtIsya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        gotService = ServiceGenerator.createService(GotService.class);
        reloadData();
        txtSubuh    = (TextView) findViewById(R.id.txtSubuh);
        txtDhuhur   = (TextView) findViewById(R.id.txtDhuhur);
        txtAshar    = (TextView) findViewById(R.id.txtAshar);
        txtMagrib   = (TextView) findViewById(R.id.txtMagrib);
        txtIsya     = (TextView) findViewById(R.id.txtIsya);
    }

    private void reloadData() {
        Call<Jadwal> call = gotService.getJadwal();
        call.enqueue(new Callback<Jadwal>() {
            @Override
            public void onResponse(Call<Jadwal> call, Response<Jadwal> response) {
                Log.d("SOKO","SUKSES");
                txtSubuh.setText("Subuh : "+response.body().getItems().get(0).getFajr().toString());
                txtDhuhur.setText("Dhuhur : "+response.body().getItems().get(0).getDhuhr().toString());
                txtAshar.setText("Ashar : "+response.body().getItems().get(0).getAsr().toString());
                txtMagrib.setText("Magrib : "+response.body().getItems().get(0).getMaghrib().toString());
                txtIsya.setText("Isya : "+response.body().getItems().get(0).getIsha().toString());
            }

            @Override
            public void onFailure(Call<Jadwal> call, Throwable t) {
                Log.d("SOKO","Gagal "+t);
            }
        });
    }
}

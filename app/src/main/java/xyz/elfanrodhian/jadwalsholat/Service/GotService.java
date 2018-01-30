package xyz.elfanrodhian.jadwalsholat.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.elfanrodhian.jadwalsholat.Model.Jadwal;

/**
 * Created by elfar on 09/01/18.
 */

public interface GotService {
    @GET("bojonegoro/monthly.json?key=f833c55649485bc6afa119a746f77392")
    Call<Jadwal> getJadwal();

    @GET("bojonegoro/daily/{link}/true.json?key=f833c55649485bc6afa119a746f77392")
    Call<Jadwal> getJadwalTgl(@Path("link") String link);
}

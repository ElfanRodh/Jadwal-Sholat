package xyz.elfanrodhian.jadwalsholat.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TodayWeather {

    @SerializedName("pressure")
    @Expose
    private String pressure;
    @SerializedName("temperature")
    @Expose
    private String temperature;

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

}
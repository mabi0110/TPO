package zad2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    @SerializedName("list")
    List<MainList> mainList;

    public Weather(List<MainList> mainList) {
        this.mainList = mainList;
    }
    public List<MainList> getMainList() {
        return mainList;
    }

    public static class MainList{

        @SerializedName("main")
        MainObject mainObject;

        public MainList(MainObject mainObject) {
            this.mainObject = mainObject;
        }

        public MainObject getMainObject() {
            return mainObject;
        }

        public static class MainObject{
            Double temp;
            Integer pressure;
            Integer humidity;

            public MainObject(Double temp, Integer pressure, Integer humidity) {
                this.temp = temp;
                this.pressure = pressure;
                this.humidity = humidity;
            }

            public Double getTemp() {
                return temp;
            }

            public Integer getPressure() {
                return pressure;
            }

            public Integer getHumidity() {
                return humidity;
            }
        }

    }
}


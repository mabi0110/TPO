/**
 *
 *  @author Biziewska Małgorzata S22275
 *
 */

package zad2;


import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;
import zad2.io.Client;
import zad2.model.City;
import zad2.model.Weather;

import java.util.Currency;

import java.util.List;
import java.util.Locale;


public class Service {
    private static final String KEY = "9a3194d55e70fc631f78dd9ac4187ee5";
    private static final Gson gson = new Gson();
    private Locale countryLocale;

    public Service(String country) {
        Locale.setDefault(new Locale("", "Poland"));
        for (Locale locale : Locale.getAvailableLocales()) {
            if (country.equals(locale.getDisplayCountry())) {
                this.countryLocale = locale;
            }
        }
        if (this.countryLocale == null) {
            this.countryLocale = new Locale("", "Poland");
        }
    }

    public String getWeather(String city) {
        String geoURL = buildGeoURL(city);
        String jsonGeoResponse = Client.getResponse(geoURL);
        City[] cities = gson.fromJson(jsonGeoResponse, City[].class);
        Double lat = cities[0].getLat();
        Double lon = cities[0].getLon();
        String weatherURL = buildWeatherURL(lat, lon);
        String jsonWeatherResponse = Client.getResponse(weatherURL);
        Weather weather = gson.fromJson(jsonWeatherResponse, Weather.class);
        List<Weather.MainList> mainList = weather.getMainList();
        String weatherToShow = "";
        Double temp = mainList.get(0).getMainObject().getTemp();
        Integer pressure = mainList.get(0).getMainObject().getPressure();
        Integer humidity = mainList.get(0).getMainObject().getHumidity();
        weatherToShow += "Temperature: " + temp + " K\n"
                + "Pressure: " + pressure + "\n"
                + "Humidity: " + humidity;
        return weatherToShow;
    }

    public Double getRateFor(String base) {
        String currencyUrl = buildCurrencyURL(base);
        String currencyResponse = Client.getResponse(currencyUrl);
        JSONObject jsonObject = null;
        String rates = null;
        try {
            jsonObject = new JSONObject(currencyResponse);
            rates = jsonObject.get("rates").toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String rateValue = rates.replaceAll("[^0-9.]", "");
        return Double.parseDouble(rateValue);
    }

    public Double getNBPRate() {
        return 1.0;
    }

    private String buildCurrencyURL(String base) {
        return "https://api.exchangerate.host/latest?base=" + base +
                "&symbols=" + Currency.getInstance(countryLocale);
    }

    private String buildWeatherURL(Double lat, Double lon) {
        return "https://api.openweathermap.org/data/2.5/forecast?lat="
                + lat + "&lon=" + lon + "&appid=" + KEY;
    }

    private String buildGeoURL(String city) {
        return "https://api.openweathermap.org/geo/1.0/direct?q=" + city
                + "&appid=" + KEY;
    }
}


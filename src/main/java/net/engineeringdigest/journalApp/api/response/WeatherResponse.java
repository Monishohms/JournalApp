package net.engineeringdigest.journalApp.api.response;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class WeatherResponse {

    private ArrayList<Weather> weather;

    @Getter
    @Setter
    public static class Weather{
        private int id;
        private String main;
        private String description;
    }





}

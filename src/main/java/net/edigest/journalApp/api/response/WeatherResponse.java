package net.edigest.journalApp.api.response;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherResponse {

    private Coord coord;

    private ArrayList<Weather> weather;

    private String base;

    private Main main;

    private int visibility;

    private Wind wind;

    private Clouds clouds;

    private long dt;

    private Sys sys;

    private int timezone;

    private int id;

    private String name;

    private int cod;

    // ================= CLOUDS =================

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Clouds {
        private int all;
    }

    // ================= COORD =================

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Coord {
        private double lon;
        private double lat;
    }

    // ================= MAIN =================

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Main {

        private double temp;

        @JsonProperty("feels_like")
        private double feelsLike;

        @JsonProperty("temp_min")
        private double tempMin;

        @JsonProperty("temp_max")
        private double tempMax;

        private int pressure;

        private int humidity;

        @JsonProperty("sea_level")
        private int seaLevel;

        @JsonProperty("grnd_level")
        private int groundLevel;
    }

    // ================= SYS =================

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sys {

        private int type;

        private int id;

        private String country;

        private long sunrise;

        private long sunset;
    }

    // ================= WEATHER =================

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Weather {

        private int id;

        private String main;

        private String description;

        private String icon;
    }

    // ================= WIND =================

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Wind {

        private double speed;

        private int deg;
    }
}
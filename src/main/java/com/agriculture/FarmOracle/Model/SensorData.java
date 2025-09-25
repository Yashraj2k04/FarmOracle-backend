//this one is to check endpoints in webpage itself, this is not to be used as an api and will be removed later. (this is only for testing)

package com.agriculture.FarmOracle.Model;

public class SensorData {
    private double humidity;
    private double soilMoisture;
    private double temperature;
    private String time;

    public SensorData() {
    } // Needed for Firestore

    // Getters and setters

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getSoilMoisture() {
        return soilMoisture;
    }

    public void setSoilMoisture(double soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "humidity=" + humidity +
                ", soilMoisture=" + soilMoisture +
                ", temperature=" + temperature +
                ", time='" + time + '\'' +
                '}';
    }
}

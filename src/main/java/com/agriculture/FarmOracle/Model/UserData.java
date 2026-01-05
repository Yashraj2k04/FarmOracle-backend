package com.agriculture.FarmOracle.Model;

public class UserData {
    private double humidity;
    private double soilMoisture;
    private double temperature;
    private String time;
    private String documentIndex;
    private String crop_name;
    private Double price;
    private String date;
    private String district;
    private String state;
    private String predictedOnDate;
    private Integer nitrogen;
    private Integer phosphorus;
    private Integer potassium;
    private Double pH;

    public UserData() {
        // Needed for Firestore and JSON serialization
    }

    // Getters and Setters

    
    public Integer getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(Integer nitrogen) {
        this.nitrogen = nitrogen;
    }

    public Integer getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(Integer phosphorus) {
        this.phosphorus = phosphorus;
    }

    public Integer getPotassium() {
        return potassium;
    }

    public void setPotassium(Integer potassium) {
        this.potassium = potassium;
    }

    public Double getpH() {
        return pH;
    }

    public void setpH(Double pH) {
        this.pH = pH;
    }

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

    public String getDocumentIndex() {
        return documentIndex;
    }

    public void setDocumentIndex(String documentIndex) {
        this.documentIndex = documentIndex;
    }

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPredictedOnDate() {
        return predictedOnDate;
    }

    public void setPredictedOnDate(String predictedOnDate) {
        this.predictedOnDate = predictedOnDate;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "humidity=" + humidity +
                ", soilMoisture=" + soilMoisture +
                ", temperature=" + temperature +
                ", time='" + time + '\'' +
                ", documentIndex='" + documentIndex + '\'' +
                ", crop_name='" + crop_name + '\'' +
                ", price=" + price +
                ", date='" + date + '\'' +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                ", predictedOnDate='" + predictedOnDate + '\'' +
                '}';
    }
}

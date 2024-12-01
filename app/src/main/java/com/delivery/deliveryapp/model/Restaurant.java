package com.delivery.deliveryapp.model;

public class Restaurant {
    Long id;
    private String icon;
    String name;
    Double longitude;
    Double latitude;
    String address;
    String bgIMG;
    String ratings;
    String deliversIn;
    String speciality;
    String closeTiming;

    public Restaurant() {}

    public Restaurant(Long id, String icon, String name, Double longitude, Double latitude, String address, String bgIMG, String ratings, String deliversIn, String speciality, String closeTiming) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.bgIMG = bgIMG;
        this.ratings = ratings;
        this.deliversIn = deliversIn;
        this.speciality = speciality;
        this.closeTiming = closeTiming;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBgIMG() {
        return bgIMG;
    }

    public void setBgIMG(String bgIMG) {
        this.bgIMG = bgIMG;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getDeliversIn() {
        return deliversIn;
    }

    public void setDeliversIn(String deliversIn) {
        this.deliversIn = deliversIn;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCloseTiming() {
        return closeTiming;
    }

    public void setCloseTiming(String closeTiming) {
        this.closeTiming = closeTiming;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "bgIMG='" + bgIMG + '\'' +
                '}';
    }
}

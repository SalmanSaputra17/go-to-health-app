package com.example.hp.justhealth.Model;

public class APIResponse {

    private boolean error;
    private String unique_id;
    private User user;
    private String error_msg;
    private double BB_ideal;
    private String kategori;
    private int day_calori;
    private int food_calori;
    private String dn_normal;
    private double dn_max;
    private String dn_latihan;

    public APIResponse() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBB_ideal() {
        return BB_ideal;
    }

    public void setBB_ideal(double BB_ideal) {
        this.BB_ideal = BB_ideal;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getDay_calori() {
        return day_calori;
    }

    public void setDay_calori(int day_calori) {
        this.day_calori = day_calori;
    }

    public int getFood_calori() {
        return food_calori;
    }

    public void setFood_calori(int food_calori) {
        this.food_calori = food_calori;
    }

    public String getDn_normal() {
        return dn_normal;
    }

    public void setDn_normal(String dn_normal) {
        this.dn_normal = dn_normal;
    }

    public double getDn_max() {
        return dn_max;
    }

    public void setDn_max(double dn_max) {
        this.dn_max = dn_max;
    }

    public String getDn_latihan() {
        return dn_latihan;
    }

    public void setDn_latihan(String dn_latihan) {
        this.dn_latihan = dn_latihan;
    }
}

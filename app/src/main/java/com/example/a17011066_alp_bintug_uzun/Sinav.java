package com.example.a17011066_alp_bintug_uzun;

import java.util.ArrayList;

public class Sinav {

    private int sinavID;
    private int zorlukDerecesi;
    private int sinavSuresi;
    private String sinavAdi;
    private String kullaniciEPosta;

    //region Constructer & Getter-Setter
    public Sinav(String kullaniciEPosta, int sinavID, int zorlukDerecesi, int sinavSuresi, String sinavAdi) {
        this.sinavID = sinavID;
        this.zorlukDerecesi = zorlukDerecesi;
        this.sinavSuresi = sinavSuresi;
        this.sinavAdi = sinavAdi;
        this.kullaniciEPosta = kullaniciEPosta;
    }
    public Sinav(String kullaniciEPosta,  int zorlukDerecesi, int sinavSuresi, String sinavAdi) {
        this.zorlukDerecesi = zorlukDerecesi;
        this.sinavSuresi = sinavSuresi;
        this.sinavAdi = sinavAdi;
        this.kullaniciEPosta = kullaniciEPosta;
    }
    public String getKullaniciEPosta() {
        return kullaniciEPosta;
    }

    public void setKullaniciEPosta(String kullaniciEPosta) {
        this.kullaniciEPosta = kullaniciEPosta;
    }

    public String getSinavAdi() {
        return sinavAdi;
    }

    public void setSinavAdi(String sinavAdi) {
        this.sinavAdi = sinavAdi;
    }
    public int getSinavID() {
        return sinavID;
    }

    public void setSinavID(int sinavID) {
        this.sinavID = sinavID;
    }
    public int getZorlukDerecesi() {
        return zorlukDerecesi;
    }

    public void setZorlukDerecesi(int zorlukDerecesi) {
        this.zorlukDerecesi = zorlukDerecesi;
    }

    public int getSinavSuresi() {
        return sinavSuresi;
    }

    public void setSinavSuresi(int sinavSuresi) {
        this.sinavSuresi = sinavSuresi;
    }
    //endregion
    @Override
    public String toString(){
            return  "{\nSinav Adi: \""+sinavAdi+"\"\nSinav ID: \""+String.valueOf(sinavID)+"\"\nSinav Suresi: \""+String.valueOf(sinavSuresi)+"\"\nZorluk Derecesi: \""+String.valueOf(zorlukDerecesi)+"\"\n}";
    }
}

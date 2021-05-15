package com.example.a17011066_alp_bintug_uzun;

import java.util.ArrayList;

public class Soru {



    private int soruID;
    private String kullaniciEposta;
    private String soruMetni;
    private String medyaYolu;
    private int zorluk;
    private String[] siklar;
    private int dogruCevap;
    private int soruTipi; //0-> Metinli, 1-> Resimli, 2-> Sesli, 3-> Videolu

    //region Constructer & Getter-Setter
    public Soru(String kullaniciEposta, String soruMetni, int zorluk, String[] siklar, int dogruCevap) {
        this.kullaniciEposta = kullaniciEposta;
        this.soruMetni = soruMetni;
        this.zorluk = zorluk;
        this.siklar = siklar;
        this.dogruCevap = dogruCevap;
        this.soruTipi = 0;
    }

    public Soru(String kullaniciEposta, String soruMetni, String medyaYolu ,int zorluk, String[] siklar, int dogruCevap, int soruTipi) {
        this.kullaniciEposta = kullaniciEposta;
        this.soruMetni = soruMetni;
        this.medyaYolu = medyaYolu;
        this.zorluk = zorluk;
        this.siklar = siklar;
        this.dogruCevap = dogruCevap;
        this.soruTipi = soruTipi;
    }
    public Soru(String kullaniciEposta, String soruMetni, String medyaYolu ,int zorluk, String[] siklar, int dogruCevap, int soruTipi,int soruID) {
        this.kullaniciEposta = kullaniciEposta;
        this.soruMetni = soruMetni;
        this.medyaYolu = medyaYolu;
        this.zorluk = zorluk;
        this.siklar = siklar;
        this.dogruCevap = dogruCevap;
        this.soruTipi = soruTipi;
        this.soruID = soruID;
    }

    public String getSoruMetni() {
        return soruMetni;
    }

    public void setSoruMetni(String soruMetni) {
        this.soruMetni = soruMetni;
    }

    public String getMedyaYolu() {
        return medyaYolu;
    }

    public void setMedyaYolu(String medyaYolu, int medyaTipi) {
        this.medyaYolu = medyaYolu;
        this.soruTipi = medyaTipi;
    }

    public int getZorluk() {
        return zorluk;
    }

    public void setZorluk(int zorluk) {
        this.zorluk = zorluk;
    }

    public String[] getSiklar() {
        return siklar;
    }

    public void setSiklar(String[] siklar) {
        this.siklar = siklar;
    }

    public int getDogruCevap() {
        return dogruCevap;
    }

    public void setDogruCevap(int dogruCevap) {
        this.dogruCevap = dogruCevap;
    }


    public int getSoruTipi() {
        return soruTipi;
    }

    public void setSoruTipi(int soruTipi) {
        this.soruTipi = soruTipi;
    }
    public String getKullaniciEposta() {
        return kullaniciEposta;
    }

    public void setKullaniciEposta(String kullaniciEposta) {
        this.kullaniciEposta = kullaniciEposta;
    }
    public int getSoruID(){
        return this.soruID;
    }

    public void setSoruID(int soruID) {
        this.soruID = soruID;
    }

    @Override
    public String toString(){
        return "{\""+kullaniciEposta+"\",\""+soruMetni+"\",\""+medyaYolu+"\",\""+String.valueOf(zorluk)+"\",\""+String.join("¨",siklar)+"\",\""+String.valueOf(dogruCevap)+"\",\""+String.valueOf(soruTipi)+"\"}";
    }
//endregion
}

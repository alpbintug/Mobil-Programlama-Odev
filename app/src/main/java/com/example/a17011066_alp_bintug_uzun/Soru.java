package com.example.a17011066_alp_bintug_uzun;

import java.util.ArrayList;

public class Soru {
    private String soruMetni;
    private String medyaYolu;
    private int zorluk;
    private ArrayList<String> siklar;
    private int dogruCevap;

    //region Constructer & Getter-Setter
    public String getSoruMetni() {
        return soruMetni;
    }

    public void setSoruMetni(String soruMetni) {
        this.soruMetni = soruMetni;
    }

    public String getMedyaYolu() {
        return medyaYolu;
    }

    public void setMedyaYolu(String medyaYolu) {
        this.medyaYolu = medyaYolu;
    }

    public int getZorluk() {
        return zorluk;
    }

    public void setZorluk(int zorluk) {
        this.zorluk = zorluk;
    }

    public ArrayList<String> getSiklar() {
        return siklar;
    }

    public void setSiklar(ArrayList<String> siklar) {
        this.siklar = siklar;
    }

    public int getDogruCevap() {
        return dogruCevap;
    }

    public void setDogruCevap(int dogruCevap) {
        this.dogruCevap = dogruCevap;
    }

    public Soru(String soruMetni, int zorluk, ArrayList<String> siklar, int dogruCevap) {
        this.soruMetni = soruMetni;
        this.zorluk = zorluk;
        this.siklar = siklar;
        this.dogruCevap = dogruCevap;
    }
//endregion
}
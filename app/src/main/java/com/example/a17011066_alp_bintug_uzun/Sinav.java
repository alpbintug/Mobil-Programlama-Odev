package com.example.a17011066_alp_bintug_uzun;

import java.util.ArrayList;

public class Sinav {
    private ArrayList<Soru> sorular;
    private int zorlukDerecesi;
    private int sinavSuresi;

    //region Constructer & Getter-Setter
    public Sinav(ArrayList<Soru> sorular, int zorlukDerecesi, int sinavSuresi) {
        this.sorular = sorular;
        this.zorlukDerecesi = zorlukDerecesi;
        this.sinavSuresi = sinavSuresi;
    }

    public ArrayList<Soru> getSorular() {
        return sorular;
    }

    public void setSorular(ArrayList<Soru> sorular) {
        this.sorular = sorular;
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
            return  "";
    }
}

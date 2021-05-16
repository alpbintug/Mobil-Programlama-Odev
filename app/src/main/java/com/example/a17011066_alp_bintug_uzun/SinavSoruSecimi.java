package com.example.a17011066_alp_bintug_uzun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class SinavSoruSecimi extends AppCompatActivity {

    private String kullaniciEPosta;
    private String sinavAdi;
    private int SinavID;
    private int sinavZorlugu;
    Context context;
    LinearLayout layout;
    View viewToAdd;
    int[] rgb = {255,255,0};
    int alpha = 170;
    int lastTo255 = 0;
    int colorChange = 17;
    private DBHelper db;
    Uri imageURI;
    ImageView soruGorseli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinav_soru_secimi);
        db = new DBHelper(this);
        Intent intent = getIntent();
        kullaniciEPosta = intent.getStringExtra(db.KULLANICI_EPOSTA);
        SinavID = intent.getIntExtra(db.SINAV_ID,-1);
        sinavZorlugu = intent.getIntExtra(db.SINAV_ZORLUK_DERECESI,-1);
        sinavAdi = intent.getStringExtra(db.SINAV_ADI);
        getSupportActionBar().setTitle(sinavAdi);
        context = getApplicationContext();
        layout = (LinearLayout)findViewById(R.id.sinav_soruListesi);

        ArrayList<Soru> sinavSorulari = db.sinavSorulariniGetir(SinavID);
        ArrayList<Integer> soruIDleri = new ArrayList<Integer>();
        for(Soru soru : sinavSorulari){
            soruIDleri.add(soru.getSoruID());
        }
        ArrayList<Soru> tumSorular = db.sorulariGetir(kullaniciEPosta);
        tumSorular.removeIf(e -> e.getZorluk()!=sinavZorlugu || soruIDleri.contains(e.getSoruID()));
        sorulariYerlestir(sinavSorulari,true);
        sorulariYerlestir(tumSorular,false);
    }

    public void sorulariYerlestir(ArrayList<Soru> sorular, boolean sinavSorusuMu){
        for (Soru soru: sorular ) {

            viewToAdd = LayoutInflater.from(this).inflate(R.layout.sinav_sorulari_liste_ogesi,null);

            //region ARKA PLAN RENKLERINI AYARLAMA
            int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
            if(rgb[(lastTo255+1)%3]!=255){
                rgb[(lastTo255+1)%3]+=colorChange;
            }
            else if(rgb[lastTo255]>0){
                rgb[lastTo255]-=colorChange;
            }
            else{
                lastTo255=(lastTo255+1)%3;
            }
            viewToAdd.findViewById(R.id.sinav_SoruCardView).setBackgroundColor(bgc);
            //endregion

            //region SORU PANOSUNU OLUSTURMA
            ((TextView)viewToAdd.findViewById(R.id.sinav_txt_soruID)).setText(String.valueOf(soru.getSoruID()));
            ((EditText)viewToAdd.findViewById(R.id.sinav_txtSoruMetni)).setText(soru.getSoruMetni());
            ((EditText)viewToAdd.findViewById(R.id.sinav_soru_txtSik0)).setText(soru.getSiklar()[0]);
            ((EditText)viewToAdd.findViewById(R.id.sinav_soru_txtSik1)).setText(soru.getSiklar()[1]);
            if(!sinavSorusuMu) {
                ((Button)viewToAdd.findViewById(R.id.sinav_soruEkle)).setBackgroundColor(Color.rgb(0,100,0));
                ((Button)viewToAdd.findViewById(R.id.sinav_soruEkle)).setText("+");
            }
            else{
                ((Button)viewToAdd.findViewById(R.id.sinav_soruEkle)).setBackgroundColor(Color.rgb(100,0,0));
                ((Button)viewToAdd.findViewById(R.id.sinav_soruEkle)).setText("X");
            }
            //endregion

            //region DOGRU SIKKI SECME
            if(soru.getDogruCevap()==0)
                ((RadioButton)viewToAdd.findViewById(R.id.sinav_radbutton_sik0)).setChecked(true);
            else if(soru.getDogruCevap()==1)
                ((RadioButton)viewToAdd.findViewById(R.id.sinav_radbutton_sik1)).setChecked(true);
            else if(soru.getDogruCevap()==2)
                ((RadioButton)viewToAdd.findViewById(R.id.sinav_radbutton_sik2)).setChecked(true);
            else if(soru.getDogruCevap()==3)
                ((RadioButton)viewToAdd.findViewById(R.id.sinav_radbutton_sik3)).setChecked(true);
            else
                ((RadioButton)viewToAdd.findViewById(R.id.sinav_radbutton_sik4)).setChecked(true);
            //endregion

            //region TERCIHEN EKLENEN SIKLARI OLUSTURMA
            if(soru.getZorluk()>2)
            {
                ((EditText)viewToAdd.findViewById(R.id.sinav_soru_txtSik2)).setText(soru.getSiklar()[2]);
                if(soru.getZorluk()>3){
                    ((EditText)viewToAdd.findViewById(R.id.sinav_soru_txtSik3)).setText(soru.getSiklar()[3]);
                    if(soru.getZorluk()>4){
                        ((EditText)viewToAdd.findViewById(R.id.sinav_soru_txtSik4)).setText(soru.getSiklar()[4]);
                    }
                    else{
                        ((EditText)viewToAdd.findViewById(R.id.sinav_soru_txtSik4)).setVisibility(View.INVISIBLE);
                        ((RadioButton)viewToAdd.findViewById(R.id.sinav_radbutton_sik4)).setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    ((EditText)viewToAdd.findViewById(R.id.sinav_soru_txtSik4)).setVisibility(View.INVISIBLE);
                    ((RadioButton)viewToAdd.findViewById(R.id.sinav_radbutton_sik4)).setVisibility(View.INVISIBLE);
                    ((EditText)viewToAdd.findViewById(R.id.soru_txtSik3)).setVisibility(View.INVISIBLE);
                    ((RadioButton)viewToAdd.findViewById(R.id.sinav_radbutton_sik4)).setVisibility(View.INVISIBLE);

                }
            }
            else{
                ((EditText)viewToAdd.findViewById(R.id.sinav_soru_txtSik4)).setVisibility(View.INVISIBLE);
                ((RadioButton)viewToAdd.findViewById(R.id.sinav_radbutton_sik4)).setVisibility(View.INVISIBLE);
                ((EditText)viewToAdd.findViewById(R.id.sinav_soru_txtSik3)).setVisibility(View.INVISIBLE);
                ((RadioButton)viewToAdd.findViewById(R.id.sinav_radbutton_sik3)).setVisibility(View.INVISIBLE);
                ((EditText)viewToAdd.findViewById(R.id.sinav_soru_txtSik2)).setVisibility(View.INVISIBLE);
                ((RadioButton)viewToAdd.findViewById(R.id.sinav_radbutton_sik2)).setVisibility(View.INVISIBLE);
            }
            //endregion

            //region READONLY YAPMA
            viewToAdd.findViewById(R.id.sinav_txtSoruMetni).setEnabled(false);
            viewToAdd.findViewById(R.id.sinav_soru_txtSik0).setEnabled(false);
            viewToAdd.findViewById(R.id.sinav_soru_txtSik1).setEnabled(false);
            viewToAdd.findViewById(R.id.sinav_soru_txtSik2).setEnabled(false);
            viewToAdd.findViewById(R.id.sinav_soru_txtSik3).setEnabled(false);
            viewToAdd.findViewById(R.id.sinav_soru_txtSik4).setEnabled(false);
            viewToAdd.findViewById(R.id.sinav_radbuttongr_siklar).setEnabled(false);
            //endregion

            //region GÖRSEL EKLEME
            soruGorseli = (ImageView) viewToAdd.findViewById(R.id.sinav_imageViewSoruResmi);
            if(soru.getMedyaYolu() != null && soru.getMedyaYolu().length() > 0) {
                try {
                    soruGorseli.setImageDrawable(Drawable.createFromPath(soru.getMedyaYolu()));
                    soruGorseli.setTag(soru.getMedyaYolu());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                soruGorseli.setImageBitmap(null);
            }
            soruGorseli.setEnabled(false);
            //endregion

            RadioGroup radioGroup_Siklar = (RadioGroup)viewToAdd.findViewById(R.id.sinav_radbuttongr_siklar);
            for(int i = 0;i<radioGroup_Siklar.getChildCount();i++){
                ((RadioButton)radioGroup_Siklar.getChildAt(i)).setEnabled(false);
            }

            layout.addView(viewToAdd);
        }
    }
    public void sinavSoruSec(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        Button buttonSec = (Button)parentView.findViewById(R.id.sinav_soruEkle);
        TextView soruID = (TextView)parentView.findViewById(R.id.sinav_txt_soruID);
        if(buttonSec.getText().toString().equals("X")){
            if(db.sinavSoruSil(SinavID,Integer.valueOf(soruID.getText().toString()))){
                buttonSec.setText("+");
                buttonSec.setBackgroundColor(Color.rgb(0,100,0));
            }
        }
        else{
            if(db.sinavSoruEkle(SinavID,Integer.valueOf(soruID.getText().toString()))){
                buttonSec.setText("X");
                buttonSec.setBackgroundColor(Color.rgb(100,0,0));
            }
        }
    }

}
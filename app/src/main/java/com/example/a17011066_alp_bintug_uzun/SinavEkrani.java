package com.example.a17011066_alp_bintug_uzun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SinavEkrani extends AppCompatActivity {

    Context context;
    CardView cardview;
    RelativeLayout.LayoutParams layoutparams;
    TextView textview;
    Button button;
    View view;
    String kullaniciEPosta;
    private DBHelper db;

    int[] rgb = {0,255,150};
    int lastTo255 = 0;
    int alpha = 255;
    int colorChange = 17;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //region DEGISKEN AYARLARI
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sinav_ekrani);
        db = new DBHelper(this);
        Intent intent = getIntent();
        RatingBar ratingBar;
        Button sinavDuzenle;
        Button sorulariDuzenle;
        EditText sinavAdi;
        EditText sinavSuresi;
        TextView sinavID;
        TextView soruSayisi;
        ImageButton sinavPaylas;
        kullaniciEPosta = intent.getStringExtra(db.KULLANICI_EPOSTA);


        context = getApplicationContext();
        layout = (LinearLayout)findViewById(R.id.sinav_sinavListesi);
        ArrayList<Sinav> sinavlar = db.sinavlariGetir(kullaniciEPosta);
        SharedPreferences sh = getSharedPreferences(kullaniciEPosta, MODE_PRIVATE);
        Map<String,?> sinavIDleri = sh.getAll();


        //endregion


        //region KULLANICI SINAVLARINI GETIRME

        //endregion



        for (Sinav sinav: sinavlar) {
            View viewToAdd = LayoutInflater.from(this).inflate(R.layout.sinavlar_liste_ogesi,null);
            sinavAdi = (EditText)viewToAdd.findViewById(R.id.editTextSinavAd??);
            sinavSuresi = (EditText)viewToAdd.findViewById(R.id.editTextSinavSuresi);
            sinavID = (TextView)viewToAdd.findViewById(R.id.txtSinavID);
            soruSayisi = (TextView)viewToAdd.findViewById(R.id.txtSoruSayisi);
            sorulariDuzenle =  (Button)viewToAdd.findViewById(R.id.button_sinavSoruEdit);
            ratingBar = (RatingBar)viewToAdd.findViewById(R.id.sinav_sinavZorlugu);
            ratingBar.setRating(2.0f);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                @Override public void onRatingChanged(RatingBar ratingBar, float rating,
                                                      boolean fromUser) {
                    if(rating<2.0f)
                        ratingBar.setRating(2.0f);
                }
            });

            sinavAdi.setText(sinav.getSinavAdi());
            ratingBar.setRating((float)sinav.getZorlukDerecesi());
            sinavSuresi.setText(String.valueOf(sinav.getSinavSuresi()));
            sinavID.setText(String.valueOf(sinav.getSinavID()));
            soruSayisi.setText(String.valueOf(db.sinavSoruSayisi(sinav.getSinavID())));


            sinavAdi.setEnabled(false);
            ratingBar.setEnabled(false);
            sinavSuresi.setEnabled(false);
            sorulariDuzenle.setEnabled(true);
            sorulariDuzenle.setVisibility(View.VISIBLE);

            calculateColors();

            int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);

            viewToAdd.findViewById(R.id.sinav_cardView).setBackgroundColor(bgc);
            ((Button)viewToAdd.findViewById(R.id.button_sinavEdit)).setText("D??zenle");
            layout.addView(viewToAdd);
        }

        //region BO?? SINAV KARTI EKLEME
       bosKartEkle(this);
        //endregion
    }
    public void calculateColors(){

        if(rgb[(lastTo255+1)%3]<255){
            rgb[(lastTo255+1)%3]+=colorChange;
            if(rgb[(lastTo255+1)%3]>255)
                rgb[(lastTo255+1)%3]=255;
        }
        else if(rgb[lastTo255]>0){
            rgb[lastTo255]-=colorChange;
            if(rgb[lastTo255]<0)
                rgb[lastTo255]=0;
        }
        else{
            lastTo255=(lastTo255+1)%3;
        }
    }
    private void bosKartEkle(Context context){
        View viewToAdd = LayoutInflater.from(context).inflate(R.layout.sinavlar_liste_ogesi,null);

        EditText sinavAdi = (EditText)viewToAdd.findViewById(R.id.editTextSinavAd??);
        EditText sinavSuresi = (EditText)viewToAdd.findViewById(R.id.editTextSinavSuresi);
        RatingBar ratingBar = (RatingBar)viewToAdd.findViewById(R.id.sinav_sinavZorlugu);
        ratingBar.setRating(2.0f);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override public void onRatingChanged(RatingBar ratingBar, float rating,
                                                  boolean fromUser) {
                if(rating<2.0f)
                    ratingBar.setRating(2.0f);
            }
        });
        calculateColors();

        int bgc = Color.argb(alpha/2,rgb[0],rgb[1],rgb[2]);

        sinavAdi.setEnabled(true);
        ratingBar.setEnabled(true);
        sinavSuresi.setEnabled(true);
        viewToAdd.findViewById(R.id.sinav_cardView).setBackgroundColor(bgc);
        ((Button)viewToAdd.findViewById(R.id.button_sinavEdit)).setText("Kaydet");
        layout.addView(viewToAdd);
    }

    public void sinavDuzenle(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        Button duzenle = (Button)view;
        Button sinavSoruDuzenle = (Button)parentView.findViewById(R.id.button_sinavSoruEdit);
        EditText sinavAdi = (EditText)parentView.findViewById(R.id.editTextSinavAd??);
        EditText sinavSuresi = (EditText)parentView.findViewById(R.id.editTextSinavSuresi);
        TextView sinavID = (TextView)parentView.findViewById(R.id.txtSinavID);
        RatingBar ratingBar = (RatingBar)parentView.findViewById(R.id.sinav_sinavZorlugu);

        if(duzenle.getText().toString().equals("Kaydet"))
        {
            String s_sinavAdi = sinavAdi.getText().toString();
            int sinavZorlugu = (int)ratingBar.getRating();
            int i_sinavSuresi = Integer.valueOf(sinavSuresi.getText().toString());
            if(s_sinavAdi.length()>0&&i_sinavSuresi>0) {
                Sinav sinav = new Sinav(kullaniciEPosta,sinavZorlugu,i_sinavSuresi,s_sinavAdi);
                boolean result;
                if(sinavID.getText().length()>0){
                    sinav.setSinavID(Integer.valueOf(sinavID.getText().toString()));
                    result = db.sinavDuzenle(sinav);
                }
                else{
                    result = db.sinavEkle(sinav);
                }
                if(result) {
                    sinavAdi.setEnabled(false);
                    ratingBar.setEnabled(false);
                    sinavSuresi.setEnabled(false);
                    sinavSoruDuzenle.setEnabled(true);
                    sinavSoruDuzenle.setVisibility(View.VISIBLE);
                    int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
                    parentView.setBackgroundColor(bgc);
                    duzenle.setText("D??zenle");
                    SharedPreferences sharedPreferences = getSharedPreferences(kullaniciEPosta, MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString(String.valueOf(db.sonSinavID(sinav.getKullaniciEPosta())),sinav.toString());
                    sinavID.setText(String.valueOf(db.sonSinavID(sinav.getKullaniciEPosta())));
                    bosKartEkle(this);
                }
            }
            else{
                Toast.makeText(this,"L??tfen s??nav ad??n?? ve s??resini ge??erli ??ekilde giriniz.",Toast.LENGTH_LONG);
            }
        }
        else
        {
            sinavAdi.setEnabled(true);
            ratingBar.setEnabled(true);
            sinavSuresi.setEnabled(true);
            duzenle.setText("Kaydet");
        }
    }
    public void sinavSil(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        String sinavID = ((TextView)parentView.findViewById(R.id.txtSinavID)).getText().toString();
        if(sinavID!=null & sinavID.length()>0){
            if(db.sinavSil(Integer.valueOf(sinavID)))
                parentView.removeAllViews();
            else
                Toast.makeText(this,"S??nav silinemedi.",Toast.LENGTH_SHORT);
        }
    }
    public void sinavSoruDuzenle(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        Intent menuAcma = new Intent(getApplicationContext(),SinavSoruSecimi.class);
        TextView sinavID = (TextView)parentView.findViewById(R.id.txtSinavID);
        EditText sinavAdi = (EditText)parentView.findViewById(R.id.editTextSinavAd??);
        RatingBar sinavZorlugu = (RatingBar)parentView.findViewById(R.id.sinav_sinavZorlugu);
        menuAcma.putExtra(db.KULLANICI_EPOSTA,kullaniciEPosta);
        menuAcma.putExtra(db.SINAV_ID,Integer.valueOf(sinavID.getText().toString()));
        menuAcma.putExtra(db.SINAV_ZORLUK_DERECESI,(int)sinavZorlugu.getRating());
        menuAcma.putExtra(db.SINAV_ADI,sinavAdi.getText().toString());
        startActivity(menuAcma);
    }

    public void sinavPaylas(View view){
        String text = "<SINAV B??LG??LER??: \n****SINAV AYARLARI****\n";
        ViewGroup parentView = (ViewGroup)view.getParent();
        TextView sinavID = (TextView)parentView.findViewById(R.id.txtSinavID);
        int SinavID = Integer.valueOf(sinavID.getText().toString());
        Sinav sinav = db.sinavGetir(SinavID);
        text+=sinav.toString()+"\n****SORULAR****\n";
        ArrayList<Soru> sinavSorulari = db.sinavSorulariniGetir(SinavID);
        for (int i = 0; i<sinavSorulari.size();i++){
            text+="\n____SORU "+String.valueOf(i)+"____\n"+sinavSorulari.get(i).toString()+"\n";
        }
        text+=">";
        String fileName = "SINAV"+sinavID.getText().toString()+".txt";

        //Log.d("Sinav:",text);
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(fileName,MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d("Sinav:",text);
        Intent fileShare = new Intent(Intent.ACTION_SEND);
        fileShare.setType("text/plain");

        fileShare.putExtra(Intent.EXTRA_SUBJECT, sinav.getSinavAdi() + " SINAV B??LG??LER??");
        fileShare.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(fileShare);

    }
}
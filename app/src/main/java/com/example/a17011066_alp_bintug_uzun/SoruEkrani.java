package com.example.a17011066_alp_bintug_uzun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SoruEkrani extends AppCompatActivity {

    Context context;
    LinearLayout layout;
    View viewToAdd;
    String kullaniciEposta;
    int[] rgb = {255,255,0};
    int alpha = 25;
    int lastTo255 = 0;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_soru_ekrani);
        layout = (LinearLayout)findViewById(R.id.soru_soruListesi);
        Intent intent = getIntent();
        context = getApplicationContext();
        db = new DBHelper(this);
        kullaniciEposta = intent.getStringExtra(db.KULLANICI_EPOSTA);
        ArrayList<Soru> sorular = db.sorulariGetir(kullaniciEposta);
        for (Soru soru: sorular ) {
            Log.d("herSeyiAyarladim","xd");

            viewToAdd = LayoutInflater.from(this).inflate(R.layout.sorular_liste_ogesi_metinli,null);

            int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
            if(rgb[(lastTo255+1)%3]!=255){
                rgb[(lastTo255+1)%3]+=15;
            }
            else if(rgb[lastTo255]>0){
                rgb[lastTo255]-=15;
            }
            else{
                lastTo255=(lastTo255+1)%3;
            }
            viewToAdd.findViewById(R.id.soru_cardView).setBackgroundColor(bgc);
            ((Button)viewToAdd.findViewById(R.id.button_soruEdit)).setText("Düzenle");

            ((TextView)viewToAdd.findViewById(R.id.txt_soruID)).setText(String.valueOf(soru.getSoruID()));
            ((EditText)viewToAdd.findViewById(R.id.soru_txtSoruMetni)).setText(soru.getSoruMetni());
            ((EditText)viewToAdd.findViewById(R.id.soru_txtSik0)).setText(soru.getSiklar()[0]);
            ((EditText)viewToAdd.findViewById(R.id.soru_txtSik1)).setText(soru.getSiklar()[1]);
            Log.d("herSeyiAyarladim","0");
            if(soru.getZorluk()>2)
            {
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik2)).setText(soru.getSiklar()[2]);
                if(soru.getZorluk()>3){
                    ((EditText)viewToAdd.findViewById(R.id.soru_txtSik3)).setText(soru.getSiklar()[3]);
                    if(soru.getZorluk()>4){
                        ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setText(soru.getSiklar()[4]);
                    }
                    else{
                        ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setVisibility(View.INVISIBLE);
                        ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik4)).setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setVisibility(View.INVISIBLE);
                    ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik4)).setVisibility(View.INVISIBLE);
                    ((EditText)viewToAdd.findViewById(R.id.soru_txtSik3)).setVisibility(View.INVISIBLE);
                    ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik3)).setVisibility(View.INVISIBLE);

                }
            }
            else{
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setVisibility(View.INVISIBLE);
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik4)).setVisibility(View.INVISIBLE);
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik3)).setVisibility(View.INVISIBLE);
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik3)).setVisibility(View.INVISIBLE);
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik2)).setVisibility(View.INVISIBLE);
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik2)).setVisibility(View.INVISIBLE);
            }
            viewToAdd.findViewById(R.id.soru_txtSoruMetni).setEnabled(false);
            viewToAdd.findViewById(R.id.soru_txtSik0).setEnabled(false);
            viewToAdd.findViewById(R.id.soru_txtSik1).setEnabled(false);
            viewToAdd.findViewById(R.id.soru_txtSik2).setEnabled(false);
            viewToAdd.findViewById(R.id.soru_txtSik3).setEnabled(false);
            viewToAdd.findViewById(R.id.soru_txtSik4).setEnabled(false);
            viewToAdd.findViewById(R.id.radbuttongr_siklar).setEnabled(false);
            layout.addView(viewToAdd);
        }

        int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
        if(rgb[(lastTo255+1)%3]!=255){
            rgb[(lastTo255+1)%3]+=15;
        }
        else if(rgb[lastTo255]>0){
            rgb[lastTo255]-=15;
        }
        else{
            lastTo255=(lastTo255+1)%3;
        }

        viewToAdd = LayoutInflater.from(this).inflate(R.layout.sorular_liste_ogesi_metinli,null);
        viewToAdd.findViewById(R.id.soru_cardView).setBackgroundColor(bgc);
        ((Button)viewToAdd.findViewById(R.id.button_soruEdit)).setText("Kaydet");
        layout.addView(viewToAdd);
    }

    public void buttonSoruDuzenle(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        Button duzenlemeButonu = (Button)parentView.findViewById(R.id.button_soruEdit);
        EditText soruMetni = (EditText)parentView.findViewById(R.id.soru_txtSoruMetni);
        EditText sik0 = (EditText)parentView.findViewById(R.id.soru_txtSik0);
        EditText sik1 = (EditText)parentView.findViewById(R.id.soru_txtSik1);
        EditText sik2 = (EditText)parentView.findViewById(R.id.soru_txtSik2);
        EditText sik3 = (EditText)parentView.findViewById(R.id.soru_txtSik3);
        EditText sik4 = (EditText)parentView.findViewById(R.id.soru_txtSik4);
        RadioGroup radioGroup_Siklar = (RadioGroup)parentView.findViewById(R.id.radbuttongr_siklar);
        TextView soruID = (TextView)parentView.findViewById(R.id.txt_soruID);

        ArrayList<String> siklar = new ArrayList<String>();
        if(sik0.getText().toString().length()!=0)
            siklar.add(sik0.getText().toString());
        if(sik1.getText().toString().length()!=0)
            siklar.add(sik1.getText().toString());
        if(sik2.getText().toString().length()!=0)
            siklar.add(sik2.getText().toString());
        if(sik3.getText().toString().length()!=0)
            siklar.add(sik3.getText().toString());
        if(sik4.getText().toString().length()!=0)
            siklar.add(sik4.getText().toString());
        if(duzenlemeButonu.getText().toString().equals("Kaydet")){
            if(soruMetni.getText().length()==0 || siklar.size() <2 || radioGroup_Siklar.getCheckedRadioButtonId()==-1){
                Toast.makeText(this,"Lütfen soru metnini giriniz, en az iki adet şıkkı doldurunuz ve doğru şıkkı seçiniz.",Toast.LENGTH_LONG).show();
                return;
            }
            Log.d("sikLen",String.valueOf(siklar.size()));
            Soru soru = new Soru(kullaniciEposta,soruMetni.getText().toString(),siklar.size(),siklar.toArray(new String[0]),Integer.valueOf(((RadioButton)parentView.findViewById(radioGroup_Siklar.getCheckedRadioButtonId())).getText().toString()));

            if(soruID.getText().toString().length()==0){
                if(db.soruEkle(soru)){

                    int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
                    if(rgb[(lastTo255+1)%3]!=255){
                        rgb[(lastTo255+1)%3]+=15;
                    }
                    else if(rgb[lastTo255]>0){
                        rgb[lastTo255]-=15;
                    }
                    else{
                        lastTo255=(lastTo255+1)%3;
                    }
                    viewToAdd = LayoutInflater.from(this).inflate(R.layout.sorular_liste_ogesi_metinli,null);
                    viewToAdd.findViewById(R.id.soru_cardView).setBackgroundColor(bgc);
                    ((Button)viewToAdd.findViewById(R.id.button_soruEdit)).setText("Kaydet");
                    layout.addView(viewToAdd);
                }
            }
            else {
                Log.d("Soru duzenle oldugunu anladım","");
                soru.setSoruID(Integer.valueOf(soruID.getText().toString()));
                Log.d("Sorunun ID'si",soruID.getText().toString());
                db.soruDuzenle(soru);
            }

            soruMetni.setEnabled(false);
            sik0.setEnabled(false);
            sik1.setEnabled(false);
            sik2.setEnabled(false);
            sik3.setEnabled(false);
            sik4.setEnabled(false);
            radioGroup_Siklar.setEnabled(false);
            duzenlemeButonu.setText("Düzenle");
        }
        else{
            Log.d("else girdim","heheheh");
            soruMetni.setEnabled(true);
            sik0.setEnabled(true);
            sik1.setEnabled(true);
            sik2.setEnabled(true);
            sik3.setEnabled(true);
            sik4.setEnabled(true);
            radioGroup_Siklar.setEnabled(true);
            sik2.setVisibility(View.VISIBLE);
            sik3.setVisibility(View.VISIBLE);
            sik4.setVisibility(View.VISIBLE);
            ((RadioButton)parentView.findViewById(R.id.radbutton_sik2)).setVisibility(View.VISIBLE);
            ((RadioButton)parentView.findViewById(R.id.radbutton_sik3)).setVisibility(View.VISIBLE);
            ((RadioButton)parentView.findViewById(R.id.radbutton_sik4)).setVisibility(View.VISIBLE);
            duzenlemeButonu.setText("Kaydet");
        }
    }
}
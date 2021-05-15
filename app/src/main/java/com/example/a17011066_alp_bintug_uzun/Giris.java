package com.example.a17011066_alp_bintug_uzun;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Giris extends AppCompatActivity {

    private int STORAGE_PERMISSION = 1;
    Activity main;
    private int avatarCounter = 0;
    private int loginErrorCounter = 0;
    private DBHelper db;
    private static String epostaRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static String telefonRegex = "^\\+{0,1}[0-9]{10,13}$";
    private static final Pattern epostaDesen = Pattern.compile(epostaRegex);
    private static final Pattern telefonDesen = Pattern.compile(telefonRegex);
    private int hataRengi = Color.argb(50,255,0,0);
    private int normalRenk = Color.argb(255,255,255,255);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        main = Giris.this;
        if(ContextCompat.checkSelfPermission(Giris.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestStoragePermission();
        }
        //region SETTING UP VARIABLES
        db = new DBHelper(this);
        //this.deleteDatabase(db.getDatabaseName());
        Button buttonKayitOl = ((Button)findViewById(R.id.buttonKayitOl));
        ImageButton ibuttonAvatar = ((ImageButton)findViewById(R.id.ibuttonAvatar));
        ibuttonAvatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                avatarCounter = -1;
                ibuttonAvatar(v);
                return true;
            }
        });
        Drawable drawableAvatar = getResources().getDrawable(getResources().getIdentifier("avatar"+String.valueOf(avatarCounter),"drawable",getPackageName()));
        String kayitButtonText = "Kayıt Ol";
        buttonKayitOl.setText(kayitButtonText);
        ibuttonAvatar.setBackground(drawableAvatar);
        //endregion

    }

    private void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(main,Manifest.permission.READ_EXTERNAL_STORAGE)){
                new AlertDialog.Builder(main)
                        .setTitle("İzin gerekli.")
                        .setMessage("Oluşturduğunuz soruları ve medyaları güncelleyip kaydedebilmek için izninize gerek duyuyoruz.")
                        .setPositiveButton("İzin ver", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(main, new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },STORAGE_PERMISSION);
                            }
                        }).setNegativeButton("Reddet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },STORAGE_PERMISSION);
        }
    }
    public void switchVisibility(View view, boolean girisEkraniAcik){
        Button kayitButon = ((Button)findViewById(R.id.buttonKayitOl));
        Button girisButon = ((Button)findViewById(R.id.buttonGirisYap));
        EditText sifre = ((EditText)findViewById(R.id.txtSifre));
        EditText sifreTekrar = ((EditText)findViewById(R.id.txtSifreTekrar));
        EditText ad = ((EditText)findViewById(R.id.txtAd));
        EditText soyad = ((EditText)findViewById(R.id.txtSoyad));
        EditText telefon = ((EditText)findViewById(R.id.txtTelefon));
        EditText eposta = ((EditText)findViewById(R.id.txtPosta));
        ImageButton avatar = ((ImageButton)findViewById(R.id.ibuttonAvatar));
        resetColor(view);
        if(girisEkraniAcik){
            kayitButon.setText("Kayıt Ol");
            girisButon.setText("Giriş Yap");
            sifreTekrar.setVisibility(view.INVISIBLE);
            ad.setVisibility(view.INVISIBLE);
            soyad.setVisibility(view.INVISIBLE);
            telefon.setVisibility(view.INVISIBLE);
            avatar.setVisibility(view.INVISIBLE);
        }
        else {
            kayitButon.setText("Kaydı Tamamla");
            girisButon.setText("Geri Dön");
            sifreTekrar.setVisibility(view.VISIBLE);
            ad.setVisibility(view.VISIBLE);
            soyad.setVisibility(view.VISIBLE);
            telefon.setVisibility(view.VISIBLE);
            avatar.setVisibility(view.VISIBLE);
        }
    }

    public void buttonGirisYap(View view) {
        Button girisButon = ((Button)findViewById(R.id.buttonGirisYap));
        if(girisButon.getText().equals("Giriş Yap")){
            String sifre = ((EditText)findViewById(R.id.txtSifre)).getText().toString();
            String eposta = ((EditText)findViewById(R.id.txtPosta)).getText().toString();
            if(sifre.length()==0||eposta.length()==0)
                Toast.makeText(getApplicationContext(),"Giris basarisiz! Alanlari kontrol edin!",Toast.LENGTH_SHORT).show();
            Kullanici kullanici = db.kullaniciGirisi(eposta,sifre);
            if(kullanici != null) {
                Toast.makeText(getApplicationContext(), "Giris basarili!", Toast.LENGTH_SHORT).show();
                Intent menuAcma = new Intent(getApplicationContext(),MenuEkrani.class);
                menuAcma.putExtra(db.KULLANICI_AD,kullanici.getAd());
                menuAcma.putExtra(db.KULLANICI_SOYAD,kullanici.getSoyad());
                menuAcma.putExtra(db.KULLANICI_TELEFON,kullanici.getTelefon());
                menuAcma.putExtra(db.KULLANICI_EPOSTA,kullanici.getEposta());
                menuAcma.putExtra(db.KULLANICI_SIFRE,kullanici.getSifre());
                menuAcma.putExtra(db.KULLANICI_AVATAR,kullanici.getAvatar());

                startActivity(menuAcma);

            }
            else
                Toast.makeText(getApplicationContext(),"Kullanici adi ya da sifre hatali!",Toast.LENGTH_SHORT).show();

            loginErrorCounter++;
            if(loginErrorCounter==3){
                loginErrorCounter=0;
                switchVisibility(view,false);
                Toast.makeText(getApplicationContext(),"Üç kere hatalı giriş yaptınız. Lütfen kaydolun.",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            switchVisibility(view,true);
            clearText(view);
        }

    }
    public void buttonKayitOl(View view) {
        boolean hataVar = false;
        Button kayitButon = ((Button) findViewById(R.id.buttonKayitOl));
        Button girisButon = ((Button) findViewById(R.id.buttonGirisYap));
        EditText sifre = ((EditText) findViewById(R.id.txtSifre));
        String strSifre = sifre.getText().toString();
        EditText sifreTekrar = ((EditText) findViewById(R.id.txtSifreTekrar));
        String strSifreTekrar = sifreTekrar.getText().toString();
        EditText ad = ((EditText) findViewById(R.id.txtAd));
        String strAd = ad.getText().toString();
        EditText soyad = ((EditText) findViewById(R.id.txtSoyad));
        String strSoyad = soyad.getText().toString();
        EditText telefon = ((EditText) findViewById(R.id.txtTelefon));
        String strTelefon = telefon.getText().toString();
        EditText eposta = ((EditText) findViewById(R.id.txtPosta));
        String strEposta = eposta.getText().toString();
        resetColor(view);
        if (kayitButon.getText().equals("Kayıt Ol")) {

            switchVisibility(view, false);

        }
        else {
            if(strAd.length() == 0){
                ad.setBackgroundColor(hataRengi);
                hataVar = true;
            }
            if(strSoyad.length() == 0){
                soyad.setBackgroundColor(hataRengi);
                hataVar = true;
            }
            if(strEposta.length() == 0 || !epostaDogrulama(strEposta)){
                eposta.setBackgroundColor(hataRengi);
                hataVar = true;
            }
            if(strSifre.length() == 0){
                sifre.setBackgroundColor(hataRengi);
                hataVar = true;
            }
            if(strSifreTekrar.length() == 0 || !strSifre.equals(strSifreTekrar)){
                sifre.setBackgroundColor(hataRengi);
                sifreTekrar.setBackgroundColor(hataRengi);
                hataVar = true;
            }
            if(strTelefon.length()==0||!telefonDogrulama(strTelefon)){
                telefon.setBackgroundColor(hataRengi);
                hataVar = true;
            }
            if(!hataVar) {

                Kullanici kullanici = new Kullanici(strAd, strSoyad, strTelefon, strEposta, strSifre,"avatar"+String.valueOf(avatarCounter%10));
                if(db.kullaniciEkle(kullanici)){
                    Toast.makeText(getApplicationContext(), "Kayit basarili!", Toast.LENGTH_SHORT).show();
                    clearText(view);
                    switchVisibility(view, true);
                }
                else{
                    eposta.setBackgroundColor(hataRengi);
                    Toast.makeText(getApplicationContext(), "Bu eposta daha once kaydedilmis!", Toast.LENGTH_SHORT).show();
                }
            }else {
                sifre.setText("");
                sifreTekrar.setText("");
                Toast.makeText(getApplicationContext(), "Kaydolma başarısız! Belirtilen alanları kontrol ediniz.".toString(), Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void ibuttonAvatar(View view){
        avatarCounter++;
        ImageButton ibuttonAvatar = ((ImageButton)findViewById(R.id.ibuttonAvatar));
        Drawable drawableAvatar = getResources().getDrawable(getResources().getIdentifier("avatar"+String.valueOf(avatarCounter%10),"drawable",getPackageName()));
        ibuttonAvatar.setBackground(drawableAvatar);
    }

    public boolean telefonDogrulama(String telefon){
        return telefonDesen.matcher(telefon).matches();
    }
    public boolean epostaDogrulama(String eposta){
        return epostaDesen.matcher(eposta).matches();
    }
    public void resetColor(View view){

        EditText sifre = ((EditText) findViewById(R.id.txtSifre));
        EditText sifreTekrar = ((EditText) findViewById(R.id.txtSifreTekrar));
        EditText ad = ((EditText) findViewById(R.id.txtAd));
        EditText soyad = ((EditText) findViewById(R.id.txtSoyad));
        EditText telefon = ((EditText) findViewById(R.id.txtTelefon));
        EditText eposta = ((EditText) findViewById(R.id.txtPosta));
        sifre.setBackgroundColor(normalRenk);
        sifreTekrar.setBackgroundColor(normalRenk);
        ad.setBackgroundColor(normalRenk);
        soyad.setBackgroundColor(normalRenk);
        telefon.setBackgroundColor(normalRenk);
        eposta.setBackgroundColor(normalRenk);
    }
    public void clearText(View view){

        EditText sifre = ((EditText)findViewById(R.id.txtSifre));
        sifre.setText("");
        EditText sifreTekrar = ((EditText)findViewById(R.id.txtSifreTekrar));
        sifreTekrar.setText("");
        EditText ad = ((EditText)findViewById(R.id.txtAd));
        ad.setText("");
        EditText soyad = ((EditText)findViewById(R.id.txtSoyad));
        soyad.setText("");
        EditText telefon = ((EditText)findViewById(R.id.txtTelefon));
        telefon.setText("");
        EditText eposta = ((EditText)findViewById(R.id.txtPosta));
        eposta.setText("");
    }
}
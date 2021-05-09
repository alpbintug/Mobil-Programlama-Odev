package com.example.a17011066_alp_bintug_uzun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mobil.db";
    public static final int DATABASE_VERSION = 1;
    public static final String KULLANICI_TABLOSU = "Kullanicilar";
    public static final String KULLANICI_EPOSTA = "eposta";
    public static final String KULLANICI_AD = "ad";
    public static final String KULLANICI_SOYAD = "soyad";
    public static final String KULLANICI_TELEFON = "telefon";
    public static final String KULLANICI_SIFRE = "sifre";
    public static final String KULLANICI_AVATAR = "avatar";
    public static final String SORU_TABLOSU = "Sorular";
    public static final String SORU_METNI = "soru_metni";
    public static final String SORU_MEDYA_YOLU = "medya_yolu";
    public static final String SORU_SIKLAR = "siklar";
    public static final String SORU_DOGRU_SIK = "dogru_sik";
    public static final String SORU_ID = "soru_id";
    public static final String SINAV_TABLOSU = "Sinavlar";
    public static final String SINAV_ZORLUK_DERECESI = "zorluk_derecesi";
    public static final String SINAV_SURESI = "sinav_suresi";
    public static final String SINAV_ID = "sinav_id";
    public static final String SINAV_SORULARI_TABLOSU = "Sinav_Sorulari";
    private static final String Kullanici_VT_Yarat = "CREATE TABLE "+KULLANICI_TABLOSU+"(" +
            KULLANICI_EPOSTA+" TEXT NOT NULL UNIQUE, " +
            KULLANICI_AD+" TEXT NOT NULL, " +
            KULLANICI_SOYAD+" TEXT NOT NULL, " +
            KULLANICI_TELEFON+" TEXT, " +
            KULLANICI_SIFRE+" TEXT NOT NULL, " +
            KULLANICI_AVATAR+" TEXT NOT NULL, " +
            "PRIMARY KEY("+KULLANICI_EPOSTA+")" +
            ")";
    private static final String Sorular_VT_Yarat = "CREATE TABLE "+SORU_TABLOSU+"(" +
            KULLANICI_EPOSTA+" TEXT NOT NULL, " +
            SORU_METNI+" TEXT NOT NULL, " +
            SORU_MEDYA_YOLU+" TEXT, " +
            SORU_SIKLAR+" TEXT NOT NULL, " +
            SORU_DOGRU_SIK+" INTEGER NOT NULL, " +
            SORU_ID+" INTEGER NOT NULL UNIQUE, " +
            " PRIMARY KEY("+SORU_ID+")" +
            ")";
    private static  final String Sinavlar_VT_Yarat = "CREATE TABLE "+SINAV_TABLOSU+"(" +
            KULLANICI_EPOSTA+" TEXT NOT NULL, " +
            SINAV_ZORLUK_DERECESI+" INTEGER, " +
            SINAV_SURESI+" INTEGER, " +
            SINAV_ID+" INTEGER NOT NULL UNIQUE, " +
            " PRIMARY KEY("+SINAV_ID+")" +
            ")";
    private  static final String SinavSorulari_VT_Yarat = "CREATE TABLE "+SINAV_SORULARI_TABLOSU+"(" +
            SINAV_ID+" INTEGER NOT NULL, " +
            SORU_ID+" INTEGER NOT NULL" +
            ")";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(Kullanici_VT_Yarat);
    db.execSQL(Sorular_VT_Yarat);
    db.execSQL(Sinavlar_VT_Yarat);
    db.execSQL(SinavSorulari_VT_Yarat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + KULLANICI_TABLOSU);
        db.execSQL("DROP TABLE IF EXISTS " + SORU_TABLOSU);
        db.execSQL("DROP TABLE IF EXISTS " + SINAV_TABLOSU);
        db.execSQL("DROP TABLE IF EXISTS " + SINAV_SORULARI_TABLOSU);

        // Create tables again
        onCreate(db);
    }

    public boolean kullaniciEkle(Kullanici kullanici){
        Log.d("sorgu","yokki");
        String q = "SELECT * FROM " + KULLANICI_TABLOSU + " WHERE " + KULLANICI_EPOSTA + " = \"" +kullanici.getEposta()+"\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("xd",q);
        Cursor cursor = db.rawQuery(q, null);
        Log.d("dx",q);
        Log.d("qq",q);
        if(cursor.getCount()>0) {
            cursor.close();
            return false;
        }
        Log.d("şl",q);
        cursor.close();
        ContentValues cv = new ContentValues();
        cv.put(KULLANICI_EPOSTA,kullanici.getEposta());
        cv.put(KULLANICI_AD,kullanici.getAd());
        cv.put(KULLANICI_SOYAD,kullanici.getSoyad());
        cv.put(KULLANICI_TELEFON,kullanici.getTelefon());
        cv.put(KULLANICI_SIFRE,kullanici.getSifre());
        cv.put(KULLANICI_AVATAR,kullanici.getAvatar());
        Log.d("xc",q);

        db.insert(KULLANICI_TABLOSU,null,cv);
        Log.d("qw",q);
        db.close();
        return true;
    }
    public Kullanici kullaniciGirisi(String eposta, String sifre){
        Log.d(eposta,sifre);
        String q = "SELECT * FROM " + KULLANICI_TABLOSU + " WHERE " + KULLANICI_EPOSTA + " = \"" + eposta + "\" AND " + KULLANICI_SIFRE + " = \"" + sifre + "\"";
        Log.d("sorgu",q);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(q, null);
        Log.d("sorguyuYaptim","heheee");
        Log.d("kapadim","heheee");
        if(cursor.moveToFirst()) {

            Log.d("buldum","heheee");
            Kullanici kullanici = new Kullanici(
                    cursor.getString(cursor.getColumnIndex(KULLANICI_AD)),
                    cursor.getString(cursor.getColumnIndex(KULLANICI_SOYAD)),
                    cursor.getString(cursor.getColumnIndex(KULLANICI_TELEFON)),
                    cursor.getString(cursor.getColumnIndex(KULLANICI_EPOSTA)),
                    cursor.getString(cursor.getColumnIndex(KULLANICI_SIFRE)),
                    cursor.getString(cursor.getColumnIndex(KULLANICI_AVATAR)));
            cursor.close();
            return kullanici;
        }

        cursor.close();
        return null;


    }
}
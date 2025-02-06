package com.example.racialroast.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Conector extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "racial_roast.db";
        private final Context context;

    //CONSTRUCTOR
    public Conector(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context=context;

            //Al iniciar siempre se copia la base de datos del proyecto
            copiarBaseDeDatos();

            //Solo se copia la base de datos del proyecto si no existe en el movil
            //if (!existe()) copiarBaseDeDatos();
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "apellidos TEXT NOT NULL, " +
                    "nickname TEXT NOT NULL UNIQUE, " +
                    "email TEXT NOT NULL UNIQUE, " +
                    "password TEXT NOT NULL, " +
                    "es_premium INTEGER DEFAULT 0, " +
                    "foto BLOB);");
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//            db.execSQL("drop Table if exists usuario");
//            onCreate(db);
        }

        //Comprueba si existe un fichero con el mismo nombre de la base de datos en el movil
    private boolean existe() {
        File dbFile = new File(context.getDatabasePath(DATABASE_NAME).getPath());
        return (dbFile.exists() );
    }

    //Copia la base de datos del proyecto al movil
    public void copiarBaseDeDatos() {

        String DB_NAME = DATABASE_NAME;
        String DB_PATH = context.getDatabasePath(DB_NAME).getPath();


        InputStream myInput = null;
        try {
            myInput = context.getAssets().open(DB_NAME);


        String outFileName = DB_PATH;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }


        myOutput.flush();
        myOutput.close();
        myInput.close();
        //Static.getSharedPreference(context).edit().putInt("DB_VERSION", Utils.Version.GetVersion()).commit();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    }


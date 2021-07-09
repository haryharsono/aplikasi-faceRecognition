package com.example.skripsi.DBuser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBuser extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=3;
    public static final String DATABASE_NAME="dataUser";
    private static final String TEXT_TYPE=" TEXT";
    private static final String COMMA_SEP=",";


    public DBuser(Context context) { super( context, DATABASE_NAME, null, DATABASE_VERSION );}

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ENTRIES = "CREATE TABLE "
        + FieldUser.NAMA_TABLE + " ( "
        + FieldUser.ID+ TEXT_TYPE
        + " ) ";

        db.execSQL( SQL_CREATE_ENTRIES );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + FieldUser.NAMA_TABLE );
        onCreate( db );
    }

    public void SqlExecuteQuery(String query){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL( query );
        sqLiteDatabase.close();
    }

    public boolean checkDataUser(){
        SQLiteDatabase cek = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = cek.rawQuery( "SELECT * FROM "+ FieldUser.NAMA_TABLE, null );
        boolean bl = cursor.moveToFirst();
        Log.e( "select sqlite", ""+bl );
        cek.close();
        return bl;
    }

    public String[] getData( String table ){
        String[] data = new String[FieldUser.table.length];
        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle")
                Cursor cursor = database.rawQuery( " SELECT * FROM "+ table, null );
        if (cursor.moveToFirst()){
            do {
                for ( int i=0; i<data.length; i++ ){
                    data[i] = cursor.getString( i );
                }
            }while (cursor.moveToNext());
        }
        Log.e( " select SQLITE = ", ""+data.length );
        database.close();
        return data;
    }


}

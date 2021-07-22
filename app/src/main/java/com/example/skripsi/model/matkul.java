package com.example.skripsi.model;

import android.os.Parcel;
import android.os.Parcelable;

public class matkul implements Parcelable {
    private String namaMatkul;
    private String pukulMulai;
    private String pukulSelesai;
    private String Dosen;
    private String hari;
    private String zoom;
    private String idZoom;
    private String passwordZoom;


    public matkul(){

    }
    protected matkul(Parcel in) {
        namaMatkul = in.readString();
        pukulMulai = in.readString();
        pukulSelesai = in.readString();
        Dosen = in.readString();
        hari = in.readString();
        zoom = in.readString();
        idZoom=in.readString();
        passwordZoom=in.readString();
    }

    public static final Creator<matkul> CREATOR = new Creator<matkul>() {
        @Override
        public matkul createFromParcel(Parcel in) {
            return new matkul(in);
        }

        @Override
        public matkul[] newArray(int size) {
            return new matkul[size];
        }
    };

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getPukulMulai() {
        return pukulMulai;
    }

    public void setPukulMulai(String pukulMulai) {
        this.pukulMulai = pukulMulai;
    }

    public String getPukulSelesai() {
        return pukulSelesai;
    }

    public void setPukulSelesai(String pukulSelesai) {
        this.pukulSelesai = pukulSelesai;
    }



    public String getNamaMatkul() {
        return namaMatkul;
    }

    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    }


    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getDosen() {
        return Dosen;
    }

    public void setDosen(String dosen) {
        Dosen = dosen;
    }

    public String getIdZoom() {
        return idZoom;
    }

    public void setIdZoom(String idZoom) {
        this.idZoom = idZoom;
    }

    public String getPasswordZoom() {
        return passwordZoom;
    }

    public void setPasswordZoom(String passwordZoom) {
        this.passwordZoom = passwordZoom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(namaMatkul);
        dest.writeString(pukulMulai);
        dest.writeString(pukulSelesai);
        dest.writeString(Dosen);
        dest.writeString(hari);
        dest.writeString(zoom);
        dest.writeString(idZoom);
        dest.writeString(passwordZoom);
    }
}

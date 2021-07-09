package com.example.skripsi.model;

import android.os.Parcel;
import android.os.Parcelable;

public class matkul implements Parcelable {
    private String namaMatkul;
    private String pukulMulai;
    private String pukulSelesai;
    private String Dosen;
    private String tanggal;
    private String zoom;

    public matkul(){

    }
    protected matkul(Parcel in) {
        namaMatkul = in.readString();
        pukulMulai = in.readString();
        pukulSelesai = in.readString();
        Dosen = in.readString();
        tanggal = in.readString();
        zoom = in.readString();
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



    public String getDosen() {
        return Dosen;
    }

    public void setDosen(String dosen) {
        Dosen = dosen;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
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
        dest.writeString(tanggal);
        dest.writeString(zoom);
    }
}

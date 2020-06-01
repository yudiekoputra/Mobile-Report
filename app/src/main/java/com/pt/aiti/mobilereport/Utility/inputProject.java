package com.pt.aiti.mobilereport.Utility;

public class inputProject {
    private String key;
    private String keperluan1;
    private String keperluan2;
    private String keperluan3;
    private String biaya1;
    private String biaya2;
    private String biaya3;
    private String deskripsi1;
    private String deskripsi2;
    private String deskripsi3;
    private String totalBiaya;
    private String teknisi1;
    private String teknisi2;
    private String teknisi3;
    private String namaProject;
    private String lokasi;
    private String catatan;
    private String tanggalProject;

    public inputProject(String keperluan1, String keperluan2, String keperluan3, String biaya1, String biaya2, String biaya3,
                        String deskripsi1, String deskripsi2, String deskripsi3, String totalBiaya,
                        String teknisi1, String teknisi2, String teknisi3, String namaProject, String lokasi, String catatan, String tanggalProject){
        this.keperluan1 = keperluan1;
        this.keperluan2 = keperluan2;
        this.keperluan3 = keperluan3;
        this.biaya1 = biaya1;
        this.biaya2 = biaya2;
        this.biaya3 = biaya3;
        this.deskripsi1 = deskripsi1;
        this.deskripsi2 = deskripsi2;
        this.deskripsi3 = deskripsi3;
        this.totalBiaya = totalBiaya;
        this.teknisi1 = teknisi1;
        this.teknisi2 = teknisi2;
        this.teknisi3 = teknisi3;
        this.namaProject = namaProject;
        this.lokasi = lokasi;
        this.catatan = catatan;
        this.tanggalProject = tanggalProject;
    }

    public inputProject(){

    }

    public String getTanggalProject() {
        return tanggalProject;
    }

    public void setTanggalProject(String tanggalProject) {
        this.tanggalProject = tanggalProject;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeperluan1() {
        return keperluan1;
    }

    public void setKeperluan1(String keperluan1) {
        this.keperluan1 = keperluan1;
    }

    public String getKeperluan2() {
        return keperluan2;
    }

    public void setKeperluan2(String keperluan2) {
        this.keperluan2 = keperluan2;
    }

    public String getKeperluan3() {
        return keperluan3;
    }

    public void setKeperluan3(String keperluan3) {
        this.keperluan3 = keperluan3;
    }

    public String getBiaya1() {
        return biaya1;
    }

    public void setBiaya1(String biaya1) {
        this.biaya1 = biaya1;
    }

    public String getBiaya2() {
        return biaya2;
    }

    public void setBiaya2(String biaya2) {
        this.biaya2 = biaya2;
    }

    public String getBiaya3() {
        return biaya3;
    }

    public void setBiaya3(String biaya3) {
        this.biaya3 = biaya3;
    }

    public String getDeskripsi1() {
        return deskripsi1;
    }

    public void setDeskripsi1(String deskripsi1) {
        this.deskripsi1 = deskripsi1;
    }

    public String getDeskripsi2() {
        return deskripsi2;
    }

    public void setDeskripsi2(String deskripsi2) {
        this.deskripsi2 = deskripsi2;
    }

    public String getDeskripsi3() {
        return deskripsi3;
    }

    public void setDeskripsi3(String deskripsi3) {
        this.deskripsi3 = deskripsi3;
    }

    public String getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(String totalBiaya) {
        this.totalBiaya = totalBiaya;
    }

    public String getTeknisi1() {
        return teknisi1;
    }

    public void setTeknisi1(String teknisi1) {
        this.teknisi1 = teknisi1;
    }

    public String getTeknisi2() {
        return teknisi2;
    }

    public void setTeknisi2(String teknisi2) {
        this.teknisi2 = teknisi2;
    }

    public String getTeknisi3() {
        return teknisi3;
    }

    public void setTeknisi3(String teknisi3) {
        this.teknisi3 = teknisi3;
    }

    public String getNamaProject() {
        return namaProject;
    }

    public void setNamaProject(String namaProject) {
        this.namaProject = namaProject;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

}

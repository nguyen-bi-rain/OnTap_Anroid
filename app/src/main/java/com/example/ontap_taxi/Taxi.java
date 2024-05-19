package com.example.ontap_taxi;

public class Taxi {
    private int Ma;
    private String SoXe;
    private float QuangDuong;
    private float DonGia;
    private int KhuyenMai;

    public Taxi(int ma, String soXe, float quangDuong, float donGia, int khuyenMai) {
        Ma = ma;
        SoXe = soXe;
        QuangDuong = quangDuong;
        DonGia = donGia;
        KhuyenMai = khuyenMai;
    }

    public int getMa() {
        return Ma;
    }

    public void setMa(int ma) {
        Ma = ma;
    }

    public String getSoXe() {
        return SoXe;
    }

    public void setSoXe(String soXe) {
        SoXe = soXe;
    }

    public float getQuangDuong() {
        return QuangDuong;
    }

    public void setQuangDuong(float quangDuong) {
        QuangDuong = quangDuong;
    }

    public float getDonGia() {
        return DonGia;
    }

    public void setDonGia(float donGia) {
        DonGia = donGia;
    }

    public int getKhuyenMai() {
        return KhuyenMai;
    }

    public void setKhuyenMai(int khuyenMai) {
        KhuyenMai = khuyenMai;
    }
    public int getTongTien(){
        return (int) (this.QuangDuong * this.DonGia * (100 - KhuyenMai)/100);
    }
}

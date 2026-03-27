/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;
import jakarta.persistence.*;

/**
 *
 * @author mhoang
 */
@Entity
@Table(name = "xt_giaithuong")
public class giaiThuongETT{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGt;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "cap_giai")
    private String capGiai;

    @Column(name = "doi_tuong")
    private String doiTuong;

    @Column(name = "ma_mon")
    private String maMon;

    @Column(name = "loai_giai")
    private String loaiGiai;

    @Column(name = "diem_cong_co_mon")
    private Double diemCongCoMon;

    @Column(name = "diem_cong_khong_mon")
    private Double diemCongKhongMon;

    // ===== Constructor =====
    public giaiThuongETT() {}

    public int getIdGt() {
        return idGt;
    }

    public String getCccd() {
        return cccd;
    }

    public String getCapGiai() {
        return capGiai;
    }

    public String getDoiTuong() {
        return doiTuong;
    }

    public String getMaMon() {
        return maMon;
    }

    public String getLoaiGiai() {
        return loaiGiai;
    }

    public Double getDiemCongCoMon() {
        return diemCongCoMon;
    }

    public Double getDiemCongKhongMon() {
        return diemCongKhongMon;
    }

    public void setIdGt(int idGt) {
        this.idGt = idGt;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public void setCapGiai(String capGiai) {
        this.capGiai = capGiai;
    }

    public void setDoiTuong(String doiTuong) {
        this.doiTuong = doiTuong;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public void setLoaiGiai(String loaiGiai) {
        this.loaiGiai = loaiGiai;
    }

    public void setDiemCongCoMon(Double diemCongCoMon) {
        this.diemCongCoMon = diemCongCoMon;
    }

    public void setDiemCongKhongMon(Double diemCongKhongMon) {
        this.diemCongKhongMon = diemCongKhongMon;
    }
    
}
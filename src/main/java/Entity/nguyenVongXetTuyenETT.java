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
@Table(name = "xt_nguyenvongxettuyen")
public class nguyenVongXetTuyenETT {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnv")
    private int idNv;

    @Column(name = "nn_cccd")
    private String nnCccd;

    @Column(name = "nv_manganh")
    private String nvMaNganh;

    @Column(name = "nv_tt")
    private int nvTt; // Thứ tự nguyện vọng

    // Dùng Double (Object) thay vì double nguyên thủy để tránh lỗi NullPointerException
    @Column(name = "diem_thxt")
    private Double diemThxt; 

    @Column(name = "diem_utqd")
    private Double diemUtqd;

    @Column(name = "diem_cong")
    private Double diemCong;

    @Column(name = "diem_xettuyen")
    private Double diemXetTuyen;

    @Column(name = "nv_ketqua")
    private String nvKetQua;

    @Column(name = "nv_keys")
    private String nvKeys;

    @Column(name = "tt_phuongthuc")
    private String ttPhuongThuc;

    @Column(name = "tt_thm")
    private String ttThm;
    
    // Thêm vào trong class nguyenVongXetTuyenETT

@Transient // Quan trọng: Để Hibernate không báo lỗi thiếu cột trong DB
private double diemMon1;
@Transient
private double diemMon2;
@Transient
private double diemMon3;

@Transient
private String tenMon1;
@Transient
private String tenMon2;
@Transient
private String tenMon3;

@Transient
private double diemUuTien;

// Sau đó bạn Generate Getter/Setter cho 7 cái này là xong!

    // Hibernate BẮT BUỘC phải có constructor rỗng
    public nguyenVongXetTuyenETT() {
    }

    // ===== GETTER & SETTER (Tự động sinh) =====
    public int getIdNv() { return idNv; }
    public void setIdNv(int idNv) { this.idNv = idNv; }

    public String getNnCccd() { return nnCccd; }
    public void setNnCccd(String nnCccd) { this.nnCccd = nnCccd; }

    public String getNvMaNganh() { return nvMaNganh; }
    public void setNvMaNganh(String nvMaNganh) { this.nvMaNganh = nvMaNganh; }

    public int getNvTt() { return nvTt; }
    public void setNvTt(int nvTt) { this.nvTt = nvTt; }

    public Double getDiemThxt() { return diemThxt; }
    public void setDiemThxt(Double diemThxt) { this.diemThxt = diemThxt; }

    public Double getDiemUtqd() { return diemUtqd; }
    public void setDiemUtqd(Double diemUtqd) { this.diemUtqd = diemUtqd; }

    public Double getDiemCong() { return diemCong; }
    public void setDiemCong(Double diemCong) { this.diemCong = diemCong; }

    public Double getDiemXetTuyen() { return diemXetTuyen; }
    public void setDiemXetTuyen(Double diemXetTuyen) { this.diemXetTuyen = diemXetTuyen; }

    public String getNvKetQua() { return nvKetQua; }
    public void setNvKetQua(String nvKetQua) { this.nvKetQua = nvKetQua; }

    public String getNvKeys() { return nvKeys; }
    public void setNvKeys(String nvKeys) { this.nvKeys = nvKeys; }

    public String getTtPhuongThuc() { return ttPhuongThuc; }
    public void setTtPhuongThuc(String ttPhuongThuc) { this.ttPhuongThuc = ttPhuongThuc; }

    public String getTtThm() { return ttThm; }
    public void setTtThm(String ttThm) { this.ttThm = ttThm; }

    public double getDiemMon1() {
        return diemMon1;
    }

    public double getDiemMon2() {
        return diemMon2;
    }

    public double getDiemMon3() {
        return diemMon3;
    }

    public String getTenMon1() {
        return tenMon1;
    }

    public String getTenMon2() {
        return tenMon2;
    }

    public String getTenMon3() {
        return tenMon3;
    }

    public double getDiemUuTien() {
        return diemUuTien;
    }

    public void setDiemMon1(double diemMon1) {
        this.diemMon1 = diemMon1;
    }

    public void setDiemMon2(double diemMon2) {
        this.diemMon2 = diemMon2;
    }

    public void setDiemMon3(double diemMon3) {
        this.diemMon3 = diemMon3;
    }

    public void setTenMon1(String tenMon1) {
        this.tenMon1 = tenMon1;
    }

    public void setTenMon2(String tenMon2) {
        this.tenMon2 = tenMon2;
    }

    public void setTenMon3(String tenMon3) {
        this.tenMon3 = tenMon3;
    }

    public void setDiemUuTien(double diemUuTien) {
        this.diemUuTien = diemUuTien;
    }
    
    
    
}
